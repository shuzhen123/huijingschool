package dianfan.dao.mapper.app;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.CourseOrder;
import dianfan.entities.CourseOrderList;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AppCoursePurchaseMapper
 * @Description 课程订单相关dao
 * @author cjy
 * @date 2018年4月10日 上午10:02:53
 */
@Repository
public interface AppCoursePurchaseMapper {
	
	/**
	 * @Title: findCourseOrderCount
	 * @Description: 根据条件获取课程订单总数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午5:30:28
	 */
	int findCourseOrderCount(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findCourseOrderData
	 * @Description: 根据条件获取课程订单列表
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午5:35:07
	 */
	List<CourseOrderList> findCourseOrderData(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: getPriceByCourseids
	 * @Description: 根据课程id获取订单总金额
	 * @param cids
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午4:17:11
	 */
	BigDecimal getPriceByCourseids(String[] cids) throws SQLExecutorException;
	
	/**
	 * @Title: createCourseOrder
	 * @Description: 创建待支付订单
	 * @param order
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午4:01:19
	 */
	@Insert("insert into t_courseorders (orderno, userid, source, money, paystatus, endpaytime, createtime, entkbn) values "
			+ "(#{orderno}, #{userid}, 1, #{money}, 0, #{endpaytime}, #{createtime}, 0)")
	void createCourseOrder(CourseOrder order) throws SQLExecutorException;

	/**
	 * @Title: createCourseOrderRelation
	 * @Description: 写入订单与课程关系
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午4:26:05
	 */
	void createCourseOrderRelation(Map<String, Object> data) throws SQLExecutorException;

	/**
	 * @Title: cleanCart
	 * @Description: 清空购物车中的选中课程
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午4:49:56
	 */
	void cleanCart(Map<String, Object> data) throws SQLExecutorException;

	/**
	 * @Title: delCourseOrder
	 * @Description: 删除订单
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月10日 上午9:16:24
	 */
	@Update("update t_courseorders set entkbn=9 where orderno=#{orderid} and userid=#{userid}")
	void delCourseOrder(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: getCourseOrderDetail
	 * @Description: 查看订单详情
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月10日 上午10:01:34
	 */
	CourseOrder getCourseOrderDetail(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: delCourseByOrderId
	 * @Description: 删除订单中的课程
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月10日 上午11:28:22
	 */
	@Delete("delete from t_course_orders_relation where orderid=#{orderid} and courseid=#{courseid}")
	void delCourseByOrderId(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: changeCourseOrderMoney
	 * @Description: 更改订单金额
	 * @param orderid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月10日 上午11:30:37
	 */
	@Update("update t_courseorders set money=" + 
			"(	select sum(course.coursemoney) " + 
			"	from t_course_orders_relation relation, t_course course " + 
			"	where relation.courseid=course.courseid and relation.orderid=#{orderid} " +
			") where orderno=#{orderid}")
	void changeCourseOrderMoney(String orderid) throws SQLExecutorException;

	/**
	 * @Title: getUnpayCourseOrderData
	 * @Description: 获取待付款订单数据
	 * @param data
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月10日 下午2:37:38
	 */
	@Select("select money from t_courseorders where orderno=#{orderid} and userid=#{userid} and paystatus=0 and endpaytime > now() and entkbn=0")
	BigDecimal getUnpayCourseOrderData(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getCouponPrice
	 * @Description: 获取代金券金额
	 * @param couponid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月10日 下午3:04:03
	 */
	@Select("select ca.price from t_ccrelation cc, t_cashcoupon ca where cc.cashid=ca.id and cc.id=#{couponid} and cc.userid=#{userid} and cc.useflag=0")
	BigDecimal getCouponPrice(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: updateCouponidToOrder
	 * @Description: 更新订单中的代金券id
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月10日 下午4:33:48
	 */
	@Update("update t_courseorders set cashcouponid=#{couponid}, trannum=#{trannum}, paytype=#{paytype} where orderno=#{orderid} and userid=#{userid}")
	void updateCouponidToOrder(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getAgentCode
	 * @Description: 获取对应代理商code
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月11日 下午2:51:15
	 */
	@Select("select agentcode from t_userinfo where userid = (select dtagentuserid from t_customer where userid=#{userid} and entkbn=0)")
	String getAgentCode(String userid) throws SQLExecutorException;

	

	

	
}












