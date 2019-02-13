package dianfan.sms.test;

import java.util.ArrayList;

import dianfan.sms.SmsMultiSender;
import dianfan.sms.SmsMultiSenderResult;
import dianfan.sms.SmsSingleSender;
import dianfan.sms.SmsSingleSenderResult;
import dianfan.sms.SmsStatusPullCallbackResult;
import dianfan.sms.SmsStatusPullReplyResult;
import dianfan.sms.SmsStatusPuller;
import dianfan.sms.SmsVoicePromptSender;
import dianfan.sms.SmsVoicePromptSenderResult;
import dianfan.sms.SmsVoiceVerifyCodeSender;
import dianfan.sms.SmsVoiceVerifyCodeSenderResult;

public class SmsSDKDemo {
	public static void main(String[] args) {
		try {
			// 请根据实际 appid 和 appkey 进行开发，以下只作为演示 sdk 使用
			// appid,appkey,templId申请方式可参考接入指南
			// https://www.qcloud.com/document/product/382/3785#5-.E7.9F.AD.E4.BF.A1.E5.86.85.E5.AE.B9.E9.85.8D.E7.BD.AE
			int appid = 1400046794;
			String appkey = "8b8db764524252d2f4dd4898e1c5c1f9";

			String phoneNumber1 = "15062621432";
			String phoneNumber2 = "12345678902";
			String phoneNumber3 = "12345678903";
			int tmplId = 51817;

			// 初始化单发
			SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult singleSenderResult;

			// 普通单发
			singleSenderResult = singleSender.send(0, "86", phoneNumber1, "您注册的验证码：1234", "", "");
			System.out.println(singleSenderResult);

			// 指定模板单发
			// 假设短信模板 id 为 1，模板内容为：测试短信，{1}，{2}，{3}，上学。
			ArrayList<String> params = new ArrayList<>();
			params.add("指定模板单发");
			params.add("深圳");
			params.add("小明");
			singleSenderResult = singleSender.sendWithParam("86", phoneNumber2, tmplId, params, "", "", "");
			System.out.println(singleSenderResult);

			// 初始化群发
			SmsMultiSender multiSender = new SmsMultiSender(appid, appkey);
			SmsMultiSenderResult multiSenderResult;

			// 普通群发
			// 下面是 3 个假设的号码
			ArrayList<String> phoneNumbers = new ArrayList<>();
			phoneNumbers.add(phoneNumber1);
			phoneNumbers.add(phoneNumber2);
			phoneNumbers.add(phoneNumber3);
			multiSenderResult = multiSender.send(0, "86", phoneNumbers, "测试短信，普通群发，深圳，小明，上学。", "", "");
			System.out.println(multiSenderResult);

			// 指定模板群发
			// 假设短信模板 id 为 1，模板内容为：测试短信，{1}，{2}，{3}，上学。
			params = new ArrayList<>();
			params.add("指定模板群发");
			params.add("深圳");
			params.add("小明");
			multiSenderResult = multiSender.sendWithParam("86", phoneNumbers, tmplId, params, "", "", "");
			System.out.println(multiSenderResult);

			// 拉取短信回执和回复
			SmsStatusPuller pullstatus = new SmsStatusPuller(appid, appkey);
			SmsStatusPullCallbackResult callback_result = pullstatus.pullCallback(10);
			System.out.println(callback_result);
			SmsStatusPullReplyResult reply_result = pullstatus.pullReply(10);
			System.out.println(reply_result);

			// 发送通知内容
			SmsVoicePromptSender smsVoicePromtSender = new SmsVoicePromptSender(appid, appkey);
			SmsVoicePromptSenderResult smsSingleVoiceSenderResult = smsVoicePromtSender.send("86", phoneNumber1, 2, 2,
					"欢迎使用", "");
			System.out.println(smsSingleVoiceSenderResult);

			// 语音验证码发送
			SmsVoiceVerifyCodeSender smsVoiceVerifyCodeSender = new SmsVoiceVerifyCodeSender(appid, appkey);
			SmsVoiceVerifyCodeSenderResult smsVoiceVerifyCodeSenderResult = smsVoiceVerifyCodeSender.send("86",
					phoneNumber1, "123", 2, "");
			System.out.println(smsVoiceVerifyCodeSenderResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
