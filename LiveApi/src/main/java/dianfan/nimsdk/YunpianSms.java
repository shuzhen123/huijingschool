package dianfan.nimsdk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.util.HttpClientHelper;

/**
 * @ClassName YunpianSms
 * @Description 云片clent(单利模式)
 * @author cjy
 * @date 2018年1月17日 上午10:53:17
 */
public class YunpianSms {
	//短信模板
	private static Properties props = null;
	
	private static YunpianSms yp = new YunpianSms();
	
	//公共参数
	private static Map<String, String> params = new HashMap<>();

	private YunpianSms() {
		props = new Properties();
		Reader reader = null;
		try {
			reader = new InputStreamReader(YunpianSms.class.getClassLoader().getResourceAsStream("yunpian.properties"), "utf-8");
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

	}

	/*
	 * 获取类对象
	 */
	public static YunpianSms getInstance() {
		params.clear();
		//公共参数apikey
		params.put("apikey", props.getProperty("yp.apikey"));
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
	public Map<String, Object> sendSignleSms(String phone, String[] data) {
		// 获取短信模板
		Enumeration<Object> keys = props.keys();
		
		while(keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.out.println(key+":"+props.getProperty(key));
		}
		String smstpl = props.getProperty("sms.tpl.1");
		if (smstpl != null && data != null && data.length > 0) {
			for (int i = 0; i < data.length; i++) {
				smstpl = smstpl.replaceFirst("@", data[i]);
			}
		}
		params.put("mobile", phone);
		params.put("text", smstpl);
		System.out.println(params);
		String post = HttpClientHelper.httpClientPost(props.getProperty("yp.sms.host"), params, "UTF-8");
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<>();
		try {
			return mapper.readValue(post, Map.class);
		} catch (JsonParseException e) {
			result.put("code", 500);
		} catch (JsonMappingException e) {
			result.put("code", 500);
		} catch (IOException e) {
			result.put("code", 500);
		}
		return result;
	}
}
