package dianfan.controller.agent;

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

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.agent.AgentBusiMapper;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.SalerLevel;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.agent.AgentBusiService;

/**
 * @ClassName TaskManage
 * @Description 业务管理
 * @author cjy
 * @date 2018年2月23日 下午2:15:40
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentBusiManage {
	@Autowired
	private RedisService<?,?> redisService;
	@Autowired
	private AgentBusiService agentBusiService;
	@Autowired
	private AgentBusiMapper agentBusiMapper;
	
	/**
	 * @Title: dashboardSalerLevel
	 * @Description: 业务员等级管理页
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 下午2:18:05
	 */
	@LogOp(method = "dashboardSalerLevel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员等级管理页")
	@RequestMapping(value = "dashboardsalerlevel", method = {RequestMethod.GET})
	public String dashboardSalerLevel(Model model, HttpSession session) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		//获取当前代理商默认任务数量
		String num;
		if(6 == userInfo.getRole())
		{
			num = redisService.get(ConstantIF.DEF_TASK_KEY + userInfo.getAgentid());
		}
		else
		{
			num = redisService.get(ConstantIF.DEF_TASK_KEY + userInfo.getUserid());
		}
    	if(num == null || num.trim().isEmpty()) {
    		num = "0";
    	}
    	model.addAttribute("count", num);
		return ConstantIF.AGENT_SALER + "saler-level-list";
	}
	
	/**
	 * @Title: salerLevelList
	 * @Description: 等级列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 下午3:18:04
	 */
	@LogOp(method = "salerLevelList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "等级列表")
	@RequestMapping(value = "salerlevellist", method = {RequestMethod.POST})
	public @ResponseBody DataTable salerLevelList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			Map<String, Object> param = new HashMap<>();
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
			// 等级列表
			table = agentBusiService.findSalerLevel(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: addSalerLevel
	 * @Description: 添加等级
	 * @param salerLevel
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 下午3:54:40
	 */
	@LogOp(method = "addSalerLevel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加等级")
	@RequestMapping(value = "addsalerlevel", method = {RequestMethod.POST})
	public @ResponseBody ResultBean addSalerLevel(SalerLevel salerLevel, HttpSession session) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		try {
			salerLevel.setLevelname(salerLevel.getLevelname().trim());
			if(6 == userInfo.getRole())
			{
				salerLevel.setAgentid(userInfo.getAgentid());
			}
			else
			{
				salerLevel.setAgentid(userInfo.getUserid());
			}
			//检测等级名称
			int count = agentBusiMapper.findCountByLevelName(salerLevel);
			if(count > 0) {
				//存在相同名称
				return new ResultBean("020", ResultMsg.C_020);
			}
			//检测通过
			agentBusiService.addSalerLevel(salerLevel);
		} catch (SQLExecutorException e) {
			// TODO 错误处理
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: editSalerLevel
	 * @Description: 修改等级
	 * @param salerLevel
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 下午4:35:42
	 */
	@LogOp(method = "editSalerLevel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改等级")
	@RequestMapping(value = "editsalerlevel", method = {RequestMethod.POST})
	public @ResponseBody ResultBean editSalerLevel(SalerLevel salerLevel, HttpSession session) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		try {
			salerLevel.setLevelname(salerLevel.getLevelname().trim());
			if(6 == userInfo.getRole())
			{
				salerLevel.setAgentid(userInfo.getAgentid());
			}
			else
			{
				salerLevel.setAgentid(userInfo.getUserid());
			}
			//检测等级名称
			int count = agentBusiMapper.findCountByLevelId(salerLevel);
			if(count > 0) {
				//存在相同名称
				return new ResultBean("020", ResultMsg.C_020);
			}
			//检测通过
			agentBusiService.editSalerLevel(salerLevel);
		} catch (SQLExecutorException e) {
			// TODO 错误处理
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: setDefSalerCount
	 * @Description: 修改默认任务数
	 * @param salerLevel
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 下午5:11:34
	 */
	@LogOp(method = "setDefSalerCount", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改默认任务数")
	@RequestMapping(value = "setdefsalercount", method = {RequestMethod.POST})
	public @ResponseBody ResultBean setDefSalerCount(@RequestParam(value = "count") String count) {
		redisService.set(ConstantIF.DEF_TASK_KEY, count);
		return new ResultBean();
	}
	
	/**
	 * @Title: delSalerLevel
	 * @Description: 删除等级
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 下午4:44:05
	 */
	@LogOp(method = "delSalerLevel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除等级")
	@RequestMapping(value = "delsalerlevel", method = {RequestMethod.POST})
	public @ResponseBody ResultBean delSalerLevel(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		List<String> lids = new ArrayList<>();
		
		for(String id : ids) {
			lids.add(id);
		}
		
		try {
			agentBusiService.delSalerLevel(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
