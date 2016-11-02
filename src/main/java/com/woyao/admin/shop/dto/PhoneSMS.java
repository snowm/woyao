package com.woyao.admin.shop.dto;

public class PhoneSMS {
	
	private final static String url="http://gw.api.taobao.com/router/rest";//url第三方的
	private final static String appkey="23443133";//相当于账号
	private final static String secret="09231554ea21627a6140206182c4cf54";//相当于密码
	private final static String signNAme="捷报";//签名名称
	private final static String modelCode="SMS_25100249";//模块Id
	private final static String sMSType="normal";//类型
	
	
	/**
	 * ${name}，你的场子于${date}，斩获霸屏${money1}元,送出大礼${money2}元，合计：${total}元。如有疑问请于倮克达人及时联系。
	 * @param json
	 * @param tell
	 * @return
	 */
	public String sendSMS(String json,String tell) {
		/*String json="{'name':'test','num':'10','total':'3000'}";//模板里面的参数 		
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);//建立第三方client
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();//配置参数
		req.setSmsType(sMSType);
		req.setSmsFreeSignName(signNAme);
		req.setSmsParamString(json);
		req.setRecNum(tell);//接收方电话
		req.setSmsTemplateCode(modelCode);//模板Id
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);//发起请求
*/		return null;
	}
}
