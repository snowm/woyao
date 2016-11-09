package com.woyao.admin.shop.dto;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Component("phoneSMS")
public class PhoneSMS {

	private final static String url = "http://gw.api.taobao.com/router/rest";// url第三方的
	private final static String appkey = "23443133";// 相当于账号
	private final static String secret = "09231554ea21627a6140206182c4cf54";// 相当于密码
	private final static String sMSType = "normal";// 类型
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * <pre>
	 * ${name}，你的场子于${date}，斩获霸屏共${num1}次，共计：共${money1}元，
	 * 送出大礼共${num2}次，共计：${money2}元，总合计：${total}元。如有疑问请于倮克达人及时联系。
	 * </pre>
	 * 
	 * @param json
	 *            模板里面的参数
	 * @param tell
	 *            接收方电话
	 * @return
	 * @throws ApiException
	 */
	public void sendSMS(String json, String tell) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);// 建立第三方client
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();// 配置参数
		String signNAme = "捷报";// 签名名称
		String modelCode = "SMS_25485108";// 模块Id
		req.setSmsType(sMSType);
		req.setSmsFreeSignName(signNAme);
		req.setSmsParamString(json);
		req.setRecNum(tell);// 接收方电话
		req.setSmsTemplateCode(modelCode);// 模板Id
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);// 发起请求
		if (!StringUtils.isEmpty(rsp.getErrorCode())) {
			logger.error("Send sms error.\n{}", rsp.getBody());
			throw new ApiException(rsp.getBody());
		}
	}

	/**
	 * 
	 * @param dto
	 *            整体数据库取出的数据转化第三方参数
	 * @return 第三方短信模板参数
	 */
	public TaoBaoDTO getTaoBaoDTO(SMSParamsDTO dto) {
		TaoBaoDTO taobao = new TaoBaoDTO();
		taobao.setDate(dto.getDate());
		taobao.setMoney1(dto.getBaTotal());
		taobao.setMoney2(dto.getLiTotal());
		taobao.setName(dto.getName());
		taobao.setNum1(dto.getBaNum());
		taobao.setNum2(dto.getLiNum());
		taobao.setTotal(dto.getTotal());
		return taobao;
	}
}
