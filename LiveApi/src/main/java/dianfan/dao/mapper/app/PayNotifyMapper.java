package dianfan.dao.mapper.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BranchProfit;
import dianfan.entities.CourseOrder;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName PayNotifyMapper
 * @Description 支付异步通知处理服务dao
 * @author cjy
 * @date 2018年4月11日 上午9:37:31
 */
@Repository
public interface PayNotifyMapper {

	/**
	 * @Title: findCourseOrderDataById
	 * @Description: 根据交易号查询订单数据
	 * @param trade_no
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月11日 上午9:38:45
	 */
	@Select("select * from t_courseorders where trannum=#{trade_no} and entkbn=0")
	CourseOrder findCourseOrderDataById(String trade_no) throws SQLExecutorException;

	/**
	 * @Title: getCouponPrice
	 * @Description: 查询代金券金额
	 * @param cashcouponid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月12日 上午10:02:40
	 */
	@Select("select ca.price from t_ccrelation cc, t_cashcoupon ca where cc.cashid=ca.id and cc.id=#{cashcouponid}")
	BigDecimal getCouponPrice(String cashcouponid) throws SQLExecutorException;

	/**
	 * @Title: updateCourseOrder
	 * @Description: 付款成功，更新课程订单
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月11日 上午10:21:16
	 */
	@Update("update t_courseorders set money=#{money}, paystatus=1, paytime=now(), validitytime=#{validitytime} where trannum=#{trannum}")
	void updateCourseOrder(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: updateCashcouponById
	 * @Description: 更新代金券
	 * @param cashid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月11日 上午10:24:44
	 */
	@Update("update t_ccrelation set useflag=9 where id=#{cashid}")
	void updateCashcouponById(String cashid) throws SQLExecutorException;

	/**
	 * @Title: findAgentAndSalerRelation
	 * @Description: 获取用户对应的代理商及业务员
	 * @param userid
	 * @throws:
	 * @time: 2018年2月26日 上午9:33:11
	 */
	@Select("select dtagentuserid agentuserid, dtsmuserid saleruserid from t_customer where userid = #{userid} and entkbn = 0")
	BranchProfit findAgentAndSalerRelation(String userid) throws SQLExecutorException;

	/**
	 * @Title: addBranchProfit
	 * @Description: 插入分润数据
	 * @param bp
	 * @throws:
	 * @time: 2018年2月26日 上午10:11:03
	 */
	@Insert("insert into branchprofit (id, orderno, kind, agentuserid, saleruserid, bprofitdate, bagentmoney, bsalermoney, bagentresult, bsalerresult, createtime, entkbn) values "
			+ "(#{id}, #{orderno}, #{kind}, #{agentuserid}, #{saleruserid}, now(), #{bagentmoney}, #{bsalermoney}, #{bagentresult}, #{bsalerresult}, now(), 0)")
	void addBranchProfit(BranchProfit bp) throws SQLExecutorException;

	/**
	 * @Title: getByedCourse
	 * @Description: 根据用户id查询已购买的课程id
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月27日 下午3:37:16
	 */
	@Select("select DISTINCT courseid from t_course_orders_relation " + 
			"where orderid in(select orderno from t_courseorders where userid=#{userid} and paystatus=1 and validitytime >now())")
	List<String> getByedCourse(String userid) throws SQLExecutorException;

	/**
	 * @Title: findVipLevelByUserid
	 * @Description: 根据用户id获取满足的的vip等级id
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月27日 下午3:44:38
	 */
	@Select("select id, days from t_viplevel where money between 0 and "
			+ "(select money from t_courseorders where orderno=#{orderno} and userid=#{userid}) and entkbn=0 order by money desc limit 0,1")
	VipLevel findVipLevelByUserid(CourseOrder order) throws SQLExecutorException;

	/**
	 * @Title: createVipLevelInfo
	 * @Description: 持久化用户赠送vip等级信息
	 * @param userid
	 * @param orderno
	 * @param id
	 * @param timestamp
	 * @throws:
	 * @time: 2018年4月27日 下午4:43:37
	 */
	@Insert("insert into t_user_viplevel_time (id, userid, orderid, viplevelid, endtime) values "
			+ "(replace(uuid(),'-',''), #{userid}, #{orderid}, #{viplevelid}, #{endtime})")
	void createVipLevelInfo(@Param(value = "userid") String userid, @Param(value = "orderid") String orderid, @Param(value = "viplevelid") String viplevelid, @Param(value = "endtime") Timestamp endtime) throws SQLExecutorException;

}
