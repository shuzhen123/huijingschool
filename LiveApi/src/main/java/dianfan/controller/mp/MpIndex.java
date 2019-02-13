package dianfan.controller.mp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.BannerInfo;
import dianfan.entities.IndexCourseBean;
import dianfan.entities.RecommendTeacherInfo;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.mp.MpIndexService;

/**
 * @ClassName MpIndex
 * @Description 微信公众号首页
 * @author cjy
 * @date 2018年1月26日 下午1:34:33
 */
@Scope("request")
@Controller
@RequestMapping(value = "/mp")
public class MpIndex {
	@Autowired
	private MpIndexService mpIndexService;
	/**
	 * @Title: courseList
	 * @Description: 轮播图列表
	 * @return
	 * @throws:
	 * @time: 2018年1月24日 上午9:50:57
	 */
	@LogOp(method = "bannerList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "轮播图列表")
	@ApiOperation(value = "轮播图列表", httpMethod = "GET", notes = "轮播图列表", response = ResultBean.class)
	@RequestMapping(value = "bannerlist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean bannerList() {
		try {
			List<BannerInfo> bannerList = mpIndexService.findBannerList();
			return new ResultBean(bannerList);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: indexCourseList
	 * @Description: 首页课程分类推荐列表
	 * @return
	 * @throws:
	 * @time: 2018年2月5日 上午9:12:03
	 */
	@LogOp(method = "indexCourseList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "首页课程分类推荐列表")
	@ApiOperation(value = "首页课程分类推荐列表", httpMethod = "GET", notes = "首页课程分类推荐列表", response = ResultBean.class)
	@RequestMapping(value = "indexcourselist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean indexCourseList() {
		try {
			List<IndexCourseBean> list = mpIndexService.findCourseListByType();
			return new ResultBean(list);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
}
