package dianfan.controller.agent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.entities.UserInfo;

/**
 * @ClassName AdminManage
 * @Description 代理商信息管理
 * @author cjy
 * @date 2018年2月8日 上午11:00:29
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentAdminManage {
	/**
	 * @Title: dashboardAdmin
	 * @Description: 代理商个人信息页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午2:46:06
	 */
	@LogOp(method = "dashboardAdmin", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商信息页")
	@RequestMapping(value = "mydata")
	public String dashboardAdmin(HttpSession session, Model model) {
		return "forward:/admin/mydata";
	}
	
	/**
	 * @Title: editAdminUser
	 * @Description: 代理商个人信息修改
	 * @param session
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午4:29:11
	 */
	@LogOp(method = "editAdminUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商信息修改")
	@RequestMapping(value = "editmydata")
	public String editAdminUser(HttpSession session, UserInfo info) {
		return "forward:/admin/editmydata";
	}
	
	/**
	 * @Title: editAdminUserPwd
	 * @Description: 修改密码
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午5:00:58
	 */
	@LogOp(method = "editAdminUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商信息修改")
	@RequestMapping(value = "editadminpwd")
	public String editAdminUserPwd(HttpServletRequest request) {
		return "forward:/admin/editadminpwd";
	}
	
}
