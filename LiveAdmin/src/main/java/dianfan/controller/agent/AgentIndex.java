package dianfan.controller.agent;

import java.text.ParseException;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.AdminUserMapper;
import dianfan.entities.ChartsBean;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.AdminUserService;
import dianfan.service.admin.AgentService;
import dianfan.service.agent.AgentIndexService;

/**
 * @ClassName AgentIndex
 * @Description 代理商首页
 * @author cjy
 * @date 2018年1月2日 下午5:25:23
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentIndex {
	@Autowired
	private AgentService agentService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private AgentIndexService agentIndexService;
	
	/**
	 * @Title: dashboard
	 * @Description: 后台首页
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午5:52:50
	 */
	@LogOp(method = "dashboard",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "后台首页")
	@RequestMapping(value = "index", method = { RequestMethod.GET })
	public String dashboard() {
		return ConstantIF.AGENT_PATH + "index";
	}
	
	/**
	 * @Title: welcome
	 * @Description: 我的桌面
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午5:53:01
	 */
	@LogOp(method = "desktop",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "我的桌面")
	@RequestMapping(value = "welcome", method = { RequestMethod.GET })
	public String desktop() {
		return ConstantIF.AGENT_PATH + "welcome";
	}
	
	/**
	 * @Title: loadResourceData
	 * @Description: 注册人数数据
	 * @param type 1今日，2近一周，3近一月
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 上午9:20:32
	 */
	@LogOp(method = "loadResourceData",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "资源数据")
	@RequestMapping(value = "resourcedata", method = { RequestMethod.GET })
	public @ResponseBody ResultBean loadResourceData(@RequestParam("type") int type, HttpSession session) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			LinkedList<ChartsBean> resourceData = agentIndexService.getResourceData(type, userInfo.getUserid());
			return new ResultBean(resourceData);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: loadEarningsData
	 * @Description: 收益数据
	 * @return
	 * @throws:
	 * @time: 2018年2月6日 上午9:21:27
	 */
	@LogOp(method = "loadEarningsData",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "收益数据")
	@RequestMapping(value = "earningsdata", method = { RequestMethod.GET })
	public @ResponseBody ResultBean loadEarningsData(@RequestParam("year") int year, @RequestParam("month") int month, HttpSession session) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			LinkedList<ChartsBean> earningsData = agentIndexService.getEarningsData(year, month, userInfo.getUserid());
			return new ResultBean(earningsData);
		} catch (SQLExecutorException | ParseException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: dashboardAdmin
	 * @Description: 管理员个人信息页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午2:46:06
	 */
	@LogOp(method = "dashboardAdmin", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员信息页")
	@RequestMapping(value = "mydata", method = { RequestMethod.GET })
	public String dashboardAdmin(HttpSession session, Model model) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		model.addAttribute("userinfo", userInfo);
		return ConstantIF.AGENT_PATH + "self-info";
	}
	
	/**
	 * @Title: editSelfInfo
	 * @Description: 个人信息修改
	 * @param avator
	 * @param pic
	 * @param info
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月22日 下午5:21:56
	 */
	@LogOp(method = "editSelfInfo", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "个人信息修改")
	@RequestMapping(value = "editselfinfo", method = RequestMethod.POST)
	public @ResponseBody ResultBean editSelfInfo(UserInfo info, HttpSession session) {
		try {
			agentService.updateSelfAgentInfo(info);
			//更新session
			UserInfo sess = adminUserMapper.getUserDataById(info.getUserid());
			session.setAttribute(ConstantIF.PC_SESSION_KEY, sess);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: editAdminUserPwd
	 * @Description: 密码修改
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月22日 下午5:59:55
	 */
	@LogOp(method = "changePwd", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "密码修改")
	@RequestMapping(value = "changepwd")
	public @ResponseBody ResultBean changePwd(HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
		
		String oldpwd = request.getParameter("oldpwd");
		//检测旧密码正确性
		if(!userInfo.getPassword().equals(oldpwd)) {
			return new ResultBean("017", ResultMsg.C_017);
		}
		
		String newpwd = request.getParameter("newpwd");
		String password = request.getParameter("password");
		//检测新密码是否一致
		if(!newpwd.equals(password)) {
			return new ResultBean("018", ResultMsg.C_018);
		}
		//检测新旧密码是否相同
		if(oldpwd.equals(password)) {
			return new ResultBean("019", ResultMsg.C_019);
		}
		
		userInfo.setPassword(password);
		try {
			adminUserService.updateAdminUserPwd(userInfo);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	
}
