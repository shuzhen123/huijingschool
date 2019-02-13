package dianfan.controller.agent;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.agent.AgentPersonnelMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Dept;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentPersonnelService;

/**
 * @ClassName AgentDeptManage
 * @Description 代理商部门管理
 * @author cjy
 * @date 2018年2月7日 上午9:33:43
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentDeptManage {
	@Autowired
	private AgentPersonnelService agentPersonnelService;
	@Autowired
	private AgentPersonnelMapper agentPersonnelMapper;
	/**
	 * @Title: deptManage
	 * @Description: 部门列表页
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午9:35:59
	 */
	@LogOp(method = "deptManage",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "部门列表页")
	@RequestMapping(value = "deptmanage", method = {RequestMethod.GET})
	public String deptManage() {
		return ConstantIF.AGENT_SALER + "dept-list";
	}
	
	/**
	 * @Title: deptList
	 * @Description: 代理商部门列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午9:50:04
	 */
	@LogOp(method = "deptList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商部门列表")
	@RequestMapping(value = "deptlist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DataTable deptList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 根据条件查询用户列表
			if(6 == userInfo.getRole())
			{
				table = agentPersonnelService.findDept(userInfo.getAgentid(), start, length);
			}
			else
			{
				table = agentPersonnelService.findDept(userInfo.getUserid(), start, length);
			}
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: addDept
	 * @Description: 添加新部门
	 * @param session
	 * @param name
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午10:24:44
	 */
	@LogOp(method = "addDept", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加新部门")
	@RequestMapping(value = "adddept", method=RequestMethod.POST)
	public @ResponseBody ResultBean addDept(HttpSession session, @RequestParam(value = "name") String name) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				return agentPersonnelService.addNewDept(name, userInfo.getAgentid());
			}
			else
			{
				return agentPersonnelService.addNewDept(name, userInfo.getUserid());
			}
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: editDept
	 * @Description: 部门修改
	 * @param session
	 * @param dept
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:03:56
	 */
	@LogOp(method = "editDept", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "部门修改")
	@RequestMapping(value = "editdept", method=RequestMethod.POST)
	public @ResponseBody ResultBean editDept(HttpSession session, Dept dept) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				dept.setCreateid(userInfo.getAgentid());
			}
			else
			{
				dept.setCreateid(userInfo.getUserid());
			}
			return agentPersonnelService.editDept(dept);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: getEnabledDept
	 * @Description: 获取可用部门列表
	 * @param session
	 * @param dept
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:37:09
	 */
	@LogOp(method = "getEnabledDept", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取可用部门列表")
	@RequestMapping(value = "getenableddept", method=RequestMethod.POST)
	public @ResponseBody ResultBean getEnabledDept(HttpSession session) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			List<Dept> dept;
			if(6 == userInfo.getRole())
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getAgentid());
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
			}
			return new ResultBean(dept);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
	/**
	 * @Title: changeDeptStatus
	 * @Description: 部门停、启用
	 * @param session
	 * @param status 1启用/0禁用
	 * @param deptid 当前部门id
	 * @param tardeptid 当禁用部门时，部门下的业务员移至当前部门下
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:48:08
	 */
	@LogOp(method = "changeDeptStatus", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "部门停、启用")
	@RequestMapping(value = "editstatus", method=RequestMethod.POST)
	public @ResponseBody ResultBean changeDeptStatus(HttpSession session, 
			@RequestParam(value="status") int status, 
			@RequestParam(value="deptid") String deptid, 
			@RequestParam(value="tardeptid", required = false) String tardeptid) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				agentPersonnelService.changeDeptStatus(userInfo.getAgentid(), status, deptid, tardeptid);
			}
			else
			{
				agentPersonnelService.changeDeptStatus(userInfo.getUserid(), status, deptid, tardeptid);
			}
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: delDept
	 * @Description: 删除部门
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午1:59:23
	 */
	@LogOp(method = "delDept", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除部门")
	@RequestMapping(value = "deldept", method=RequestMethod.POST)
	public @ResponseBody ResultBean delDept(HttpSession session, @RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		List<String> lids = new ArrayList<>();
		for(String id : ids) {
			lids.add(id);
		}
		// 根据课程id删除课程
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				agentPersonnelService.delDept(lids, userInfo.getAgentid());
			}
			else
			{
				agentPersonnelService.delDept(lids, userInfo.getUserid());
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
