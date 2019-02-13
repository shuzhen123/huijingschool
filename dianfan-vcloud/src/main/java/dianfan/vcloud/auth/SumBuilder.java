package dianfan.vcloud.auth;

import java.security.MessageDigest;

/**
 * @ClassName SumBuilder
 * @Description 服务器认证SHA1
 * @author cjy
 * @date 2018年1月6日 上午9:50:26
 */
public class SumBuilder {
	/**
	 * @Title: getCheckSum
	 * @Description: 获取checkSum值
	 * @param appSecret
	 * @param nonce 
	 * @param curTime
	 * @return
	 * @throws:
	 * @time: 2018年1月6日 上午9:51:44
	 */
	public static String getCheckSum(String appSecret, String nonce, String curTime) {
		return encode("sha1", appSecret + nonce + curTime);
	}

	private static String encode(String algorithm, String value) {
		if (value == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(value.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}