package dianfan.nimsdk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import dianfan.util.HttpClientHelper;

/**
 * @ClassName YunpianSms
 * @Description 云片clent(单利模式)
 * @author cjy
 * @date 2018年1月17日 上午10:53:17
 */
public class YunpianSms1 {
	YunpianClient clnt = null;
	//短信模板
	private static Properties props = null;
	
	private static YunpianSms1 yp = new YunpianSms1();

	private YunpianSms1() {
		props = new Properties();
		Reader reader = null;
		try {
			reader = new InputStreamReader(YunpianSms1.class.getClassLoader().getResourceAsStream("yunpian.properties"), "utf-8");
			props.load(reader);
		} catch (FileNotFoundException e) {
			System.out.println("文件找不到！");
		} catch (IOException e) {
			System.out.println("出现IOException");
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				System.out.println("文件流关闭出现异常");
			}
		}
		clnt = new YunpianClient("3bd9465660bbc68d8238235321cb416f").init();
	}

	/*
	 * 获取类对象
	 */
	public static YunpianSms1 getInstance() {
		return yp;
	}

	/**
	 * @Title: sendSignleSms
	 * @Description: 发送单条短信验证码
	 * @param phone
	 *            接收短信的手机号码
	 * @param tpl
	 *            模板号
	 * @throws:
	 * @time: 2018年1月17日 上午11:19:03
	 */
	public Result<SmsSingleSend> sendSignleSms(String phone, String[] data) {
		// 获取短信模板
		String smstpl = props.getProperty("sms.tpl.1");
		if (smstpl != null && data != null && data.length > 0) {
			for (int i = 0; i < data.length; i++) {
				smstpl = smstpl.replaceFirst("@", data[i]);
			}
		}
		
		Map<String, String> param = clnt.newParam(2);
	    param.put(YunpianClient.MOBILE, phone);
	    param.put(YunpianClient.TEXT, smstpl);
	    System.out.println(param);
	    Result<SmsSingleSend> r = clnt.sms().single_send(param);
		
		return r;
	}
}
