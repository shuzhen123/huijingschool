package dianfan.controller.admin;

import java.util.List;

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
import dianfan.entities.ResultBean;
import dianfan.entities.Role;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.RoleService;

/**
 * @ClassName RoleManage
 * @Description 权限管理
 * @author cjy
 * @date 2018年1月15日 下午1:13:27
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class RoleManage {

	@Autowired
	private RoleService roleService;
	
	/**
	 * @Title: dashboardAdminRole
	 * @Description: 权限管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午2:46:06
	 */
	@LogOp(method = "dashboardAdminRole", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "权限管理页")
	@RequestMapping(value = "adminrolepage", method = {RequestMethod.GET})
	public String dashboardAdminRole(Model model) {
		try {
			List<Role> roles = roleService.findRoleByStatus(null);
			model.addAttribute("role", roles);
		} catch (SQLExecutorException e) {
			return ConstantIF.ERROR_500;
		}
		return ConstantIF.ADMIN_ROLE + "role-list";
	}
	
	/**
	 * @Title: addAdminRole
	 * @Description: 添加权限
	 * @param rolename
	 * @param rolevalue
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 上午9:57:02
	 */
	@LogOp(method = "addAdminRole", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加权限")
	@RequestMapping(value = "addadminrole", method = {RequestMethod.POST})
	public @ResponseBody ResultBean addAdminRole(@RequestParam String rolename, @RequestParam String rolevalue) {
		//检测空值
		if(rolename == null || "".equals(rolename.trim()) || rolevalue == null || "".equals(rolevalue.trim())) {
			return new ResultBean("501", ResultMsg.C_501);
		}
		String roleid = null;
		try {
			//添加权限
			roleid = roleService.addRole(rolename, rolevalue);
		} catch (SQLExecutorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean(roleid);
	}
	
	/**
	 * @Title: changeRoleStatus
	 * @Description: 更改权限状态
	 * @param roleid
	 * @param status
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 下午1:53:34
	 */
	@LogOp(method = "changeRoleStatus", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "更改权限状态")
	@RequestMapping(value = "changerolestatus", method = {RequestMethod.POST})
	public @ResponseBody ResultBean changeRoleStatus(@RequestParam String roleid, @RequestParam String status) {
		if(status == null || "".equals(status.trim())) {
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		try {
			//更改权限状态
			roleService.changeRoleStatus(roleid, status);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: authRole
	 * @Description: 授权/撤权
	 * @param userid
	 * @param roleid
	 * @param status
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 下午6:14:32
	 */
	@LogOp(method = "changeRoleStatus", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "授权/撤权限")
	@RequestMapping(value = "authrole", method = {RequestMethod.POST})
	public @ResponseBody ResultBean authRole(@RequestParam String userid, @RequestParam String roleid, @RequestParam String action) {
		try {
			//授权/撤权限
			roleService.authRole(userid, roleid, action);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	
	
}
