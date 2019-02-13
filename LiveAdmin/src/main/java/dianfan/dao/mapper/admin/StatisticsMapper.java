package dianfan.dao.mapper.admin;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BranchProfit;
import dianfan.entities.Coupon;
import dianfan.entities.CourseOrder;
import dianfan.entities.CourseOrderCollect;
import dianfan.entities.GoodsOrderCollect;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName StatisticsMapper
 * @Description 统计管理dao
 * @author cjy
 * @date 2018年4月11日 下午3:01:04
 */
@Repository
public interface StatisticsMapper {

	/**
	 * @Title: findCourseOrderCount
	 * @Description: 获取课程订单总数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月11日 下午3:02:25
	 */
	int findCourseOrderCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCourseOrders
	 * @Description: 获取课程订单数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月11日 下午3:27:17
	 */
	List<CourseOrderCollect> findCourseOrders(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findGoodsOrderCount
	 * @Description: 根据条件获取礼物订单数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月18日 上午11:14:04
	 */
	int findGoodsOrderCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findGoodsOrders
	 * @Description: 根据条件获取礼物订单数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月18日 上午11:22:18
	 */
	List<GoodsOrderCollect> findGoodsOrders(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getCourseOrderInfo
	 * @Description: 查询订单信息
	 * @param orderid
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午7:00:50
	 */
	@Select("select orders.*, user.userid, user.telno, user.nickname " +
			"from t_courseorders orders, t_userinfo user " +
			"where orders.userid=user.userid and orders.orderno=#{orderid}")
	CourseOrderCollect getCourseOrderInfo(String orderid) throws SQLExecutorException;

	/**
	 * @Title: findUserCoupon
	 * @Description: 查询用户代金券列表
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午7:12:12
	 */
	@Select("select cc.id, ca.cpname, price from t_ccrelation cc, t_cashcoupon ca  " +
			"where cc.cashid=ca.id and cc.userid=#{userid} and  " +
			"cc.expire > now() and cc.useflag=0 and ca.type=2 and ca.entkbn=0 ")
	List<Coupon> findUserCoupon(String userid) throws SQLExecutorException;

	/**
	 * @Title: changeCourseOrder
	 * @Description: 未付款课程订单修改
	 * @param order
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月10日 下午4:08:12
	 */
	@Update("update t_courseorders set trannum=replace(uuid(),'-',''), cashcouponid=#{cashcouponid}, money=#{money}, " +
			"paystatus=#{paystatus}, paytype=#{paytype}, paytime=now(), validitytime=#{validitytime}  where orderno=#{orderno}")
	void changeCourseOrder(CourseOrderCollect order) throws SQLExecutorException;
	
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
	 * @Title: findVipLevelByUserid
	 * @Description: 根据用户id获取满足的的vip等级id
	 * @param orderno
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月27日 下午3:44:38
	 */
	@Select("select id, days from t_viplevel where money between 0 and "
			+ "(select money from t_courseorders where orderno=#{orderno}) and entkbn=0 order by money desc limit 0,1")
	VipLevel findVipLevelByUserid(String orderno) throws SQLExecutorException;
	
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
}