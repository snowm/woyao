package com.woyao.admin.shop.dto;

import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class PhoneSMS {
	
	private final static String url="http://gw.api.taobao.com/router/rest";//url第三方的
	private final static String appkey="23511293";//相当于账号
	private final static String secret="2a6a4619ea301cb985c850c9cab812cd";//相当于密码
	private final static String signNAme="测试";//签名名称
	private final static String modelCode="SMS_24940402";//模块Id
	private final static String sMSType="normal";//类型
	
	public String sendSMS(String json,String tell) throws ApiException{		
		/*String json="{'name':'test','num':'10','total':'3000'}";//模板里面的参数 */		
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);//建立第三方client
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();//配置参数
		req.setSmsType(sMSType);
		req.setSmsFreeSignName(signNAme);
		req.setSmsParamString(json);
		req.setRecNum(tell);//接收方电话
		req.setSmsTemplateCode(modelCode);//模板Id
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);//发起请求
		return rsp.getBody();
	}
}
