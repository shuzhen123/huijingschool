package dianfan.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GenRandomNumUtil {
	/**
	 * 生成日期号
	 * 
	 */
	public static String getDateNo() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ((int) (Math.random() * 10000));
	}
	/**
	 * 随机生成订单号
	 * 
	 * @return 订单号
	 */
	public static String getOrderNo() {
		String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String code = "";
		for (int i = 0; i < 8; i++) {
			code += (int) (Math.random() * 9);
		}
		return today + code;
	}
	/**
	 * 生成用户id[24位]
	 * 
	 * @return
	 */
	public static String userId() {
		String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String code = getRandomNum(10);
		String userId = today + code;
		return userId;
	}

	/**
	 * 生成邀请CODE[24位]
	 * 
	 * @return
	 */
	public static String inviteCode() {
		String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String code = getRandomNum(10);
		String inviteCode = today + code;
		return inviteCode;
	}

	/**
	 * 生成数字id
	 * 
	 * @return
	 */
	public static String randomDigitalId() {
		String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String code = getRandomNum(18);
		String randomDigitalId = today + code;
		return randomDigitalId;
	}

	/**
	 * 生成一个n位的随机数字符串
	 * 
	 * @param n
	 * @return
	 */
	public static String getRandomNum(int length) {
		String str = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(9);// [1,9)
			sb.append(str.charAt(number + 1));
		}
		return sb.toString();
	}

	/**
	 * 生成一个随机的用户名
	 */
	public static String getRandomName() {
		String front = "huijing";
		String behind = getRandomNum(6);
		String integration = front + behind;
		return integration;
	}

	/**
	 * 生成一个随机的昵称
	 */
	public static String getRandomNickName() {
		String front = "peoplepig";
		String back = getRandomNum(6);
		String nickname = front + back;
		return nickname;
	}
}
