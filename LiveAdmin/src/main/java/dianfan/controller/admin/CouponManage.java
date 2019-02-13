package dianfan.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.CouponMapper;
import dianfan.entities.Coupon;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.CouponService;

/**
 * @ClassName CouponManage
 * @Description 卡券管理
 * @author cjy
 * @date 2018年1月8日 下午1:59:19
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class CouponManage {
	@Autowired
	private CouponMapper couponMapper;
	@Autowired
	private CouponService couponService;
	/**
	 * @Title: dashboardCoupon
	 * @Description: 卡券管理首页
	 * @return
	 * @throws:
	 * @time: 2018年1月5日 下午2:58:48
	 */
	@LogOp(method = "dashboardCoupon", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "卡券管理首页")
	@RequestMapping(value = "coupon", method = RequestMethod.GET)
	public String dashboardCoupon() {
		return ConstantIF.ADMIN_COUPON + "coupon-list";
	}
	
	/**
	 * @Title: couponList
	 * @Description: 卡券列表
	 * @return
	 * @throws:
	 * @time: 2018年1月8日 下午2:35:57
	 */
	@LogOp(method = "couponList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "卡券管理首页")
	@RequestMapping(value = "couponlist", method = RequestMethod.POST)
	public @ResponseBody DataTable couponList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 根据卡券列表
			table = couponService.findCoupons(start, length);
			
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	/**
	 * @Title: editCouponPage
	 * @Description: 卡券编辑页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月8日 下午4:17:34
	 */
	@LogOp(method = "editCouponPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取卡券数据")
	@RequestMapping(value = "editcouponpage", method = RequestMethod.GET)
	public String editCouponPage(String couponid, Model model) {
		try {
			Coupon coupon = couponMapper.getCourseDataById(couponid);
			model.addAttribute("coupon", coupon);
			return ConstantIF.ADMIN_COUPON + "coupon-edit";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}
	
	/**
	 * @Title: editCoupon
	 * @Description: 更新卡券
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 上午10:27:26
	 */
	@LogOp(method = "editCoupon", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "更新卡券")
	@RequestMapping(value = "editcoupon", method = RequestMethod.POST)
	public @ResponseBody ResultBean editCoupon(Coupon coupon) {
		try {
			couponService.editCoupon(coupon);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	
	
	/**
	 * @Title: delCoupon
	 * @Description: 删除卡券
	 * @param couponids
	 * @return
	 * @throws:
	 * @time: 2018年1月8日 下午3:40:56
	 */
	@LogOp(method = "delCoupon", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除卡券")
	@RequestMapping(value = "delcoupon", method = RequestMethod.POST)
	public @ResponseBody ResultBean delCoupon(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		List<String> lids = new ArrayList<>();
		
		for(String id : ids) {
			lids.add(id);
		}
		
		// 根据卡券id删除卡券
		try {
			couponService.delCoupon(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: addCouponPage
	 * @Description: 添加卡券页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 上午11:40:03
	 */
	@LogOp(method = "addCouponPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加卡券页")
	@RequestMapping(value = "addcouponpage", method = RequestMethod.GET)
	public String addCouponPage() {
		return ConstantIF.ADMIN_COUPON + "coupon-add";
	}
	
	/**
	 * @Title: addCoupon
	 * @Description: 添加卡券数据
	 * @param coupon
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午12:05:19
	 */
	@LogOp(method = "addCoupon", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加卡券数据")
	@RequestMapping(value = "addcoupon", method = {RequestMethod.POST})
	public @ResponseBody ResultBean addCoupon(Coupon coupon) {
		try {
			couponService.addCoupon(coupon);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
