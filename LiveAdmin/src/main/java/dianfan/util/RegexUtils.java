package dianfan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegexUtils
 * @Description 匹配器工具类
 * @author cjy
 * @date 2017年12月21日 上午9:04:43
 */
public class RegexUtils {
	
	private static String regex;

	private RegexUtils() {
	}

	/**
	 * @Title: phoneRegex
	 * @Description: 校验手机号码
	 * @param phone 手机号码
	 * @return Boolean
	 * @throws
	 */
	public static boolean phoneRegex(String phone) {
		if (phone == null || "".equals(phone.trim())) {
			return false;
		}
		regex = "^1(3[0-9]|4[57]|5[0-3 5-9]|7[01678]|8[0-9])\\d{8}$";
		return checked(phone);
	}
	
	/**
	 * @Title: telphoneRegex
	 * @Description: 固话正则校验
	 * @param phone
	 * @return
	 * @throws:
	 * @time: 2018年3月29日 下午12:04:49
	 */
	public static boolean telphoneRegex(String phone) {
		if (phone == null || "".equals(phone.trim())) {
			return false;
		}
		regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
		return checked(phone);
	}
	
	/**
	 * @Title: pwdRegex
	 * @Description: 密码位数校验
	 * @param pwd 密码字符串
	 * @return
	 * @throws
	 */
	public static boolean pwdRegex(String pwd) {
		if (pwd == null || "".equals(pwd)) {
			return false;
		}
		regex = "^.{4,16}$";
		return checked(pwd);
	}

	/**
	 * @Title: checked
	 * @Description: 执行正则判断
	 * @param attr 待校验字符串
	 * @return boolean
	 * @throws
	 */
	private static boolean checked(String attr) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(attr);
		return matcher.matches();
	}

}
