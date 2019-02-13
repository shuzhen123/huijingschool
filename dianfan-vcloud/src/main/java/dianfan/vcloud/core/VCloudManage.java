package dianfan.vcloud.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.vcloud.auth.SumBuilder;
import dianfan.vcloud.config.VcoludConf;

/**
 * @ClassName CreateChannel
 * @Description 频道管理
 * @author cjy
 * @date 2018年1月6日 上午9:54:06
 */
public class VCloudManage {

	/**
	 * @Title: channel
	 * @Description:  网易云频道操作
	 * @param urlkey 请求的properties key
	 * @param param 请求参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws:
	 * @time: 2018年1月23日 上午10:59:34
	 */
	public static Map<String, Object> channel(String urlkey, Map<String, Object> param) throws ClientProtocolException, IOException {
		HttpPost httpPost = getHttpPost(urlkey, true);
		// 对象转json字符串
		ObjectMapper mapper = new ObjectMapper();
		String data = mapper.writeValueAsString(param);
		// 设置请求的参数
		StringEntity params = new StringEntity(data, Consts.UTF_8);
		httpPost.setEntity(params);
		//执行请求并返回数据
		return reqHandle(httpPost);
	}
	
	/**
	 * @Title: im
	 * @Description: 网易云im操作
	 * @param urlkey 请求的properties key
	 * @param param 请求参数
	 * @return
	 * @throws ClientProtocolException 
	 * @throws ParseException
	 * @throws IOException
	 * @throws:
	 * @time: 2018年5月8日 下午1:37:16
	 */
	public static Map<String, Object> im(String urlkey, Map<String, String> param) throws ClientProtocolException, IOException {
		HttpPost httpPost = getHttpPost(urlkey, false);
		// 设置请求的参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for(Entry<String, String> ent : param.entrySet()) {
			nvps.add(new BasicNameValuePair(ent.getKey(), ent.getValue()));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		//执行请求并返回数据
		return reqHandle(httpPost);
	}
	
	/**
	 * @Title: getHttpPost
	 * @Description: 封装HttpPost
	 * @param urlkey prop中网易云地址key
	 * @param userJson Content-Type是否使用json（true 使用，false 使用form）
	 * @return
	 * @throws:
	 * @time: 2018年4月25日 下午12:36:04
	 */
	private static HttpPost getHttpPost(String urlkey, boolean userJson) {
		String url = VcoludConf.config().getConfig(urlkey); //请求地址
		String appKey = VcoludConf.config().getConfig("vcloud.key"); //开发者平台分配的AppKey
		String appSecret = VcoludConf.config().getConfig("vcloud.secret"); //开发者平台分配的Secret
		//获取随机数
		StringBuffer random = new StringBuffer();
		for (int i = 0; i < 32; i++) {
			random.append((int) (Math.random() * 9));
		}
		String nonce = random.toString();
		
		//当前UTC时间戳
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		//SHA1
		String checkSum = SumBuilder.getCheckSum(appSecret, nonce ,curTime);

		HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        if(userJson) {
        	httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        }else {
        	httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        }
        
		return httpPost;
	}
	
	/**
	 * @Title: reqHandle
	 * @Description: 执行请求
	 * @param httpPost
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws:
	 * @time: 2018年5月8日 下午1:23:59
	 */
	private static Map<String, Object> reqHandle(HttpPost httpPost) throws ClientProtocolException, IOException {
		// 执行请求
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(httpPost);
		
		String result = EntityUtils.toString(response.getEntity(), "utf-8");
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(result, Map.class);
	}
}
