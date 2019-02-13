package dianfan.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.AdminUserMapper;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.Role;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.AdminUserService;
import dianfan.service.admin.RoleService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName AdminManage
 * @Description 管理员信息管理
 * @author cjy
 * @date 2018年1月17日 下午5:28:57
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class AdminManage {

	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: dashboardAdmin
	 * @Description: 管理员个人信息页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午2:46:06
	 */
	@LogOp(method = "dashboardAdmin", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员信息页")
	@RequestMapping(value = "mydata")
	public String dashboardAdmin(HttpSession session, Model model) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		if (userInfo.getIconurl() != null && !userInfo.getIconurl().isEmpty()) {
			userInfo.setIconurl(ConstantIF.PROJECT + userInfo.getIconurl());
		}
		model.addAttribute("userinfo", userInfo);
		return ConstantIF.ADMIN_ADMIN + "admin-info";
	}

	/**
	 * @Title: editAdminUser
	 * @Description: 管理员个人信息修改
	 * @param session
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午4:29:11
	 */
	@LogOp(method = "editAdminUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员信息修改")
	@RequestMapping(value = "editmydata")
	public @ResponseBody ResultBean editAdminUser(MultipartFile avator, UserInfo info, HttpSession session) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		info.setUserid(userInfo.getUserid());
		info.setRole(userInfo.getRole());

		if (userInfo.getRole() == 4 && avator != null && !avator.isEmpty()) {
			// 绝对上传路径
			try {
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				// 上传
				String newfilename = FileUploadUtils.uploadOne(avator, realPath);
				info.setIconurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
				userInfo.setIconurl(info.getIconurl());
			} catch (IOException e) {
				// 头像上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		}

		try {
			adminUserService.editAdminUserInfo(info);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		// 更新缓存
		userInfo.setTelno(info.getTelno());
		userInfo.setNickname(info.getNickname());

		if (userInfo.getRole() == 2) {

		} else if (userInfo.getRole() == 3) {

		} else if (userInfo.getRole() == 4) {
			userInfo.setCidpassword(info.getCidpassword());
		}
		session.setAttribute(ConstantIF.PC_SESSION_KEY, userInfo);
		return new ResultBean();
	}

	/**
	 * @Title: editAdminUserPwd
	 * @Description: 修改密码
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午5:00:58
	 */
	@LogOp(method = "editAdminUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改密码")
	@RequestMapping(value = "editadminpwd")
	public @ResponseBody ResultBean editAdminUserPwd(HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);

		String oldpwd = request.getParameter("oldpwd");
		// 检测旧密码正确性
		if (!userInfo.getPassword().equals(oldpwd)) {
			return new ResultBean("017", ResultMsg.C_017);
		}

		String newpwd = request.getParameter("newpwd");
		String password = request.getParameter("password");
		// 检测新密码是否一致
		if (!newpwd.equals(password)) {
			return new ResultBean("018", ResultMsg.C_018);
		}
		// 检测新旧密码是否相同
		if (oldpwd.equals(password)) {
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

	/* *************************************************** */
	/* *******************以下是管理员管理操作******************* */
	/* *************************************************** */

	/**
	 * @Title: adminManagePage
	 * @Description: 管理员管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月15日 上午9:38:47
	 */
	@LogOp(method = "adminManagePage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员管理页")
	@RequestMapping(value = "adminmanagepage")
	public String adminManagePage() {
		return ConstantIF.ADMIN_ADMIN + "admin-list";
	}

	/**
	 * @Title: adminUserList
	 * @Description: 管理员列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月15日 上午10:04:27
	 */
	@LogOp(method = "adminUserList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员列表")
	@RequestMapping(value = "adminuserlist")
	public @ResponseBody DataTable adminUserList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 管理员列表
			table = adminUserService.findAdminUser(userInfo.getUserid(), start, length);
			
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: addAdminPage
	 * @Description: 管理员添加页
	 * @return
	 * @throws:
	 * @time: 2018年1月15日 上午10:45:35
	 */
	@LogOp(method = "addAdminPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员添加页")
	@RequestMapping(value = "addadminpage")
	public String addAdminPage(Model model) {
		// 生成管理员用户名
		String username = redisService.incr(ConstantIF.USERNAME_KEY);
		model.addAttribute("username", username);
		// 获取所有可用权限
		try {
			List<Role> roles = adminUserMapper.findRoles();
			model.addAttribute("roles", roles);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.ADMIN_ADMIN + "admin-add";
	}

	/**
	 * @Title: addAdmin
	 * @Description: 添加管理员
	 * @param user
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月15日 上午11:37:36
	 */
	@LogOp(method = "addAdmin", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加管理员")
	@RequestMapping(value = "addadmin")
	public @ResponseBody ResultBean addAdmin(UserInfo user, HttpServletRequest request) {
		try {
			// 添加管理员
			adminUserService.addAdminUser(user, request.getParameterValues("roles"));
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	
	/**
	 * @Title: editAdminPage
	 * @Description: 管理员修改页
	 * @return
	 * @throws:
	 * @time: 2018年1月15日 上午10:45:35
	 */
	@LogOp(method = "editAdminPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员修改页")
	@RequestMapping(value = "editadminpage", method=RequestMethod.GET)
	public String editAdminPage(String userid, Model model) {
		try {
			//获取管理员信息
			UserInfo info = adminUserMapper.getUserDataById(userid);
			model.addAttribute("info", info);
			//获取管理员已有权限
			List<String> has_roles = adminUserMapper.getUserRoleById(userid);
			model.addAttribute("has_roles", has_roles);
			// 获取所有可用权限
			List<Role> roles = adminUserMapper.findRoles();
			model.addAttribute("roles", roles);
			return ConstantIF.ADMIN_ADMIN + "admin-edit";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}
	
	/**
	 * @Title: editAdminInfo
	 * @Description: 管理员修改
	 * @param user
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月15日 上午11:37:36
	 */
	@LogOp(method = "editAdminInfo", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员修改")
	@RequestMapping(value = "editadmin", method=RequestMethod.POST)
	public @ResponseBody ResultBean editAdminInfo(UserInfo user, HttpServletRequest request) {
		try {
			// 管理员修改
			adminUserService.editAdminUser(user, request.getParameterValues("roles"));
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	
	

	/**
	 * @Title: editAdminRolePage
	 * @Description: 管理员权限设置页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 下午4:49:39
	 */
	@LogOp(method = "editAdminRolePage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员权限设置页")
	@RequestMapping(value = "editadminrolepage")
	public String editAdminRolePage(@RequestParam String userid, Model model) {
		// 获取管理员权限列表
		try {
			Map<String, List<Role>> role = roleService.getAdminUserRole(userid);
			model.addAttribute("userid", userid);
			model.addAttribute("role", role);
		} catch (SQLExecutorException e1) {
			//跳转错误页面
			return ConstantIF.ERROR_500;
		}
		return ConstantIF.ADMIN_ADMIN + "admin-role-setting";
	}
	
	/**
	 * @Title: delAdminUser
	 * @Description: 删除管理员账号
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年5月18日 上午9:54:26
	 */
	@LogOp(method = "delAdminUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除管理员账号")
	@RequestMapping(value = "deladminuser", method = RequestMethod.POST)
	public @ResponseBody ResultBean delAdminUser(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		List<String> lids = new ArrayList<>();
		
		for(String id : ids) {
			lids.add(id);
		}
		
		try {
			adminUserService.delAdminUser(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
