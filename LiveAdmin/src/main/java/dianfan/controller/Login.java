package dianfan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.AdminRoleMapper;
import dianfan.dao.mapper.admin.AdminUserMapper;
import dianfan.entities.Role;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.AdminUserService;
import dianfan.util.VerifyCodeUtils;

/**
 * @ClassName Login
 * @Description 管理员登录
 * @author cjy
 * @date 2018年1月2日 上午11:32:35
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/")
public class Login {
	@Autowired
	private AdminRoleMapper adminRoleMapper;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AdminUserMapper adminUserMapper;

	/**
	 * @Title: getVerifyCode
	 * @Description: 获取图片验证码
	 * @param request
	 * @param response
	 * @throws:
	 * @time: 2018年1月2日 下午12:21:30
	 */
	@LogOp(method = "getVerifyCode", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取图片验证码")
	@RequestMapping(value = "verifycode", method = RequestMethod.GET)
	@UnCheckedFilter
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		// 删除以前的
		session.removeAttribute(ConstantIF.VERFY_CODE_KEY);
		// 保存现在的
		session.setAttribute(ConstantIF.VERFY_CODE_KEY, verifyCode.toLowerCase());
		// 生成图片
		int w = 100, h = 30;
		try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Title: login
	 * @Description: 后台登录地址(后台人员登录统一入口)
	 * @throws:
	 * @time: 2018年1月2日 下午1:54:21
	 */
	@LogOp(method = "login", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "后台登录地址")
	@RequestMapping(value = "login", method = RequestMethod.GET)
	@UnCheckedFilter
	public String login() {
		return "login";
	}

	/**
	 * @Title: hexunLogin
	 * @Description: 和讯人员后台登录地址
	 * @throws:
	 * @time: 2018年1月2日 下午1:54:21
	 */
	@LogOp(method = "hexunLogin", logtype = ConstantIF.LOG_TYPE_3, userid = "", description = "和讯人员后台登录地址")
	@RequestMapping(value = "/hexun/login", method = RequestMethod.GET)
	@UnCheckedFilter
	public String hexunLogin() {
		return "hexunlogin";
	}

	/**
	 * @Title: loginAction
	 * @Description: 登录操作
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param attributes
	 *            重定向控制器
	 * @param model
	 *            啊所发生的
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午3:53:29
	 */
	@LogOp(method = "loginAction", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "登录操作")
	@RequestMapping(value = "loginaction", method = { RequestMethod.POST })
	@UnCheckedFilter
	public String loginAction(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes,
			Model model) {
		// 获取session中的验证码
		HttpSession session = request.getSession();
		String save_code = (String) session.getAttribute(ConstantIF.VERFY_CODE_KEY);
		// 删除验证码
		session.removeAttribute(ConstantIF.VERFY_CODE_KEY);
		// 获取用户验证码
		String verifycode = request.getParameter("verifycode");
		if (save_code == null || !save_code.toLowerCase().equals(verifycode.toLowerCase())) {
			// 验证码不正确
			attributes.addFlashAttribute("error_msg", ResultMsg.C_005);
			return "redirect:/login.do";
		}

		String account = request.getParameter("account");
		String password = request.getParameter("password");

		try {
			UserInfo userInfo = adminUserService.checkLogin(account, password);
			if (userInfo == null) {
				// 验证未通过
				attributes.addFlashAttribute("error_msg", ResultMsg.C_008);
				return "redirect:/login";
			} else if (userInfo.getEntkbn() == 1) {
				// 账号被封
				attributes.addFlashAttribute("error_msg", ResultMsg.C_023);
				return "redirect:/login";
			} else {
				String agentid = adminUserMapper.getAgentByUserId(userInfo.getUserid());
				if(agentid != null && !agentid.trim().isEmpty())
				{
					userInfo.setAgentid(agentid);
				}
				// 验证通过,保存token
				session.setAttribute(ConstantIF.PC_SESSION_KEY, userInfo);
				// 创建Cookie
				Cookie nameCookie = new Cookie(ConstantIF.COOKIE_NAME, account);
				Cookie pswCookie = new Cookie(ConstantIF.COOKIE_PWD, password);
				// 设置Cookie的父路径
				nameCookie.setPath(request.getContextPath() + "/");
				pswCookie.setPath(request.getContextPath() + "/");

				String online = request.getParameter("online");
				if (online != null && "on".equals(online.toLowerCase())) {
					// 保存Cookie
					// 保存Cookie的时间长度，单位为秒
					nameCookie.setMaxAge(7 * 24 * 60 * 60);
					pswCookie.setMaxAge(7 * 24 * 60 * 60);
				} else {
					// 不保存Cookie
					nameCookie.setValue(null);
					pswCookie.setValue(null);
					nameCookie.setMaxAge(0);
					pswCookie.setMaxAge(0);
				}
				// 加入Cookie到响应头
				response.addCookie(nameCookie);
				response.addCookie(pswCookie);

				List<String> rolelist = new ArrayList<>();
				// 对登录用户按角色进行分类
				if (userInfo.getRole() == 2) {
					if("admin".equals(userInfo.getUsername())) {
						// 当前用户为管理员
						rolelist.add("all");
					}else {
						// 非超级管理员，缓存管理员权限
						List<Role> roles = adminRoleMapper.findRoleByAdminId(userInfo.getUserid());
						for(Role r : roles) {
							rolelist.add(r.getFunctionname());
						}
						//缓存所有权限
						List<String> allroles = adminRoleMapper.findAllRoles();
						session.setAttribute(ConstantIF.PC_ROLE_ALL, allroles);
					}
					session.setAttribute(ConstantIF.PC_ROLE, rolelist);
					return "redirect:/admin/index";
				} else if (userInfo.getRole() == 3) {
					// 当前用户为业务员
					/*
					 * 1、岗位判断
					 * 2、有 获取岗位对应的权限
					 * 3、缓存权限
					 * 4、跳至xxx
					 */
					rolelist.add("all");
					session.setAttribute(ConstantIF.PC_ROLE, rolelist);
					session.setAttribute(ConstantIF.PC_AGENT_ROLE, rolelist);
					return "redirect:/saler/index";
				} else if (userInfo.getRole() == 4) {
					// 当前用户为代理商
					rolelist.add("all");
					session.setAttribute(ConstantIF.PC_AGENT_ROLE, rolelist);
					return "redirect:/agent/index";
				} else if (userInfo.getRole() == 5) {
					// 当前用户为老师
					rolelist.add("all");
					session.setAttribute(ConstantIF.PC_ROLE, rolelist);
					return "redirect:/tea/index";
				} else if (userInfo.getRole() == 6) {
					// 当前用户为代理商管理人员
					List<Role> roles = adminRoleMapper.findAgentRoleByUserId(userInfo.getUserid());
					for(Role r : roles) {
						rolelist.add(r.getFunctionname());
					}
					session.setAttribute(ConstantIF.PC_AGENT_ROLE, rolelist);
					//缓存所有权限
					List<String> allroles = adminRoleMapper.findAgentAllRoles();
					session.setAttribute(ConstantIF.PC_AGENT_ROLE_ALL, allroles);
					return "redirect:/agent/index";
				}
				return ResultMsg.ADMIN_500;
			}
		} catch (SQLExecutorException e) {
			// 重定向到 500
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: hexunLoginAction
	 * @Description: 和讯人员登录操作
	 * @param request
	 * @param response
	 * @param attributes
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 上午11:40:17
	 */
	// @LogOp(method = "hexunLoginAction", logtype = ConstantIF.LOG_TYPE_3, userid =
	// "", description = "和讯人员登录操作")
	@RequestMapping(value = "hxloginaction", method = { RequestMethod.POST })
	@UnCheckedFilter
	public String hexunLoginAction(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes attributes, Model model) {
		// 获取session中的验证码
		HttpSession session = request.getSession();
		String save_code = (String) session.getAttribute(ConstantIF.VERFY_CODE_KEY);
		// 删除验证码
		session.removeAttribute(ConstantIF.VERFY_CODE_KEY);
		// 获取用户验证码
		String verifycode = request.getParameter("verifycode");
		if (save_code == null || !save_code.toLowerCase().equals(verifycode.toLowerCase())) {
			// 验证码不正确
			attributes.addFlashAttribute("error_msg", ResultMsg.C_005);
			return "redirect:/hexun/login";
		}

		String account = request.getParameter("account");
		String password = request.getParameter("password");

		if (!"hexun".equals(account) && !"hexun".equals(password)) {
			// 验证未通过
			attributes.addFlashAttribute("error_msg", ResultMsg.C_008);
			return "redirect:/hexun/login";
		} else {
			// 验证通过,保存token
			session.setAttribute(ConstantIF.PC_SESSION_KEY, "hexun");
			// 创建Cookie
			Cookie nameCookie = new Cookie(ConstantIF.COOKIE_NAME, account);
			Cookie pswCookie = new Cookie(ConstantIF.COOKIE_PWD, password);
			// 设置Cookie的父路径
			nameCookie.setPath(request.getContextPath() + "/");
			pswCookie.setPath(request.getContextPath() + "/");

			String online = request.getParameter("online");
			if (online != null && "on".equals(online.toLowerCase())) {
				// 保存Cookie
				// 保存Cookie的时间长度，单位为秒
				nameCookie.setMaxAge(7 * 24 * 60 * 60);
				pswCookie.setMaxAge(7 * 24 * 60 * 60);
			} else {
				// 不保存Cookie
				nameCookie.setValue(null);
				pswCookie.setValue(null);
				nameCookie.setMaxAge(0);
				pswCookie.setMaxAge(0);
			}
			// 加入Cookie到响应头
			response.addCookie(nameCookie);
			response.addCookie(pswCookie);

			return "redirect:/hx/index";
		}
	}

}
