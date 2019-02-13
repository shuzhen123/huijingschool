package dianfan.controller.admin;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import dianfan.dao.mapper.admin.StatisticsMapper;
import dianfan.entities.BashMap;
import dianfan.entities.Coupon;
import dianfan.entities.CourseOrderCollect;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.StatisticsService;

/**
 * @ClassName StatisticsManage
 * @Description 统计管理
 * @author cjy
 * @date 2018年1月12日 下午2:43:42
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class StatisticsManage {
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private StatisticsMapper statisticsMapper;
	@Autowired
	private RedisService<?,?> redisService;

	/**
	 * @Title: coursePurchaseStatistics
	 * @Description: 课程购买统计管理页
	 * @return
	 * @throws:
	 * @time: 2018年4月11日 下午2:45:01
	 */
	@LogOp(method = "coursePurchaseStatistics", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程购买统计管理页")
	@RequestMapping(value = "coursestatistics", method = RequestMethod.GET)
	public String coursePurchaseStatistics() {
		return ConstantIF.ADMIN_STATISTICS + "course-statistics-index";
	}

	/**
	 * @Title: courseOrderList
	 * @Description: 课程订单列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年4月11日 下午2:55:50
	 */
	@LogOp(method = "courseOrderList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程订单列表")
	@RequestMapping(value = "courseorderlist", method = RequestMethod.POST)
	public @ResponseBody DataTable courseOrderList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
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

			// 课程订单列表
			table = statisticsService.findCourseOrder(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: corsetOrderEditPage
	 * @Description: 课程订单编辑页
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年5月9日 下午6:12:50
	 */
	@LogOp(method = "goodsPurchaseStatistics", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程订单编辑页")
	@RequestMapping(value = "corsetordereditpage", method = RequestMethod.GET)
	public String corsetOrderEditPage(String orderid, String userid, Model model) {
		try {
			//1、查询订单信息
			CourseOrderCollect order = statisticsMapper.getCourseOrderInfo(orderid);
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			//订单有效期
			String time_limit = redisService.get(ConstantIF.COURSE_BY_TIME);
			if (time_limit == null || time_limit.trim().isEmpty()) {
				calendar.add(calendar.DATE, 31);// 把日期往后增加一天.整数往后推,负数往前移动
			}else {
				calendar.add(calendar.DATE, Integer.parseInt(time_limit) + 1);// 把日期往后增加一天.整数往后推,负数往前移动
			}
			order.setValiditytime(new Timestamp(calendar.getTime().getTime()));
			model.addAttribute("order", order);
			//2、查询用户代金券列表
			List<Coupon> coupon = statisticsMapper.findUserCoupon(userid);
			model.addAttribute("coupon", coupon);
			return ConstantIF.ADMIN_STATISTICS + "course-order-edit";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}
	
	/**
	 * @Title: changeCourseOrder
	 * @Description: 未付款课程订单修改
	 * @param order
	 * @return
	 * @throws:
	 * @time: 2018年5月10日 下午4:08:22
	 */
	@LogOp(method = "changeCourseOrderPay", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "未付款课程订单修改")
	@RequestMapping(value = "courseorderpay", method = RequestMethod.POST)
	public @ResponseBody ResultBean changeCourseOrderPay(CourseOrderCollect order) {
		try {
			statisticsService.changeCourseOrderPay(order);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: goodsPurchaseStatistics
	 * @Description:礼物赠送统计管理页
	 * @return
	 * @throws:
	 * @time: 2018年4月18日 上午11:10:35
	 */
	@LogOp(method = "goodsPurchaseStatistics", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "礼物赠送统计管理页")
	@RequestMapping(value = "goodsstatistics", method = RequestMethod.GET)
	public String goodsPurchaseStatistics() {
		return ConstantIF.ADMIN_STATISTICS + "goods-statistics-index";
	}

	/**
	 * @Title: goodsOrderList
	 * @Description: 礼物赠送统订单列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年4月18日 上午11:12:28
	 */
	@LogOp(method = "goodsOrderList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "礼物赠送统订单列表")
	@RequestMapping(value = "goodsorderlist", method = RequestMethod.POST)
	public @ResponseBody DataTable goodsOrderList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 用户账号
			param.put("telno", request.getParameter("telno"));
			// 用户昵称
			param.put("nickname", request.getParameter("nickname"));
			// 订单号
			param.put("orderno", request.getParameter("orderid"));
			// 下单时间
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			// 课程订单列表
			table = statisticsService.findGoodsOrder(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
}
