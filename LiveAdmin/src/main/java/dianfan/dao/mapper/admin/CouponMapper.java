package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Coupon;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName CouponMapper
 * @Description 卡券dao
 * @author cjy
 * @date 2018年1月8日 下午2:48:33
 */
@Repository
public interface CouponMapper {
	/**
	 * @Title: findCouponCount
	 * @Description: 根据条件获取卡券数量
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 下午3:32:34
	 */
	@Select("select count(*) from t_cashcoupon where entkbn != 9")
	int findCouponCount() throws SQLExecutorException;

	/**
	 * @Title: findCoupons
	 * @Description: 根据条件获取卡券列表
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月8日 下午2:58:31
	 */
	@Select("select * from t_cashcoupon where entkbn != 9 limit #{start}, #{length}")
	List<Coupon> findCoupons(@Param(value = "start") int start, @Param(value = "length") int length) throws SQLExecutorException;

	/**
	 * @Title: delCouponByIds
	 * @Description: 根据id删除卡券
	 * @param ids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月8日 下午3:42:44
	 */
	void delCouponByIds(List<String> ids) throws SQLExecutorException;

	/**
	 * @Title: getCourseDataById
	 * @Description: 根据id获取卡券数据
	 * @param couponid
	 * @return
	 * @throws:
	 * @time: 2018年1月8日 下午4:15:54
	 */
	@Select("select * from t_cashcoupon where id=#{couponid}")
	Coupon getCourseDataById(String couponid) throws SQLExecutorException;

	/**
	 * @Title: editCoupon
	 * @Description: 修改卡券信息
	 * @param coupon
	 * @throws:
	 * @time: 2018年1月9日 上午10:32:48
	 */
	@Update("update t_cashcoupon set cpname=#{cpname}, price=#{price}, servicedays=#{servicedays}, validity=#{validity}, entkbn=#{entkbn}  where id=#{id}")
	void editCoupon(Coupon coupon) throws SQLExecutorException;

	/**
	 * @Title: addCoupon
	 * @Description: 添加卡券数据
	 * @param coupon
	 * @throws:
	 * @time: 2018年1月9日 下午12:05:55
	 */
	@Insert("insert into t_cashcoupon (id, cpname, type, price, useplace, servicedays, validity, createtime) values "
			+ "(replace(uuid(),'-',''), #{cpname}, #{type}, #{price}, #{type}, #{servicedays}, #{validity}, now())")
	void addCoupon(Coupon coupon) throws SQLExecutorException;


	
	
}