package com.woyao.customer.chat.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.dto.chat.in.GPSMsgDTO;
import com.woyao.customer.service.IMobileService;

@Component("gpsMsgHandler")
public class GPSMsgHandler implements MsgHandler<GPSMsgDTO> {

	@Resource(name = "mobileService")
	private IMobileService mobileService;
	
	@Override
	public void handle(WebSocketSession wsSession, GPSMsgDTO inbound) {
		if (inbound.getLatitude() == null || inbound.getLongitude() == null) {
			return;
		}
		ProfileDTO chatter = SessionUtils.getChatter(wsSession);
		chatter.setLatitude(inbound.getLatitude());
		chatter.setLongitude(inbound.getLongitude());

		long shopId = SessionUtils.getShopId(wsSession);
		chatter.setDistanceToRoom(this.mobileService.calculateDistanceToShop(chatter.getLatitude(), chatter.getLongitude(), shopId ));
	}

}
