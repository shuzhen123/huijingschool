package common.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtility {
	/**
	 * 字符串判断是否为空(不包含空字符串)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null || str.trim().length() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
