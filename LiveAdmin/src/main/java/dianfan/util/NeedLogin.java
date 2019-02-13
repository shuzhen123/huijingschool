package dianfan.util;

import javax.servlet.http.HttpServletRequest;

import dianfan.constant.ConstantIF;

public final class NeedLogin {
	/**
	 * 检测内页子页是否过期 false/过期 true/未过期 检测session过期
	 * 
	 * @param request
	 *            请求
	 * @return
	 */
	public static boolean needLogin(HttpServletRequest request) {
		Object loginUser = request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
		// 验证是否过期
		if (loginUser == null) {
			return false;
		}
		return true;
	}
}
