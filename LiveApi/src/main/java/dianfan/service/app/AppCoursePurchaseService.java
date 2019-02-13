package dianfan.service.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dainfan.hexun.core.HexunPay;
import dainfan.hexun.entity.HexunInstantTradeData;
import dainfan.hexun.utils.HexunProperty;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppCoursePurchaseMapper;
import dianfan.entities.CartCourse;
import dianfan.entities.CourseOrder;
import dianfan.entities.CourseOrderList;
import dianfan.entities.HexunPayResult;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.util.GenRandomNumUtil;
import dianfan.util.HttpClientHelper;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AppCoursePurchaseService
 * @Description 课程订单服务
 * @author cjy
 * @date 2018年4月10日 上午10:03:06
 */
@Service
public class AppCoursePurchaseService {

	@Autowired
	private AppCoursePurchaseMapper coursePurchaseMapper;
	
	public static Logger log = Logger.getLogger(AppCoursePurchaseService.class);
	
	public Map<String, Object> myCourseOrder(String userid, Integer type, Integer page) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();
		//条件
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("paystatus", type);
		
		//根据条件获取课程订单总数
		int count = coursePurchaseMapper.findCourseOrderCount(param);
		// 总页数
		int totalPage;
		if (count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count / ConstantIF.PAGE_OFFSET + 1;
		} else {
			totalPage = count / ConstantIF.PAGE_OFFSET;
		}

		// 总页数
		data.put("totalpage", totalPage);
		// 当前页
		data.put("currentpage", page);

		// 查看是否已超总页数
		if (totalPage < page) {
			data.put("orderlist", new ArrayList<>());
			return data;
		}
		
		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);
		
		//根据条件获取订单列表
		List<CourseOrderList> orders = coursePurchaseMapper.findCourseOrderData(param);
		for(CourseOrderList col : orders) {
			col.setCoursepic(ConstantIF.PROJECT + col.getCoursepic());
			
			if(col.getPaystatus() == 0 && col.getEndpaytime().getTime() <= System.currentTimeMillis()) {
				//未支付订单已过期
				col.setPaystatus(2);
			}
			
			if(col.getPaystatus() == 0 && col.getEndpaytime().getTime() > System.currentTimeMillis()) {
				//计算支付截止时间（秒）
				col.setSecond((col.getEndpaytime().getTime() - System.currentTimeMillis())/1000);
			}
		}
		
		data.put("orderlist", orders);
		return data;
	}

	/**
	 * @Title: createCourseOrder
	 * @Description: 创建待支付订单
	 * @param userid
	 * @param cids
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月9日 下午3:11:10
	 */
	public String createCourseOrder(String userid, String[] cids) throws SQLExecutorException {
		//1、创建代付款订单
		CourseOrder order = new CourseOrder();
		order.setOrderno(GenRandomNumUtil.getOrderNo());
		order.setUserid(userid);
		//根据课程id获取订单总金额
		BigDecimal price = coursePurchaseMapper.getPriceByCourseids(cids);
		order.setMoney(price);
		//订单创建时间
		order.setCreatetime(new Timestamp(System.currentTimeMillis()));
		//支付截止时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, +ConstantIF.PAY_TIME_OUT);
		order.setEndpaytime(new Timestamp(cal.getTime().getTime()));
		coursePurchaseMapper.createCourseOrder(order);
		
		//2、写入订单与课程关系
		Map<String, Object> data = new HashMap<>();
		data.put("orderno", order.getOrderno());
		data.put("cids", cids);
		coursePurchaseMapper.createCourseOrderRelation(data);
		
		//3、清空购物车中的选中课程
		data.clear();
		data.put("userid", userid);
		data.put("cids", cids);
		coursePurchaseMapper.cleanCart(data);
		
		return order.getOrderno();
	}

	/**
	 * @Title: delCourseOrder
	 * @Description: 删除订单
	 * @param userid
	 * @param orderid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月10日 上午9:14:30
	 */
	@Transactional
	public void delCourseOrder(String userid, String orderid) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("userid", userid);
		param.put("orderid", orderid);
		coursePurchaseMapper.delCourseOrder(param);
	}

	/**
	 * @Title: getCourseOrderDetail
	 * @Description: 查看订单详情
	 * @param userId
	 * @param orderid
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月10日 上午9:59:36
	 */
	public CourseOrder getCourseOrderDetail(String userid, String orderid) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("userid", userid);
		param.put("orderid", orderid);
		CourseOrder order = coursePurchaseMapper.getCourseOrderDetail(param);
		if(order != null) {
			for(CartCourse cc : order.getCourselist()) {
				cc.setCoursepic(ConstantIF.PROJECT + cc.getCoursepic());
			}
		}
		if(order.getPaystatus() == 0 && order.getEndpaytime().getTime() <= System.currentTimeMillis()) {
			//未支付订单已过期
			order.setPaystatus(2);
		}
		if(order.getPaystatus() == 0 && order.getEndpaytime().getTime() > System.currentTimeMillis()) {
			//计算支付截止时间（秒）
			order.setSecond((order.getEndpaytime().getTime() - System.currentTimeMillis())/1000);
		}
		return order;
	}

	/**
	 * @Title: changeCourseOrder
	 * @Description: 删除订单中的课程
	 * @param orderid
	 * @param courseid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月10日 上午11:26:17
	 */
	@Transactional
	public void changeCourseOrder(String orderid, String courseid) throws SQLExecutorException {
		//删除订单中的课程
		Map<String, String> param = new HashMap<>();
		param.put("orderid", orderid);
		param.put("courseid", courseid);
		coursePurchaseMapper.delCourseByOrderId(param);
	}
	/**
	 * @Title: updateCourseOrderPrice
	 * @Description: 更新订单价格
	 * @param userid
	 * @param orderid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月12日 上午9:36:46
	 */
	@Transactional
	public void updateCourseOrderPrice(String orderid) throws SQLExecutorException {
		coursePurchaseMapper.changeCourseOrderMoney(orderid);
	}

	/**
	 * @Title: orderPayment
	 * @Description: 调起支付
	 * @param userId 用户id
	 * @param orderid 订单id
	 * @param couponid 代金券id
	 * @param paytype 支付类型（固定值：alipay 支付宝，wxpay 微信，upop 银联）
	 * @param mch 支付设备（固定值：app（app调用））
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月10日 下午2:33:10
	 */
	@Transactional
	public ResultBean orderPayment(String userid, String orderid, String couponid, String paytype, String mch) throws JsonParseException, JsonMappingException, IOException, SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("orderid", orderid);
		param.put("userid", userid);
		//获取订单总金额
		BigDecimal money = coursePurchaseMapper.getUnpayCourseOrderData(param);
		if(money == null) {
			//未查询到订单信息
			return new ResultBean("014", ResultMsg.C_014);
		}
		
		if(couponid != null && !couponid.trim().isEmpty()) {
			//使用代金券
			param.put("couponid", couponid);
			//获取代金券金额
			BigDecimal price = coursePurchaseMapper.getCouponPrice(param);
			if(price != null) {
				//支付总金额  = 订单金额 - 代金券金额
				money = money.subtract(price);
			}
		}else {
			//不使用代金券，将订单中的代金券至为null
			param.put("couponid", null);
		}
		
		//支付交易号
		String trans_num = UUIDUtil.getUUID();
		//更新订单中的代金券id，交易号
		param.put("trannum", trans_num);
		//支付类型
		if(ConstantIF.ALIPAY.equals(paytype)) {
			param.put("paytype", 1);
		}else if(ConstantIF.WXPAY.equals(paytype)) {
			param.put("paytype", 2);
		}else if(ConstantIF.WXPAY.equals(paytype)) {
			param.put("paytype", 3);
		}
		coursePurchaseMapper.updateCouponidToOrder(param);
		
		//支付数据
		Map<String, String> payData = null;
		
		// 构造支付请求数据
		HexunInstantTradeData build_data = new HexunInstantTradeData();
		build_data.setOrderid(trans_num);
		build_data.setUnit_price(money.toString());
		build_data.setNumber(1);
		build_data.setPrice(money.toString());
		build_data.setProduct_name("课程购买");
		build_data.setPay_type((mch == null || mch.isEmpty())? paytype : mch + "." + paytype);
		build_data.setOrder_type("course");
		//获取对应代理商code
		String agentCode = coursePurchaseMapper.getAgentCode(userid);
		build_data.setUtm(agentCode);
		build_data.setMch(mch);
		//即时到账交易(新支付)
		payData = HexunPay.getPayData(build_data);
		
		String result = HttpClientHelper.httpClientPost(HexunProperty.getValue("hexun.mag.url"), payData, "utf-8");
		
		ObjectMapper mapper = new ObjectMapper();
		if(result.indexOf("error_message") != -1) {
			//请求支付出错
			@SuppressWarnings("unchecked")
			Map<String, String> value = mapper.readValue(result, Map.class);
			return new ResultBean("500", value.get("error_message"));
		}
		
		if(result.indexOf("_input_charset") != -1 && 
//			result.indexOf("cashier_url") != -1 &&
			result.indexOf("is_success") != -1) {
			//请求支付成功
			HexunPayResult value = mapper.readValue(result, HexunPayResult.class);
			log.error(value);
			if("app".equals(mch)) {
				Object ret = value.getResult();
				if(ret instanceof Map) {
					((Map) ret).put("packageStr", ((Map) ret).get("package"));
					((Map) ret).remove("package");
				}
				return new ResultBean(ret);
			}else {
				return new ResultBean(value.getCashier_url() + "<script language='javascript'>document.getElementById('frmBankID').submit();</script>");
			}
		}else {
			return new ResultBean(result);
		}
	}

	
}
