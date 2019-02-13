package dianfan.wxqrcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSONObject;

import common.propertymanager.PropertyUtil;

/**
 * 
 * @Title: AqrManager.java
 * @Package dianfan.wxqrcode
 * @Description: aqr
 * @author Administrator
 * @date 2018年4月16日 下午3:01:17
 * @version V1.0
 */
public class AqrManager {
	/**
	 * 文件保存目录路径
	 */
	private static String configPath = PropertyUtil.getProperty("agentImgUrl", "upload/qr");
	/**
	 * 文件保存目录根路径
	 */
	private static String savePath = PropertyUtil.getProperty("uploadimgroot", "C:/") + configPath;

	/**
	 * @param url
	 *            基础的url地址
	 * @param json
	 *            查询参数
	 * @param openid
	 *            微信openid
	 * @exception Exception
	 *                异常
	 * @return 构建好的url
	 */

	public static boolean httpPostWithJSON(String url, String json, String openid) throws Exception {
		boolean result = false;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		StringEntity se = new StringEntity(json);
		se.setContentType("application/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
		httpPost.setEntity(se);
		String uploadSysUrl = savePath + "/";
		File saveFile = new File(uploadSysUrl + openid + ".jpg");
		if (!saveFile.getParentFile().exists()) {
			// 如果不存在就创建这个文件夹
			saveFile.getParentFile().mkdirs();
		}
		if (!saveFile.exists()) {
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					InputStream instreams = resEntity.getContent();
					int i = saveToImgByInputStream(instreams, uploadSysUrl, openid + ".jpg");
					if (i == 1) {
						result = true;
					}
				} else {
					result = false;
				}
			} else {
				result = false;
			}
		}
		return result;
	}

	/**
	 * @param instreams
	 *            二进制流
	 * 
	 * @param imgPath
	 *            图片的保存路径
	 * 
	 * @param imgName
	 *            图片的名称
	 * 
	 * @return 1：保存正常 0：保存失败
	 * @throws FileNotFoundException
	 *             文件找不到异常
	 */
	public static int saveToImgByInputStream(InputStream instreams, String imgPath, String imgName)
			throws FileNotFoundException {

		int stateInt = 1;
		File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
		FileOutputStream fos = new FileOutputStream(file);
		if (instreams != null) {
			try {

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = instreams.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}

			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {

				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stateInt;
	}

	/**
	 * @Title: exists 判断文件存在
	 * @Description:判断文件存在
	 * @param imgPath
	 *            路径
	 * @return 返回值
	 * @throws:
	 * @time: 2018年4月16日 下午3:00:04
	 */
	public static boolean exists(String imgPath) {
		File saveFile = new File(imgPath);
		if (!saveFile.getParentFile().exists()) {
			return false;
		} else {
			// 如果存在判断这个文件的大小
			if (saveFile.length() > 0) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 调用产生二维码
	 * 
	 * @param accesstoken
	 *            accesstoken
	 * @param path
	 *            路径
	 * @param width
	 *            宽度
	 * @param openId
	 *            openid
	 * @return AgentDTO
	 */
	public static AgentDTO createwxaqrcode(String accesstoken, String path, int width, String openId) {
		AgentDTO agentDTO = new AgentDTO();
		String URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode";
		try {
			AgentReqView agentResView = new AgentReqView();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("path", path);// 你二维码中跳向的地址(规则)
			map.put("width", width);// 图片大小
			Object json = JSONObject.toJSON(map);
			AqrManager.httpPostWithJSON(URL + "?access_token=" + accesstoken, json.toString(), openId);
			String downloadUrl = configPath + "/";
			// 返回给前端的后台服务器文件下载路径
			String downloadfileUrl = downloadUrl + openId + ".jpg";
			agentResView.setDownloadfileUrl(downloadfileUrl);
			agentDTO.setResultCode("200");
			agentDTO.setDesc("成功");
			agentDTO.setBody(agentResView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return agentDTO;
	}
}
