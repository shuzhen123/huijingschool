package dianfan.controller;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;

/**
 * @ClassName Logout
 * @Description 管理员登出
 * @author cjy
 * @date 2018年1月3日 上午10:50:28
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/")
public class Logout {
	
	
	/**
	 * @Title: logout
	 * @Description: 登出操作
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月3日 上午10:52:36
	 */
	@LogOp(method = "logout",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "登出操作")
	@RequestMapping(value = "logout")
	@UnCheckedFilter
	public String logout(HttpSession session) {
		session.removeAttribute(ConstantIF.PC_SESSION_KEY);
		return "redirect:/login";
	}
	
	/**
	 * @Title: logout
	 * @Description: 登出操作
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月3日 上午10:52:36
	 */
	@LogOp(method = "hexunLogout",  logtype = ConstantIF.LOG_TYPE_3, userid = "", description = "和讯账号登出操作")
	@RequestMapping(value = "hexunlogout")
	@UnCheckedFilter
	public String hexunLogout(HttpSession session) {
		session.removeAttribute(ConstantIF.PC_SESSION_KEY);
		return "redirect:/hexun/login";
	}
	
}
