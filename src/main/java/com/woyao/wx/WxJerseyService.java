package com.woyao.wx;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

import com.snowm.utils.encrypt.SHA1Encrypt;
import com.woyao.GlobalConfig;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.dto.chat.out.OutMsgDTO;
import com.woyao.customer.dto.chat.out.OutboundCommand;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IOrderService;
import com.woyao.customer.service.IProductService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.purchase.OrderResultInfo;
import com.woyao.domain.purchase.OrderStatus;
import com.woyao.utils.JaxbUtils;
import com.woyao.wx.dto.OrderNotifyRequest;
import com.woyao.wx.dto.OrderNotifyResponse;

@Component
@Path("/")
public class WxJerseyService {

	private static final String RETURN_CODE_SUCCESS = "SUCCESS";
	private static final String RESULT_CODE_SUCCESS = "SUCCESS";

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private GlobalConfig globalConfig;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IChatService chatService;

	@Autowired
	private IProductService productService;
	
	@Autowired
	private CommonDao commonDao;

	@Path("/verifyToken")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String verify(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp,
			@QueryParam("nonce") String nonce, @QueryParam("echostr") String echostr, @Context HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("Verify token request--signature:%s, timestamp:%s, nonce:%s, echostr:%s", signature, timestamp, nonce,
					echostr));
		}
		try {
			String encoded = this.encode(timestamp, nonce, this.globalConfig.getVerifyToken());
			if (log.isDebugEnabled()) {
				this.log.debug("Verify token encoded:" + encoded);
			}
			if (encoded.equals(signature)) {
				return echostr;
			}
		} catch (Exception ex) {
			this.log.error("Verify token failure!", ex);
		}
		this.log.error("Verify token failure!");
		return null;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/orderNotify")
	public String helloWorld(String body) {
		try {
			OrderNotifyRequest req = JaxbUtils.unmarshall(OrderNotifyRequest.class, body);
			OrderNotifyResponse resp = new OrderNotifyResponse();
			resp.setReturnCode(RETURN_CODE_SUCCESS);
			resp.setReturnMsg("OK");
			if (!this.validate(req)) {
				return JaxbUtils.marshall(resp);
			}
			OrderResultInfo resultInfo = new OrderResultInfo();
			BeanUtils.copyProperties(req, resultInfo);
			resultInfo.setTimeEnd(WxUtils.parseDate(req.getTimeEnd()));
			resultInfo.setDesc(req.getReturnMsg());
			long orderId = Long.parseLong(req.getOutTradeNo());
			this.orderService.savePayResultInfo(resultInfo, orderId);

			if (RESULT_CODE_SUCCESS.equals(req.getResultCode()) && !StringUtils.isBlank(req.getTransactionId())) {
				log.debug("支付成功！");
				this.orderService.updateOrderStatus(orderId, OrderStatus.SUCCESS);
				OutMsgDTO outbound = new OutMsgDTO();
				OrderDTO order = this.orderService.get(orderId);
				Long msgId = order.getMsgId();

				if (msgId != null) {
					log.debug("发送霸屏消息！");
					ChatMsg msg = this.commonDao.get(ChatMsg.class, msgId);
					outbound.setClientMsgId(msg.getClientMsgId());
					outbound.setCommand(OutboundCommand.ACCEPT_MSG);
					outbound.setText(msg.getText());
					outbound.setPic(msg.getPicURL());
					outbound.setSender(this.chatService.getChatter(order.getConsumer().getId()));
					outbound.setSentDate(new Date());
					outbound.setCreationDate(msg.getModification().getCreationDate());
					MsgProductDTO msgProduct = productService.getMsgProduct(msg.getProductId());
					outbound.setDuration(msgProduct.getHoldTime());
					Long chatRoomId = msg.getChatRoomId();
					this.chatService.sendRoomMsg(outbound, chatRoomId, null);
				}
			} else {
				this.orderService.updateOrderStatus(orderId, OrderStatus.FAIL);
			}
			return JaxbUtils.marshall(resp);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean validate(OrderNotifyRequest req) {
		if (req == null) {
			return false;
		}
		SignValidateFieldCallback fc = new SignValidateFieldCallback(req);
		ReflectionUtils.doWithFields(req.getClass(), fc, ff);
		String sign = WxUtils.generatePaySign(fc.nvs, globalConfig.getPayApiKey());
		if (!sign.equals(req.getSign())) {
			log.error("sign incorrect -- expected:" + sign + "  actual:" + req.getSign());
			return false;
		}
		if (!RETURN_CODE_SUCCESS.equals(req.getReturnCode())) {
			return false;
		}
		return true;
	}

	private class SignValidateFieldCallback implements FieldCallback {

		private List<NameValuePair> nvs = new ArrayList<>();

		private Object obj;

		public SignValidateFieldCallback(Object obj) {
			super();
			this.obj = obj;
		}

		@Override
		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
			ReflectionUtils.makeAccessible(field);
			Object value = ReflectionUtils.getField(field, obj);
			if (value == null) {
				return;
			}
			String valueStr = value.toString();
			XmlElement xmlEleAnnotation = field.getAnnotation(XmlElement.class);
			String name = xmlEleAnnotation.name();
			this.nvs.add(WxUtils.generateNVPair(name, valueStr));
		}

	}

	private FieldFilter ff = new FieldFilter() {

		@Override
		public boolean matches(Field field) {
			if (field.getName().equals("sign")) {
				return false;
			}
			int mod = field.getModifiers();
			return !Modifier.isStatic(mod) && !Modifier.isFinal(mod);
		}

	};

	private String encode(String timestamp, String nonce, String token) {
		List<String> list = new ArrayList<>();
		list.add(timestamp);
		list.add(nonce);
		list.add(token);
		Collections.sort(list);
		String s = list.get(0) + list.get(1) + list.get(2);

		String encoded = SHA1Encrypt.encrypt(s.getBytes());
		return encoded;
	}
}
