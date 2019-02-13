package dianfan.controller.saler;

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
import dianfan.entities.DataTable;
import dianfan.entities.Dept;
import dianfan.entities.ResultBean;
import dianfan.entities.Saler;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentStatisticsService;
import dianfan.service.saler.SalerStatisticsService;

/**
 * @ClassName SalerStatisticsManage
 * @Description 报表
 * @author cjy
 * @date 2018年3月6日 下午4:16:40
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/saler")
public class SalerStatisticsManage {
	@Autowired
	private AgentPersonnelMapper agentPersonnelMapper;
	@Autowired
	private AgentCustomerMapper agentCustomerMapper;
	@Autowired
	private AgentStatisticsService agentStatisticsService;
	@Autowired
	private SalerStatisticsService salerStatisticsService;

	/**
	 * @Title: dashboardStatistics
	 * @Description: 直播消费统计页
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 下午2:45:29
	 */
	@LogOp(method = "dashboardStatistics", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播消费统计页")
	@RequestMapping(value = "dashboardstatistics", method = { RequestMethod.GET })
	public String dashboardStatistics() {
		return ConstantIF.AGENT_STA + "order-statistics-saler-list";
	}

	/**
	 * @Title: vipOrderList
	 * @Description: 直播消费列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午1:33:41
	 */
	@LogOp(method = "vipOrderList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播消费列表")
	@RequestMapping(value = "orderlist", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody DataTable vipOrderList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			param.put("saleid", userInfo.getUserid());
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 用户账号
			param.put("telno", request.getParameter("telno"));
			// 用户昵称
			param.put("nickname", request.getParameter("nickname"));
			// 订单号
			param.put("orderno", request.getParameter("orderid"));
			// 支付状态
			param.put("paystatus", request.getParameter("paystatus"));
			// 订单状态
			param.put("entkbn", request.getParameter("ostatus"));
			// 下单时间
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			// 根据条件查询用户列表
			table = salerStatisticsService.findCourseOrderList(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
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
	@RequestMapping(value = "dashboardgoods", method = { RequestMethod.GET })
	public String dashboardGoods() {
		return ConstantIF.AGENT_STA + "goods-order-saler-list";
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
	@RequestMapping(value = "goodsorderlist", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody DataTable goodsOrderList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			String search = request.getParameter(ConstantIF.DT_SEARCH).trim();
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 根据条件查询订单列表
			table = agentStatisticsService.findGoodsOrderList(null, null, null, null, userInfo.getUserid(), search, start, length);
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
	@RequestMapping(value = "dashboardwagecollect", method = { RequestMethod.GET })
	public String dashboardWageCollect(HttpServletRequest request, Model model) {
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			// 获取代理商下的部门
			List<Dept> dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
			model.addAttribute("depts", dept);
			// 获取无部门的员工
			Map<String, String> param = new HashMap<>();
			param.put("deptid", null);
			List<Saler> salers = agentCustomerMapper.findSalerByDeptid(param);
			model.addAttribute("salers", salers.size() > 0 ? salers : null);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_STA + "wage-collect-saler-list";
	}

	/**
	 * @Title: wageCollect
	 * @Description: 业务员提成汇总
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 下午5:08:53
	 */
	@LogOp(method = "wageCollect", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "提成汇总")
	@RequestMapping(value = "wagecollect", method = { RequestMethod.GET })
	public @ResponseBody ResultBean wageCollect(HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
		try {
			Map<String, String> data = agentStatisticsService.wageSalerStatistics(userInfo.getUserid(),
					request.getParameter("starttime"), request.getParameter("endtime"));
			return new ResultBean(data);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

}
