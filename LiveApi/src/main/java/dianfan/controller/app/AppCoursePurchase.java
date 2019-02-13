package dianfan.controller.app;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.CourseOrder;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppCoursePurchaseService;

/**
 * @ClassName AppCoursePurchase
 * @Description 课程订单
 * @author cjy
 * @date 2018年4月3日 上午9:18:58
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppCoursePurchase {
	@Autowired
	private AppCoursePurchaseService coursePurchaseService;
	@Autowired
	private RedisTokenService redisTokenService;

	/**
	 * @Title: myCourseOrder
	 * @Description: 我的订单列表
	 * @param accesstoken
	 * @param type 
	 * @param page
	 * @return
	 * @throws:
	 * @time: 2018年4月9日 下午5:31:59
	 */
	@LogOp(method = "myCourseOrder", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "我的订单列表")
	@ApiOperation(value = "我的订单列表", httpMethod = "POST", notes = "我的订单列表", response = ResultBean.class)
	@RequestMapping(value = "mycourseorder", method = {RequestMethod.POST})
	public @ResponseBody ResultBean myCourseOrder(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("订单类型(-1全部，0未支付，1已支付)") @RequestParam(value = "type", required = true) Integer type,
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue="1", required = false) Integer page) {
		if(page == null || page < 1) {
			page = 1;
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			Map<String, Object> order = coursePurchaseService.myCourseOrder(token.getUserId(), type, page);
			return new ResultBean(order);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: createCourseOrder
	 * @Description: 创建待支付订单
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年4月9日 下午3:06:03
	 */
	@LogOp(method = "createCourseOrder", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "创建待支付订单")
	@ApiOperation(value = "创建待支付订单", httpMethod = "POST", notes = "创建待支付订单", response = ResultBean.class)
	@RequestMapping(value = "createcourseorder", method = {RequestMethod.POST})
	public @ResponseBody ResultBean createCourseOrder(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("课程id连接字符串（id之间以','分隔）") @RequestParam(value = "cids") String cids) {
		String[] cids_arr = null;
		if(cids != null) {
			cids_arr = cids.split(",");
		}
		
		if(cids_arr == null || cids_arr.length < 1) {
			//课程列表不能为空
			return new ResultBean("027", ResultMsg.C_027);
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			String ordernum = coursePurchaseService.createCourseOrder(token.getUserId(), cids_arr);
			return new ResultBean(ordernum);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: delCourseOrder
	 * @Description: 删除订单
	 * @param accesstoken
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年4月10日 上午9:14:12
	 */
	@LogOp(method = "delCourseOrder", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "删除订单")
	@ApiOperation(value = "删除订单", httpMethod = "POST", notes = "删除订单", response = ResultBean.class)
	@RequestMapping(value = "delcourseorder", method = {RequestMethod.POST})
	public @ResponseBody ResultBean delCourseOrder(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("课程订单id") @RequestParam(value = "orderid") String orderid) {
		try {
			TokenModel token = redisTokenService.getToken(accesstoken);
			coursePurchaseService.delCourseOrder(token.getUserId(), orderid);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: courseOrderDetail
	 * @Description: 查看订单详情
	 * @param accesstoken
	 * @param orderid
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月10日 上午9:56:39
	 */
	@LogOp(method = "courseOrderDetail", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "查看订单详情")
	@ApiOperation(value = "查看订单详情", httpMethod = "POST", notes = "查看订单详情", response = ResultBean.class)
	@RequestMapping(value = "courseorderdetail", method = {RequestMethod.POST})
	public @ResponseBody ResultBean courseOrderDetail(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("课程订单id") @RequestParam(value = "orderid") String orderid) throws SQLExecutorException {
		try {
			TokenModel token = redisTokenService.getToken(accesstoken);
			CourseOrder order = coursePurchaseService.getCourseOrderDetail(token.getUserId(), orderid);
			return new ResultBean(order);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: changeCourseOrder
	 * @Description: 删除订单中的课程
	 * @param accesstoken
	 * @param orderid
	 * @param courseid
	 * @return
	 * @throws:
	 * @time: 2018年4月10日 上午11:26:24
	 */
	@LogOp(method = "changeCourseOrder", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "删除订单中的课程")
	@ApiOperation(value = "删除订单中的课程", httpMethod = "POST", notes = "删除订单中的课程", response = ResultBean.class)
	@RequestMapping(value = "changecourseorder", method = {RequestMethod.POST})
	public @ResponseBody ResultBean changeCourseOrder(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("课程订单id") @RequestParam(value = "orderid") String orderid,
			@ApiParam("课程id") @RequestParam(value = "courseid") String courseid) {
		try {
			//删除订单中的课程
			coursePurchaseService.changeCourseOrder(orderid, courseid);
			//更新订单价格
			coursePurchaseService.updateCourseOrderPrice(orderid);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: orderPayment
	 * @Description: 调起支付
	 * @param accesstoken
	 * @param orderid
	 * @param couponid
	 * @param paytype
	 * @param repay
	 * @return
	 * @throws:
	 * @time: 2018年4月10日 下午2:31:08
	 */
	@LogOp(method = "orderPayment", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "调起支付")
	@ApiOperation(value = "调起支付", httpMethod = "POST", notes = "调起支付", response = ResultBean.class)
	@RequestMapping(value = "orderpayment", method = {RequestMethod.POST})
	public @ResponseBody ResultBean orderPayment(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("订单id") @RequestParam(value = "orderid") String orderid,
			@ApiParam("代金券id") @RequestParam(value = "couponid", required = false) String couponid,
			@ApiParam("支付类型（固定值：alipay 支付宝，wxpay 微信，upop 银联）") @RequestParam(value = "paytype") String paytype,
			@ApiParam("支付设备（固定值：app）") @RequestParam(value = "mch", required = false) String mch) {
		try {
			paytype = paytype.trim();
			TokenModel token = redisTokenService.getToken(accesstoken);
			return coursePurchaseService.orderPayment(token.getUserId(), orderid, couponid, paytype, mch);
		} catch (JsonParseException e) {
			//支付响应数据解析出错
			return new ResultBean("500", ResultMsg.C_500);
		} catch (JsonMappingException e) {
			//json数据映射实体类出错
			return new ResultBean("500", ResultMsg.C_500);
		} catch (SQLExecutorException e) {
			//sql执行出错
			return new ResultBean("500", ResultMsg.C_500);
		} catch (IOException e) {
			//读取和讯配置文件出错
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
}
