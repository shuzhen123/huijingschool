package dianfan.controller.notify;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;

import dainfan.hexun.entity.notify.ResponseCode;
import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.entities.CourseOrder;
import dianfan.exception.SQLExecutorException;
import dianfan.service.PayNotifyService;

/**
 * @ClassName PayNotify
 * @Description 和讯支付服务异步通知接口
 * @author cjy
 * @date 2018年4月11日 上午9:29:58
 */
@Scope("request")
@Controller
@RequestMapping(value = "/notify/pay")
public class CourseNotify {
	@Autowired
	private PayNotifyService payNotifyService;

	// public static Logger log = Logger.getLogger(CourseNotify.class);

	/**
	 * @Title: courseNotify
	 * @Description: 课程购买交易支付服务异步通知
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年4月11日 上午9:22:33
	 */
	@LogOp(method = "courseNotify", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程购买交易支付服务异步通知")
	@ApiOperation(value = "课程购买交易支付服务异步通知", httpMethod = "", notes = "课程购买交易支付服务异步通知", response = String.class)
	@RequestMapping(value = "coursenotify", method = { RequestMethod.POST, RequestMethod.GET })
	@UnCheckedFilter
	public @ResponseBody String courseNotify(HttpServletRequest request) {
		// 提取通知数据
		@SuppressWarnings("unchecked")
		Map<String, String[]> requestData = request.getParameterMap();
		Map<String, String> data = new HashMap<>();
		for (Map.Entry<String, String[]> m : requestData.entrySet()) {
			data.put(m.getKey(), m.getValue()[0]);
		}
		try {
			boolean pass = payNotifyService.checkSign(data);
			if (!pass) {
				// 验签未通过
				return ResponseCode.FAILD;
			}
		} catch (SQLExecutorException e) {
			return ResponseCode.FAILD;
		}
		// 验证通知数据
		try {
			Map<String, String> valid = payNotifyService.checkOrder(data, "course");
			if (ResponseCode.FAILD.equals(valid.get("result"))) {
				// 验证失败
				return ResponseCode.FAILD;
			}
		} catch (SQLExecutorException e) {
			// TODO 系统日志
			return ResponseCode.FAILD;
		}
		
		try {
			//更新vip订单、添加分润数据
			CourseOrder order = payNotifyService.updateCourseOrder(data.get("outer_trade_no"));
			//更新用户vip等级信息
			payNotifyService.updateUserVipLevelInfo(order);
			//更新缓存
			payNotifyService.updateUserCache(order.getUserid());
			
			return ResponseCode.SUCCESS;
		} catch (SQLExecutorException e) {
			// TODO 订单更新失败
			// log.error("订单更新失败");
			return ResponseCode.FAILD;
		}
	}

}
