package dianfan.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import dianfan.constant.ConstantIF;

public class SessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("客户端已连接服务器 --会话号为： " + se);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("用户会话已失效session -- 会话号为：" + se);
	}

	public synchronized static boolean isLogin(HttpSession session, Object userInfo, String ip) {
		session.setAttribute(ConstantIF.PC_SESSION_KEY, userInfo);
		return true;
	}

	public synchronized static boolean isLogOut(HttpSession session, String remoteAddr) {
		session.invalidate();
		System.out.println("用户失效会话号为： - " + session);
		return true;
	}

}
