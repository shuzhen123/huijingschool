package dianfan.service.admin;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.StatisticsMapper;
import dianfan.entities.BranchProfit;
import dianfan.entities.CourseOrderCollect;
import dianfan.entities.DataTable;
import dianfan.entities.GoodsOrderCollect;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.util.UUIDUtil;

/**
 * @ClassName StatisticsService
 * @Description 统计管理服务
 * @author cjy
 * @date 2018年4月11日 下午3:01:17
 */
@Service
public class StatisticsService {
	@Autowired
	private StatisticsMapper statisticsMapper;
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: findCourseOrder
	 * @Description: 课程订单列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月11日 下午2:59:08
	 */
	public DataTable findCourseOrder(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = statisticsMapper.findCourseOrderCount(param);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、获取需求数据
		List<CourseOrderCollect> order = statisticsMapper.findCourseOrders(param);
		for (CourseOrderCollect coc : order) {
			if (coc.getPaystatus() == 1 && coc.getValiditytime().getTime() < System.currentTimeMillis()) {
				coc.setPaystatus(2);
			}
		}
		// 设置数据
		dt.setData(order);
		return dt;
	}

	/**
	 * @Title: findGoodsOrder
	 * @Description: 礼物赠送统订单列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月10日 下午4:06:54
	 */
	public DataTable findGoodsOrder(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = statisticsMapper.findGoodsOrderCount(param);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、获取需求数据
		List<GoodsOrderCollect> order = statisticsMapper.findGoodsOrders(param);
		// 设置数据
		dt.setData(order);
		return dt;
	}

	/**
	 * @Title: changeCourseOrderPay
	 * @Description: 未付款课程订单修改
	 * @param order
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月10日 下午4:07:08
	 */
	@Transactional
	public void changeCourseOrderPay(CourseOrderCollect order) throws SQLExecutorException {
		//课程订单修改
		statisticsMapper.changeCourseOrder(order);
		//若课程已手动置为已付款，赠送vip、分润
		if(order.getPaystatus() == 1) {
			
			// 更新代金券
			if (order.getCashcouponid() != null && !order.getCashcouponid().isEmpty()) {
				statisticsMapper.updateCashcouponById(order.getCashcouponid());
			}
			
			// 获取用户对应的代理商及业务员
			BranchProfit bp = statisticsMapper.findAgentAndSalerRelation(order.getUserid());
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
			statisticsMapper.addBranchProfit(bp);
			
			//更新用户vip等级信息
			//根据用户id获取满足的的vip等级id
			VipLevel vipLevel = statisticsMapper.findVipLevelByUserid(order.getOrderno());
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
				statisticsMapper.createVipLevelInfo(order.getUserid(), order.getOrderno(), vipLevel.getId(), new Timestamp(calendar.getTime().getTime()));
			}
			
			// 更新当前购买用户的课程缓存
			// 缓存已购买的课程id
			List<String> courseids = statisticsMapper.getByedCourse(order.getUserid());
			// 清空课程缓存
			redisService.del(order.getUserid() + ConstantIF.USER_COURSE_CACHE_SUFFIX);
			// 缓存课程id
			redisService.Sadd(order.getUserid() + ConstantIF.USER_COURSE_CACHE_SUFFIX, courseids);
		}
	}

}