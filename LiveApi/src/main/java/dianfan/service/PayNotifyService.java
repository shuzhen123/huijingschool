package dianfan.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dainfan.hexun.entity.notify.ResponseCode;
import dainfan.hexun.utils.HexunProperty;
import dainfan.hexun.utils.HexunSignature;
import dainfan.hexun.utils.MD5Utils;
import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.PayNotifyMapper;
import dianfan.entities.BranchProfit;
import dianfan.entities.CourseOrder;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName PayNotifyService
 * @Description 支付异步通知处理服务
 * @author cjy
 * @date 2018年4月11日 上午9:29:36
 */
@Service
public class PayNotifyService {
	@Autowired
	private PayNotifyMapper payNotifyMapper;
	@Autowired
	private RedisService redisService;

	// public static Logger log = Logger.getLogger(PayNotifyService.class);
	/**
	 * @Title: checkSign
	 * @Description: 验签
	 * @param data
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 下午4:33:39
	 */
	public boolean checkSign(Map<String, String> data) throws SQLExecutorException {
		String fromSign = data.get("sign");
		/* 1、验签 */
		String signContent = HexunSignature.createSignContent(data);
		// log.error("待签字符串："+ signContent);
		// 签名
		String sign = MD5Utils.getMD5(signContent + HexunProperty.getValue("hexun.check.sign.key"));
		// log.error("本地签名："+ sign);

		if (sign == null || !sign.equals(fromSign)) {
			// 验签未通过
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @Title: checkOrder
	 * @Description: 校验付款金额
	 * @param data
	 * @param orderType
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 下午5:25:13
	 */
	public Map<String, String> checkOrder(Map<String, String> data, String orderType) throws SQLExecutorException {
		// 交易号
		String trade_no = data.get("outer_trade_no");
		// 交易金额
		BigDecimal pay_money = new BigDecimal(data.get("trade_amount"));

		Map<String, String> valid_result = new HashMap<>();

		if ("course".equals(orderType)) {
			// 根据交易号查询订单数据
			CourseOrder courseOrder = payNotifyMapper.findCourseOrderDataById(trade_no);
			// log.error("根据交易号查询订单数据 ："+ courseOrder.toString());
			// 查询代金券金额
			if (courseOrder.getCashcouponid() != null) {
				BigDecimal cash_price = payNotifyMapper.getCouponPrice(courseOrder.getCashcouponid());
				// log.error("查询代金券金额 ："+ cash_price);
				courseOrder.setMoney(courseOrder.getMoney().subtract(cash_price));
			}
			// log.error("根据交易号查询订单数据(代金券处理后) ："+ courseOrder.toString());

			// 1、校验支付金额
			if (pay_money == null || !pay_money.equals(courseOrder.getMoney())) {
				// 支付金额与订单金额不匹配
				valid_result.put("result", ResponseCode.FAILD);
				valid_result.put("msg", "支付金额与订单金额不匹配");
				return valid_result;
			}

			// 2、订单支付状态 0未支付，1已支付，2已失效
			if (courseOrder.getPaystatus() != 0) {
				// 订单支付状态已改变
				valid_result.put("result", ResponseCode.FAILD);
				valid_result.put("msg", "订单支付状态已改变");
				return valid_result;
			}

			// 验证通过
			valid_result.put("result", ResponseCode.SUCCESS);
			return valid_result;
		} else {
			valid_result.put("result", ResponseCode.FAILD);
			valid_result.put("msg", "未知的订单信息");
			return valid_result;
		}
	}

	/**
	 * @Title: updateVipOrder
	 * @Description: 更新vip订单、添加分润数据
	 * @param trannum
	 *            交易号
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月29日 下午3:55:50
	 */
	@Transactional
	public CourseOrder updateCourseOrder(String trannum) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("trannum", trannum);

		// 获取订单信息
		CourseOrder order = payNotifyMapper.findCourseOrderDataById(trannum);

		if (order.getCashcouponid() != null && !order.getCashcouponid().isEmpty()) {
			// 更新代金券
			payNotifyMapper.updateCashcouponById(order.getCashcouponid());
			// 更新订单价格
			// 查询代金券金额
			BigDecimal cash_price = payNotifyMapper.getCouponPrice(order.getCashcouponid());
			order.setMoney(order.getMoney().subtract(cash_price));
		}
		param.put("money", order.getMoney());
		// 订单到期时间
		// 获取课程购买时长
		String time_len = redisService.get(ConstantIF.COURSE_BY_TIME);
		if (time_len == null || time_len.trim().isEmpty()) {
			// 默认时长
			time_len = "30";
		}
		int time_len_num = Integer.parseInt(time_len) + 1;

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(calendar.DATE, +time_len_num);

		param.put("validitytime", new Timestamp(calendar.getTime().getTime()));
		param.put("money", order.getMoney());
		// 更新课程订单
		payNotifyMapper.updateCourseOrder(param);
		
		// 获取用户对应的代理商及业务员
		BranchProfit bp = payNotifyMapper.findAgentAndSalerRelation(order.getUserid());
		bp.setId(UUIDUtil.getUUID());
		bp.setOrderno(order.getOrderno());
		bp.setKind(1);
		bp.setBagentresult(1);
		bp.setBsalerresult(1);
		// 订单金额
		BigDecimal oPrice = order.getMoney();
		// 获取代理商分润比率结果
		String agent = redisService.get(ConstantIF.DEF_AGENT_PROFIT_KEY);
		if (agent == null || agent.trim().isEmpty()) {
			bp.setBagentmoney(new BigDecimal(0));
		} else {
			// 比率因数
			BigDecimal factor = new BigDecimal(agent);
			bp.setBagentmoney(oPrice.multiply(factor));
		}
		// 获取业务员课程分润
		String saler = redisService.get(ConstantIF.DEF_SALER_PROFIT_KEY);
		if (saler == null || saler.trim().isEmpty()) {
			bp.setBsalermoney(new BigDecimal(0));
		} else {
			// 比率因数
			BigDecimal factor = new BigDecimal(saler);
			bp.setBsalermoney(oPrice.multiply(factor));
		}
		// 插入分润数据
		payNotifyMapper.addBranchProfit(bp);

		return order;
	}

	/**
	 * @Title: updateUserVipLevelInfo
	 * @Description: 更新用户vip等级信息
	 * @param userid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月27日 下午3:43:08
	 */
	@Transactional
	public void updateUserVipLevelInfo(CourseOrder order) throws SQLExecutorException {
		//根据用户id获取满足的的vip等级id
		VipLevel vipLevel = payNotifyMapper.findVipLevelByUserid(order);
		if(vipLevel != null) {
			//满足vip等级
			
			//计算vip等级赠送期限
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.add(calendar.DATE, + (vipLevel.getDays() + 1));

			//持久化用户赠送vip等级信息
			payNotifyMapper.createVipLevelInfo(order.getUserid(), order.getOrderno(), vipLevel.getId(), new Timestamp(calendar.getTime().getTime()));
		}
	}
	
	/**
	 * @Title: updateUserCache
	 * @Description: 更新用户缓存（课程缓存、vip等级缓存）
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月27日 下午5:08:52
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void updateUserCache(String userid) throws SQLExecutorException {
		// 更新当前购买用户的课程缓存
		// 缓存已购买的课程id
		List<String> courseids = payNotifyMapper.getByedCourse(userid);
		// 清空课程缓存
		redisService.del(userid + ConstantIF.USER_COURSE_CACHE_SUFFIX);
		// 缓存课程id
		redisService.Sadd(userid + ConstantIF.USER_COURSE_CACHE_SUFFIX, courseids);
	}

}
