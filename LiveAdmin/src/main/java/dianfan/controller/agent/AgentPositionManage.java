package dianfan.controller.agent;

import java.util.ArrayList;
import java.util.List;

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
import dianfan.dao.mapper.agent.AgentPersonnelMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Position;
import dianfan.entities.ResultBean;
import dianfan.entities.Role;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentPersonnelService;

/**
 * @ClassName AgentPositionManage
 * @Description 代理商管理岗位
 * @author zhusun
 * @date 2018年5月20日
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentPositionManage {
	@Autowired
	private AgentPersonnelService agentPersonnelService;
	@Autowired
	private AgentPersonnelMapper agentPersonnelMapper;
	/**
	 * @Title: positionManage
	 * @Description: 管理岗位列表页
	 * @return
	 * @throws:
	 * @time: 2018年5月20日
	 */
	@LogOp(method = "positionManage",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理岗位列表页")
	@RequestMapping(value = "positionmanage", method = {RequestMethod.GET})
	public String positionManage(Model model) {
		// 获取所有可用权限
		try {
			List<Role> agent_roles = agentPersonnelMapper.findAgentRoles();
			model.addAttribute("agent_roles", agent_roles);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_SALER + "position-list";
	}
	
	/**
	 * @Title: positionList
	 * @Description: 管理岗位列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午9:50:04
	 */
	@LogOp(method = "positionList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理岗位列表")
	@RequestMapping(value = "positionlist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DataTable positionList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 根据条件查询用户列表
			if(6 == userInfo.getRole())
			{
				table = agentPersonnelService.findPosition(userInfo.getAgentid(), start, length);
			}
			else
			{
				table = agentPersonnelService.findPosition(userInfo.getUserid(), start, length);
			}
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: addPositonPage
	 * @Description: 岗位添加页
	 * @return
	 * @throws:
	 * @time: 2018年1月15日 上午10:45:35
	 */
	@LogOp(method = "addPositonPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "岗位添加页")
	@RequestMapping(value = "addpositonpage")
	public String addPositonPage(Model model) {
		// 获取所有可用权限
		try {
			List<Role> agent_roles = agentPersonnelMapper.findAgentRoles();
			model.addAttribute("agent_roles", agent_roles);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_SALER + "position-add";
	}
	
	/**
	 * @Title: addPositon
	 * @Description: 添加岗位
	 * @param session
	 * @param name
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午10:24:44
	 */
	@LogOp(method = "addPositon", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加岗位")
	@RequestMapping(value = "addpositon", method=RequestMethod.POST)
	public @ResponseBody ResultBean addPositon(HttpSession session, HttpServletRequest request) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			String positionname = request.getParameter("positionname");
			if(6 == userInfo.getRole())
			{
				return agentPersonnelService.addNewPosition(positionname, userInfo.getAgentid(), request.getParameterValues("roles"));
			}
			else
			{
				return agentPersonnelService.addNewPosition(positionname, userInfo.getUserid(), request.getParameterValues("roles"));
			}
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: editPositionPage
	 * @Description: 岗位修改页
	 * @return
	 * @throws:
	 * @time: 2018年1月15日 上午10:45:35
	 */
	@LogOp(method = "editPositionPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "岗位修改页")
	@RequestMapping(value = "editpositionPage", method=RequestMethod.GET)
	public String editPositionPage(String positionid, Model model) {
		try {
			Position position = agentPersonnelMapper.findPositionById(positionid);
			model.addAttribute("position", position);
			model.addAttribute("old_positionname", position.getPositionname());
			//获取岗位已有权限
			List<String> has_roles = agentPersonnelMapper.findPositionRoles(positionid);
			model.addAttribute("has_roles", has_roles);
			// 获取所有可用权限
			List<Role> agent_roles = agentPersonnelMapper.findAgentRoles();
			model.addAttribute("agent_roles", agent_roles);
			return ConstantIF.AGENT_SALER + "position-edit";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}
	
	/**
	 * @Title: editPosition
	 * @Description: 修改岗位
	 * @param session
	 * @param position
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:03:56
	 */
	@LogOp(method = "editPosition", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "岗位修改")
	@RequestMapping(value = "editposition", method=RequestMethod.POST)
	public @ResponseBody ResultBean editPosition(HttpSession session, HttpServletRequest request, Position position, String old_positionname) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				position.setCreateid(userInfo.getAgentid());
			}
			else
			{
				position.setCreateid(userInfo.getUserid());
			}
			return agentPersonnelService.editPosition(position, old_positionname, request.getParameterValues("roles"));
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: changePositionStatus
	 * @Description: 岗位停、启用
	 * @param session
	 * @param status 1启用/0禁用
	 * @param positionid 当前岗位id
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:48:08
	 */
	@LogOp(method = "changePositionStatus", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "岗位停、启用")
	@RequestMapping(value = "changepositionstatus", method=RequestMethod.POST)
	public @ResponseBody ResultBean changePositionStatus(HttpSession session, 
			@RequestParam(value="status") int status, 
			@RequestParam(value="positionid") String positionid) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				agentPersonnelService.changePositionStatus(userInfo.getAgentid(), status, positionid);
			}
			else
			{
				agentPersonnelService.changePositionStatus(userInfo.getUserid(), status, positionid);
			}
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: delPosition
	 * @Description: 删除岗位
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午1:59:23
	 */
	@LogOp(method = "delPosition", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除岗位")
	@RequestMapping(value = "delposition", method=RequestMethod.POST)
	public @ResponseBody ResultBean delPosition(HttpSession session, @RequestParam(value = "ids[]") String[] ids) {
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
				agentPersonnelService.delPosition(lids, userInfo.getAgentid());
			}
			else
			{
				agentPersonnelService.delPosition(lids, userInfo.getUserid());
			}
			
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

}
