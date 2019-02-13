package dianfan.wxpay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import common.propertymanager.PropertyUtil;

/**
 * 签名字符串
 * 
 */
public final class PayUtil {
	/**
	 * 编码格式
	 */
	private final static String ENCODING = "encoding";

	/**
	 * @Title: sign 签名
	 * @Description: 签名
	 * @param text
	 *            文本
	 * @param key
	 *            秘钥
	 * @param inputCharset
	 *            编码
	 * @return 返回加密后字符串
	 * @time: 2018年4月16日 下午1:47:08
	 */
	public static String sign(String text, String key, String inputCharset) {
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, inputCharset));
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key
	 *            密钥
	 * @param inputCharset
	 *            编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String key, String inputCharset) {
		text = text + key;
		String mysign = DigestUtils.md5Hex(getContentBytes(text, inputCharset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Title: getContentBytes 获取字节
	 * @Description: 获取内容字节
	 * @param content
	 *            内容
	 * @param charset
	 *            字符集
	 * @return 返回字节
	 * @throws: 异常
	 * @time: 2018年4月16日 下午1:50:27
	 */
	public static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	/**
	 * 生成6位或10位随机数 param
	 * 
	 * @param codeLength
	 *            多少位
	 * @return 位数
	 */
	public static String createCode(int codeLength) {
		String code = "";
		for (int i = 0; i < codeLength; i++) {
			code += (int) (Math.random() * 9);
		}
		return code;
	}

	/**
	 * @Title: isValidChar 是不是有效字符
	 * @Description:是不是有效字符
	 * @param ch
	 *            字符
	 * @return 返回true或false
	 * @throws:
	 * @time: 2018年4月16日 下午1:56:49
	 */
	private static boolean isValidChar(char ch) {
		if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
			return true;
		if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))
			return true;// 简体中文汉字编码
		return false;
	}

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || "".equals(value) || "sign".equalsIgnoreCase(key)
					|| "sign_type".equalsIgnoreCase(key)) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	/**
	 * @Title: httpRequest 请求服务器内容
	 * @Description:请求服务器内容
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方法
	 * @param outputStr
	 *            参数
	 * @return 服务器端返回的内容
	 * @throws:异常
	 * @time: 2018年4月16日 下午2:18:28
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		// 创建SSLContext
		StringBuffer buffer = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();

			// 往服务器端写内容
			if (null != outputStr) {
				OutputStream os = conn.getOutputStream();
				os.write(outputStr.getBytes(PropertyUtil.getProperty(ENCODING)));
				os.close();
			}
			// 读取服务器端返回的内容
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, PropertyUtil.getProperty(ENCODING));
			BufferedReader br = new BufferedReader(isr);
			buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			br.close();
			isr.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (buffer.length() == 0) {
			return null;
		}
		return buffer.toString();
	}

	/**
	 * @Title: urlEncodeUTF8 把url encode
	 * @Description: 把url encode
	 * @param source
	 *            源
	 * @return encode后的链接
	 * @throws:
	 * @time: 2018年4月16日 下午2:19:57
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, PropertyUtil.getProperty(ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
