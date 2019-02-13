package dianfan.controller.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.agent.AgentCustomerMapper;
import dianfan.dao.mapper.agent.AgentPersonnelMapper;
import dianfan.dao.mapper.agent.AgentStatisticsMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Dept;
import dianfan.entities.Reperecs;
import dianfan.entities.ResultBean;
import dianfan.entities.Saler;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentStatisticsService;

/**
 * @ClassName StatisticsManage
 * @Description 代理商报表
 * @author cjy
 * @date 2018年2月12日 下午1:26:43
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentStatisticsManage {
	@Autowired
	private AgentPersonnelMapper agentPersonnelMapper;
	@Autowired
	private AgentCustomerMapper agentCustomerMapper;
	@Autowired
	private AgentStatisticsService agentStatisticsService;
	@Autowired
	private AgentStatisticsMapper agentStatisticsMapper;
	/**
	 * @Title: dashboardStatistics
	 * @Description: 课程消费统计页
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 下午2:45:29
	 */
	@LogOp(method = "dashboardStatistics", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程消费统计页")
	@RequestMapping(value = "orderstatistics", method = {RequestMethod.GET})
	public String dashboardStatistics(HttpServletRequest request, Model model) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
		try {
			// 获取代理商下的部门
			List<Dept> dept;
			if(6 == userInfo.getRole())
			{
				Map<String, String> param = new HashMap<>();
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
				dept = agentPersonnelMapper.findEnabledDeptByPosition(param);
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
			}
			model.addAttribute("depts", dept);
		} catch (SQLExecutorException e) {
		}
		return ConstantIF.AGENT_STA + "order-statistics-list";
	}
	
	/**
	 * @Title: vipOrderList
	 * @Description: 课程消费订单列表 
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午1:33:41
	 */
	@LogOp(method = "courseOrderList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程消费订单列表")
	@RequestMapping(value = "orderlist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DataTable courseOrderList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("userid", null);
				param.put("agentid", userInfo.getUserid());
			}
			param.put("searchdeptid", request.getParameter("searchdeptid"));
			param.put("searchsalerid", request.getParameter("searchsalerid"));
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			//用户账号
			param.put("telno", request.getParameter("telno"));
			//用户昵称
			param.put("nickname", request.getParameter("nickname"));
			//业务员昵称
			param.put("realname", request.getParameter("realname"));
			//订单号
			param.put("orderno", request.getParameter("orderid"));
			//支付状态
			param.put("paystatus", request.getParameter("paystatus"));
			//订单状态
			param.put("entkbn", request.getParameter("ostatus"));
			//下单时间
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			
			// 根据条件查询用户列表
			table = agentStatisticsService.findCourseOrderList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: dashboardGoods
	 * @Description: 礼物消费统计页
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午3:36:52
	 */
	@LogOp(method = "dashboardGoods", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "礼物消费统计页")
	@RequestMapping(value = "dashboardgoods", method = {RequestMethod.GET})
	public String dashboardGoods(HttpServletRequest request, Model model) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
		try {
			// 获取代理商下的部门
			List<Dept> dept;
			if(6 == userInfo.getRole())
			{
				Map<String, String> param = new HashMap<>();
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
				dept = agentPersonnelMapper.findEnabledDeptByPosition(param);
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
			}
			model.addAttribute("depts", dept);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_STA + "goods-order-list";
	}
	
	/**
	 * @Title: goodsOrderList
	 * @Description: 礼物消费订单列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午3:39:03
	 */
	@LogOp(method = "goodsOrderList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "礼物消费订单列表")
	@RequestMapping(value = "goodsorderlist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DataTable goodsOrderList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			String search = request.getParameter(ConstantIF.DT_SEARCH).trim();
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			String searchdeptid = request.getParameter("searchdeptid");
			String searchsalerid = request.getParameter("searchsalerid");
			// 根据条件查询订单列表
			if(6 == userInfo.getRole())
			{
				table = agentStatisticsService.findGoodsOrderList(searchdeptid, searchsalerid, userInfo.getUserid(), userInfo.getAgentid(), null, search, start, length);
			}
			else
			{
				table = agentStatisticsService.findGoodsOrderList(searchdeptid, searchsalerid, null, userInfo.getUserid(), null, search, start, length);
			}
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: dashboardWageCollect
	 * @Description: 提成汇总页
	 * @return
	 * @throws:
	 * @time: 2018年2月26日 下午3:58:55
	 */
	@LogOp(method = "dashboardWageCollect", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "提成汇总页")
	@RequestMapping(value = "dashboardwagecollect", method = {RequestMethod.GET})
	public String dashboardWageCollect(HttpServletRequest request, Model model) {
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			//获取代理商下的部门
			List<Dept> dept;
			if(6 == userInfo.getRole())
			{
				Map<String, String> param = new HashMap<>();
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
				dept = agentPersonnelMapper.findEnabledDeptByPosition(param);
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
			}
			model.addAttribute("depts", dept);
			//获取无部门的员工
			Map<String, String> param = new HashMap<>();
			if(6 == userInfo.getRole())
			{
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("agentid", userInfo.getUserid());
			}
			param.put("deptid", null);
			List<Saler> salers = agentCustomerMapper.findSalerByDeptid(param);
			model.addAttribute("salers", salers.size() > 0 ? salers : null);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_STA + "wage-collect-list";
	}
	
	/**
	 * @Title: wageCollect
	 * @Description: 提成汇总
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 下午5:08:53
	 */
	@LogOp(method = "wageCollect", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "提成汇总")
	@RequestMapping(value = "wagecollect", method = {RequestMethod.GET})
	public @ResponseBody ResultBean wageCollect(HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
		try {
			Map<String, Object> data;
			if(6 == userInfo.getRole())
			{
				data = agentStatisticsService.wageStatistics(
						userInfo.getAgentid(), 
						userInfo.getUserid(), 
						request.getParameter("starttime"), 
						request.getParameter("endtime"), 
						request.getParameter("deptid"), request.getParameter("salerid"));
			}
			else
			{
				data = agentStatisticsService.wageStatistics(
						userInfo.getUserid(), 
						null,
						request.getParameter("starttime"), 
						request.getParameter("endtime"), 
						request.getParameter("deptid"), request.getParameter("salerid"));
			}
			return new ResultBean(data);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: dashboardSalerViolator
	 * @Description: 业务员合规处理列表页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 上午11:20:54
	 */
	@LogOp(method = "dashboardSalerViolator", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员合规处理列表页")
	@RequestMapping(value = "dashboardsalerviolator", method = {RequestMethod.GET})
	public String dashboardSalerViolator(HttpServletRequest request, Model model) {
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			//获取代理商下的部门
			List<Dept> dept;
			if(6 == userInfo.getRole())
			{
				Map<String, String> param = new HashMap<>();
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
				dept = agentPersonnelMapper.findEnabledDeptByPosition(param);
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
			}
			model.addAttribute("depts", dept);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_STA + "saler-violator-list";
	}
	
	/**
	 * @Title: salerviolatorList
	 * @Description: 业务员合规处理汇总列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午2:40:39
	 */
	@LogOp(method = "goodsOrderList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员合规处理汇总列表")
	@RequestMapping(value = "salerviolatorlist", method = {RequestMethod.POST})
	public @ResponseBody DataTable salerviolatorList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("userid", null);
				param.put("agentid", userInfo.getUserid());
			}
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			
			param.put("deptid", request.getParameter("deptid"));
			param.put("salerid", request.getParameter("salerid"));
			
			// 根据条件查询订单列表
			table = agentStatisticsService.findSalerViolatorList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: salerViolatorDetail
	 * @Description: 业务员合规处理详情页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午4:18:58
	 */
	@LogOp(method = "salerViolatorDetail", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员合规处理详情页")
	@RequestMapping(value = "salerviolatordetail", method = {RequestMethod.GET})
	public String salerViolatorDetail(HttpServletRequest request, Model model) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("salerid", request.getParameter("salerid"));
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			List<Reperecs> violator = agentStatisticsMapper.salerViolatorDetail(param);
			model.addAttribute("violator", violator);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_STA + "violator-list";
	}

	/**
	 * @Title: talktimeStatistics
	 * @Description: 业务员通话时长列表页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 上午11:20:54
	 */
	@LogOp(method = "talktimeStatistics", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员通话时长列表页")
	@RequestMapping(value = "talktimestatistics", method = {RequestMethod.GET})
	public String talktimeStatistics(HttpServletRequest request, Model model) {
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			//获取代理商下的部门
			List<Dept> dept;
			if(6 == userInfo.getRole())
			{
				Map<String, String> param = new HashMap<>();
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
				dept = agentPersonnelMapper.findEnabledDeptByPosition(param);
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
			}
			model.addAttribute("depts", dept);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_STA + "talktime-statistics-list";
	}
	
	/**
	 * @Title: talktimeStatisticsList
	 * @Description: 业务员通话时长汇总列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午2:40:39
	 */
	@LogOp(method = "talktimeStatisticsList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员合规处理汇总列表")
	@RequestMapping(value = "talktimestatisticslist", method = {RequestMethod.POST})
	public @ResponseBody DataTable talktimeStatisticsList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("userid", null);
				param.put("agentid", userInfo.getUserid());
			}
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			
			param.put("deptid", request.getParameter("deptid"));
			param.put("salerid", request.getParameter("salerid"));
			
			// 根据条件查询订单列表
			table = agentStatisticsService.findTalktimestatisticsList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
}
