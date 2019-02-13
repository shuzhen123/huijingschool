package dianfan.service.admin;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.admin.CouponMapper;
import dianfan.entities.Coupon;
import dianfan.entities.DataTable;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName CouponService
 * @Description 卡券服务
 * @author cjy
 * @date 2018年1月8日 下午2:45:09
 */
@Service
public class CouponService {

	@Autowired
	private CouponMapper couponMapper;

	/**
	 * @Title: findCoupons
	 * @Description: 卡券列表
	 * @param search
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月8日 下午2:49:19
	 */
	public DataTable findCoupons(int start, int length) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = couponMapper.findCouponCount();
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、获取需求数据
		List<Coupon> coupons = couponMapper.findCoupons(start, length);
		
		//设置数据
		dt.setData(coupons);
		return dt;
	}

	/**
	 * @Title: delCoupon
	 * @Description: 根据id删除卡券
	 * @param ids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月8日 下午3:41:14
	 */
	@Transactional
	public void delCoupon(List<String> ids) throws SQLExecutorException {
		couponMapper.delCouponByIds(ids);
	}

	/**
	 * @Title: editCoupon
	 * @Description: 修改卡券信息
	 * @param coupon
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月9日 上午10:31:58
	 */
	@Transactional
	public void editCoupon(Coupon coupon) throws SQLExecutorException {
		couponMapper.editCoupon(coupon);
	}

	/**
	 * @Title: addCoupon
	 * @Description: 添加卡券数据
	 * @param coupon
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月9日 下午12:05:13
	 */
	@Transactional
	public void addCoupon(Coupon coupon) throws SQLExecutorException {
		couponMapper.addCoupon(coupon);
	}

}