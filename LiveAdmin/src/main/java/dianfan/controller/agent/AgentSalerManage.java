package dianfan.controller.agent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.google.zxing.WriterException;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.AdminUserMapper;
import dianfan.dao.mapper.agent.AgentBusiMapper;
import dianfan.dao.mapper.agent.AgentPersonnelMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Dept;
import dianfan.entities.Position;
import dianfan.entities.ResultBean;
import dianfan.entities.Saler;
import dianfan.entities.SalerLevel;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.AgentService;
import dianfan.service.agent.AgentPersonnelService;

/**
 * @ClassName AgentSalerManage
 * @Description 代理商业务员管理
 * @author cjy
 * @date 2018年2月7日 下午2:15:15
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentSalerManage {
	@Autowired
	private AgentPersonnelService agentPersonnelService;
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private AgentBusiMapper agentbusiMapper;
	@Autowired
	private AgentService agentService;
	@Autowired
	private AgentPersonnelMapper agentPersonnelMapper;
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: salerManage
	 * @Description: 业务员管理页
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午2:16:26
	 */
	@LogOp(method = "salerManage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员管理页")
	@RequestMapping(value = "salermanage", method = { RequestMethod.GET })
	public String salerManage(HttpSession session, Model model) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			List<Dept> dept;
			List<Position> position;
			if(6 == userInfo.getRole())
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getAgentid());
				// 获取岗位数据
				position = agentPersonnelMapper.findEnabledPosition(userInfo.getAgentid());
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
				// 获取岗位数据
				position = agentPersonnelMapper.findEnabledPosition(userInfo.getUserid());
			}
			model.addAttribute("dept", dept);
			model.addAttribute("position", position);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_SALER + "saler-list";
	}

	/**
	 * @Title: salerList
	 * @Description: 业务员列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午2:18:37
	 */
	@LogOp(method = "salerList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员列表")
	@RequestMapping(value = "salerlist", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody DataTable salerList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("agentid", userInfo.getUserid());
			}
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			// 员工账号
			param.put("username", request.getParameter("username"));
			// 员工姓名
			param.put("realname", request.getParameter("realname"));
			// 所属部门
			param.put("dept", request.getParameter("dept"));
			// 岗位
			param.put("position", request.getParameter("position"));
			// 手机号码
			param.put("telno", request.getParameter("telno"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			// 根据条件查询用户列表
			table = agentPersonnelService.findSaler(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}

	/**
	 * @Title: salerAddPage
	 * @Description: 业务员添加页
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午3:26:40
	 */
	@LogOp(method = "salerAddPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员添加页")
	@RequestMapping(value = "saleraddpage", method = { RequestMethod.GET })
	public String salerAddPage(HttpSession session, Model model) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			// 获取部门和岗位数据
			List<Dept> dept;
			List<Position> position;
			if(6 == userInfo.getRole())
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getAgentid());
				position = agentPersonnelMapper.findEnabledPosition(userInfo.getAgentid());
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
				position = agentPersonnelMapper.findEnabledPosition(userInfo.getUserid());
			}
			model.addAttribute("dept", dept);
			model.addAttribute("position", position);
			// 获取代理商所有可用等级
			Map<String, Object> param = new HashMap<>();
			if(6 == userInfo.getRole())
			{
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("agentid", userInfo.getUserid());
			}
			param.put("start", 0);
			param.put("length", 9999999);
			List<SalerLevel> level = agentbusiMapper.findSalerLevel(param);
			model.addAttribute("level", level);
			// 获取自动生成的用户名
			String username = redisService.incr(ConstantIF.USERNAME_KEY);
			model.addAttribute("username", username);
			return ConstantIF.AGENT_SALER + "saler-add";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: addSaler
	 * @Description: 添加业务员
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午3:49:20
	 */
	@LogOp(method = "addSaler", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加业务员")
	@RequestMapping(value = "saleradd", method = { RequestMethod.POST })
	public @ResponseBody ResultBean addSaler(HttpServletRequest request, UserInfo userinfo) {
		// 部门id
		String deptid = request.getParameter("deptid");
		// 等级id
		String levelid = request.getParameter("levelid");
		// 岗位id
		String positionid = request.getParameter("positionid");
		// 下属部门id
		String[] subdepts = request.getParameterValues("subdepts");
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				agentPersonnelService.addSaler(userInfo.getAgentid(), deptid, levelid, positionid, subdepts, userinfo);
			}
			else
			{
				agentPersonnelService.addSaler(userInfo.getUserid(), deptid, levelid, positionid, subdepts, userinfo);
			}
		} catch (NumberFormatException | SQLExecutorException | WriterException | IOException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: salerDataPage
	 * @Description: 业务员数据页
	 * @param session
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午4:53:16
	 */
	@LogOp(method = "salerDataPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员数据页")
	@RequestMapping(value = "salerdatapage", method = { RequestMethod.GET })
	public String salerDataPage(HttpSession session, Model model, @RequestParam(value = "type") String type,
			@RequestParam(value = "salerid") String salerid) {
		// 获取部门数据
		try {
			// 1、获取业务员数据
			UserInfo salerInfo = adminUserMapper.getUserDataById(salerid);
			model.addAttribute("salerInfo", salerInfo);
			// 2、获取业务员部门和岗位数据
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			Saler salerdept;
			Saler salerposition;
			if(6 == userInfo.getRole())
			{
				salerdept = agentPersonnelService.getSalerData(userInfo.getAgentid(), salerid);
				salerposition = agentPersonnelService.getSalerPositionData(userInfo.getAgentid(), salerid);
			}
			else
			{
				salerdept = agentPersonnelService.getSalerData(userInfo.getUserid(), salerid);
				salerposition = agentPersonnelService.getSalerPositionData(userInfo.getUserid(), salerid);
			}
			model.addAttribute("salerdept", salerdept);
			model.addAttribute("salerposition", salerposition);
			// 2、获取业务员等级
			SalerLevel salerLevel = agentbusiMapper.findSalerLevelById(salerid);
			model.addAttribute("levelinfo", salerLevel);

			// 3、获取代理商部门列表
			if ("edit".equals(type.trim())) {
				//获取业务员已有下属部门
				if(salerposition != null && salerposition.getPositionid() != null && !"".equals(salerposition.getPositionid().trim()))
				{
				    List<String> has_subdepts = agentPersonnelMapper.findSalerSubdepts(salerid);
				    model.addAttribute("has_subdepts", has_subdepts);
				}
				
				List<Dept> dept;
				List<Position> position;
				if(6 == userInfo.getRole())
				{
					dept = agentPersonnelMapper.findEnabledDept(userInfo.getAgentid());
					position = agentPersonnelMapper.findEnabledPosition(userInfo.getAgentid());
				}
				else
				{
					dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
					position = agentPersonnelMapper.findEnabledPosition(userInfo.getUserid());
				}
				model.addAttribute("dept", dept);
				model.addAttribute("position", position);
				// 获取代理商所有可用等级
				Map<String, Object> param = new HashMap<>();
				if(6 == userInfo.getRole())
				{
					param.put("agentid", userInfo.getAgentid());
				}
				else
				{
					param.put("agentid", userInfo.getUserid());
				}
				param.put("start", 0);
				param.put("length", 9999999);
				List<SalerLevel> level = agentbusiMapper.findSalerLevel(param);
				model.addAttribute("level", level);
				return ConstantIF.AGENT_SALER + "saler-edit";
			}
			return ConstantIF.AGENT_SALER + "saler-detail";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: editSaler
	 * @Description: 修改业务员信息
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午5:29:42
	 */
	@LogOp(method = "editSaler", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改业务员信息")
	@RequestMapping(value = "editsaler", method = { RequestMethod.POST })
	public @ResponseBody ResultBean editSaler(UserInfo userinfo, HttpServletRequest request) {
		// 部门id
		String deptid = request.getParameter("deptid");
		// levelid
		String levelid = request.getParameter("levelid");
		// 岗位id
		String positionid = request.getParameter("positionid");
		// 下属部门id
		String[] subdepts = request.getParameterValues("subdepts");
		// 获取部门数据
		try {
			agentPersonnelService.editSaler(deptid, levelid, positionid, subdepts, userinfo);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: delSaler
	 * @Description: 删除业务员
	 * @param session
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午4:36:15
	 */
	@LogOp(method = "delSaler", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除业务员")
	@RequestMapping(value = "delsaler", method = RequestMethod.POST)
	public @ResponseBody ResultBean delSaler(HttpSession session, @RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		List<String> lids = new ArrayList<>();
		for (String id : ids) {
			lids.add(id);
		}
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				agentPersonnelService.delSaler(lids, userInfo.getAgentid());
			}
			else
			{
				agentPersonnelService.delSaler(lids, userInfo.getUserid());
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: salerTaskManage
	 * @Description: 员工任务页
	 * @return
	 * @throws:
	 * @time: 2018年2月24日 上午9:21:50
	 */
	@LogOp(method = "salerTaskManage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员任务管理页")
	@RequestMapping(value = "salertaskmanage", method = { RequestMethod.GET })
	public String salerTaskManage() {
		return ConstantIF.AGENT_SALER + "saler-task";
	}

	/**
	 * @Title: salerTaskList
	 * @Description: 业务员任务列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月24日 上午10:14:34
	 */
	@LogOp(method = "salerTaskList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员任务列表")
	@RequestMapping(value = "salertasklist", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResultBean salerTaskList(HttpServletRequest request) {
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			int year = Integer.valueOf(request.getParameter("year"));
			int month = Integer.valueOf(request.getParameter("month"));
			// 根据条件查询用户列表
			Map<String, List> map;
			if(6 == userInfo.getRole())
			{
				map = agentPersonnelService.findSalerTaskList(userInfo.getAgentid(), year, month);
			}
			else
			{
				map = agentPersonnelService.findSalerTaskList(userInfo.getUserid(), year, month);
			}
			return new ResultBean(map);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
}
