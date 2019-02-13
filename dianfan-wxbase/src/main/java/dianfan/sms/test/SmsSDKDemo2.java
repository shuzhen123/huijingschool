package dianfan.sms.test;

import dianfan.sms.SmsSingleSender;
import dianfan.sms.SmsSingleSenderResult;

public class SmsSDKDemo2 {
	public static void main(String[] args) {
		try {
			// 请根据实际 appid 和 appkey 进行开发，以下只作为演示 sdk 使用
			// appid,appkey,templId申请方式可参考接入指南
			// https://www.qcloud.com/document/product/382/3785#5-.E7.9F.AD.E4.BF.A1.E5.86.85.E5.AE.B9.E9.85.8D.E7.BD.AE
			int appid = 1400046794;
			String appkey = "8b8db764524252d2f4dd4898e1c5c1f9";
			String phoneNumber1 = "15062621432";
			// 初始化单发
			SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult singleSenderResult;
			// 普通单发
			singleSenderResult = singleSender.send(0, "86", phoneNumber1, "本平台已收到您的举报消息", "", "");
			System.out.println(singleSenderResult);

			// // 指定模板单发
			// ArrayList<String> params = new ArrayList<>();
			// params.add("1234");
			// params.add("5");
			// singleSenderResult = singleSender.sendWithParam("86", phoneNumber1, tmplId,
			// params, "", "", "");
			// System.out.println(singleSenderResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
