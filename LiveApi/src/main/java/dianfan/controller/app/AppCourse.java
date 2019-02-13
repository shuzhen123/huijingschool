package dianfan.controller.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppCourseMapper;
import dianfan.entities.AppRecommendCourse;
import dianfan.entities.CourseCondition;
import dianfan.entities.CourseDirectory;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppCourseService;

/**
 * @ClassName AppCourse
 * @Description app课程列表
 * @author cjy
 * @date 2017年12月21日 下午1:12:15
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppCourse {
	/**
	 * 注入：RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * 注入：AppCourseService
	 */
	@Autowired
	private AppCourseService appCourseService;
	/**
	 * 注入：AppCourseMapper
	 */
	@Autowired
	private AppCourseMapper appCourseMapper;

	/**
	 * @Title: indexCourseList
	 * @Description: 首页课程列表
	 * @param type
	 *            课程类型（free:免费，pay:付费）
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年1月24日 上午9:50:57
	 */
	@LogOp(method = "indexCourseList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "首页推荐课程列表")
	@ApiOperation(value = "首页推荐课程列表", httpMethod = "GET", notes = "首页推荐课程列表", response = ResultBean.class)
	@RequestMapping(value = "indexcourselist", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean indexCourseList(
			@ApiParam(value = "课程类型（free:免费，pay:付费）") @RequestParam(value = "type") String type) {
		try {
			// 首页课程列表
			int limit;
			if (type != null && "free".equals(type.trim())) {
				// 免费课程
				limit = 1;
			} else {
				// 付费课程
				limit = 2;
			}
			List<AppRecommendCourse> list = appCourseMapper.findCoursesByType(limit);
			for (AppRecommendCourse arc : list) {
				arc.setCoursepic(ConstantIF.PROJECT + arc.getCoursepic());
			}
			return new ResultBean(list);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: courseList
	 * @Description: 课程列表
	 * @param accesstoken
	 * @param coursetype
	 *            课程类型（1：免费直播课程2：vip实战直播课程3：精品课4：私教课）
	 * @param peopleunm
	 *            按购买人数排序（""自然排序，asc升序， desc降序）
	 * @param evaluate
	 *            按评价排序（""自然排序，asc升序， desc降序）
	 * 
	 * @param teacherid
	 *            按名师筛选
	 * @param type
	 *            按类型筛选
	 * @param price
	 *            按价格区间筛选
	 * 
	 * @param page
	 *            请求页
	 * @return
	 * @throws:
	 * @time: 2017年12月25日 下午5:54:35
	 */
	@LogOp(method = "courseList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程列表")
	@ApiOperation(value = "课程列表", httpMethod = "GET", notes = "课程列表", response = ResultBean.class)
	@RequestMapping(value = "courselist", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean courseList(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = false) String accesstoken,
			@ApiParam("课程类型（1：免费直播课程2：vip实战直播课程3：精品课4：私教课）") @RequestParam(value = "coursetype", defaultValue = "3", required = false) String coursetype,
			@ApiParam("按购买人数排序（''自然排序，asc升序， desc降序）") @RequestParam(value = "peopleunm", required = false) String peopleunm,
			@ApiParam("按评价排序（''自然排序，asc升序， desc降序）") @RequestParam(value = "evaluate", required = false) String evaluate,

			@ApiParam("按名师筛选") @RequestParam(value = "teacherid", required = false) String teacherid,
			@ApiParam("按类型筛选") @RequestParam(value = "type", required = false) String type,
			@ApiParam("按价格区间筛选") @RequestParam(value = "price", required = false) String price,

			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {

		CourseCondition param = new CourseCondition();

		// 判断是否登录
		TokenModel token = redisTokenService.getToken(accesstoken);
		boolean login_stu = redisTokenService.checkToken(token);
		if (login_stu) {
			// 成功登录
			param.setUserid(token.getUserId());
		} else {
			param.setUserid(null);
		}
		param.setCoursetype(coursetype);
		param.setPeopleunm(peopleunm);
		param.setEvaluate(evaluate);
		param.setTeacherid(teacherid);
		param.setType(type);
		param.setPrice(price);
		param.setPage(page);

		// 获取课程列表
		try {
			Map<String, Object> result = appCourseService.getCourseList(param);
			return new ResultBean(result);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: praise
	 * @Description: 课程点赞
	 * @param accesstoken
	 * @param courseid
	 *            课程id
	 * @return
	 * @throws:
	 * @time: 2017年12月22日 下午1:12:36
	 */
	@LogOp(method = "praise", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程点赞")
	@ApiOperation(value = "课程点赞", httpMethod = "GET", notes = "课程点赞", response = ResultBean.class)
	@RequestMapping(value = "praise", method = RequestMethod.GET)
	public @ResponseBody ResultBean praise(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("课程id") @RequestParam(value = "courseid") String courseid) {
		if (courseid == null || "".equals(courseid)) {
			return new ResultBean("501", ResultMsg.C_501);
		}
		// 点赞
		try {
			TokenModel token = redisTokenService.getToken(accesstoken);
			appCourseService.coursePraise(courseid, token.getUserId());
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: courseDetail
	 * @Description: 课程详情
	 * @param accesstoken
	 * @param courseid
	 *            课程id
	 * @return
	 * @throws:
	 * @time: 2017年12月22日 下午2:10:16
	 */
	@LogOp(method = "courseDetail", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程详情")
	@ApiOperation(value = "课程详情", httpMethod = "GET", notes = "课程详情", response = ResultBean.class)
	@RequestMapping(value = "coursedetail", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean courseDetail(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = false) String accesstoken,
			@ApiParam("课程id") @RequestParam(value = "courseid") String courseid) {
		if (courseid == null) {
			return new ResultBean("501", ResultMsg.C_501);
		}

		String userid = null;
		// 判断是否登录
		if (accesstoken != null && !"".equals(accesstoken.trim())) {
			TokenModel token = redisTokenService.getToken(accesstoken);
			boolean b = redisTokenService.checkToken(token);
			if (b) {
				// 已登录
				userid = token.getUserId();
			}
		}

		// 课程详情
		try {
			return appCourseService.getCourseDetailByCourseid(courseid, userid);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: recommendCourse
	 * @Description: 课程详情内推荐课程
	 * @param teacherid
	 * @param courseid
	 * @return
	 * @throws:
	 * @time: 2018年3月7日 下午6:17:08
	 */
	@LogOp(method = "recommendCourse", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程详情内推荐课程")
	@ApiOperation(value = "课程详情内推荐课程", httpMethod = "GET", notes = "课程详情内推荐课程", response = ResultBean.class)
	@RequestMapping(value = "recommendcourse", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean recommendCourse(
			@ApiParam("教师id") @RequestParam(value = "teacherid") String teacherid,
			@ApiParam("课程id") @RequestParam(value = "courseid") String courseid) {
		// 课程列表
		try {
			List<AppRecommendCourse> list = appCourseService.findRecommendCourse(teacherid, courseid);
			return new ResultBean(list);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: courseDirectory
	 * @Description: 课程目录
	 * @param accesstoken
	 * @param courseid
	 *            当前播放的课程id
	 * @return
	 * @throws:
	 * @time: 2017年12月22日 下午3:51:05
	 */
	@LogOp(method = "courseDirectory", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程目录")
	@ApiOperation(value = "课程目录", httpMethod = "GET", notes = "课程目录", response = ResultBean.class)
	@RequestMapping(value = "coursedirectory", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean courseDirectory(
			@ApiParam("当前播放的课程id") @RequestParam(value = "courseid") String courseid) {

		if (courseid == null || "".equals(courseid)) {
			return new ResultBean("501", ResultMsg.C_501);
		}

		List<CourseDirectory> findCourseDirectory;
		// 获取课程视频列表
		try {
			findCourseDirectory = appCourseService.findCourseDirectory(courseid);
			if (findCourseDirectory == null || findCourseDirectory.size() < 1) {
				findCourseDirectory = new ArrayList<>();
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean(findCourseDirectory);
	}

	/**
	 * @Title: courseVideoHitsInc
	 * @Description: 课程视频播放量+1
	 * @param videoid
	 * @return
	 * @throws:
	 * @time: 2018年1月25日 下午2:17:28
	 */
	@LogOp(method = "courseVideoHitsInc", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程视频播放量+1")
	@ApiOperation(value = "课程视频播放量+1", httpMethod = "GET", notes = "课程视频播放量+1", response = ResultBean.class)
	@RequestMapping(value = "videohitsinc", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean courseVideoHitsInc(
			@ApiParam("当前播放的视频id") @RequestParam(value = "videoid") String videoid) {
		if (videoid == null || "".equals(videoid.trim())) {
			return new ResultBean("501", ResultMsg.C_501);
		}

		try {
			// 课程视频播放量+1
			appCourseService.courseVideoHitsTime(videoid);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: courseComment
	 * @Description: 课程评价列表
	 * @param accesstoken
	 * @param courseid
	 *            课程id
	 * @param reqpage
	 *            请求页数
	 * @return
	 * @throws:
	 * @time: 2017年12月22日 下午5:03:36
	 */
	@LogOp(method = "courseComment", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程评价列表")
	@ApiOperation(value = "课程评价列表", httpMethod = "GET", notes = "课程评价列表", response = ResultBean.class)
	@RequestMapping(value = "coursecomment", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean courseComment(@ApiParam("课程id") @RequestParam(value = "courseid") String courseid,
			@ApiParam("请求页数") @RequestParam(value = "reqpage", defaultValue = "1") String page) {
		if (courseid == null || "".equals(courseid) || page == null || "".equals(page)) {
			return new ResultBean("501", ResultMsg.C_501);
		}

		// 分页数据
		Integer request_page = Integer.parseInt(page);
		if (request_page == null || request_page <= 1) {
			request_page = 1;
		}

		ResultBean result;
		try {
			// 课程评价列表
			result = appCourseService.getCourseComment(courseid, request_page);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return result;
	}
	
	
	@LogOp(method = "courseWriteComment", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程评价")
	@ApiOperation(value = "课程评价", httpMethod = "POST", notes = "课程评价", response = ResultBean.class)
	@RequestMapping(value = "writecomment", method = RequestMethod.POST)
	public @ResponseBody ResultBean courseWriteComment(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("课程id") @RequestParam(value = "courseid") String courseid,
			@ApiParam("评价内容") @RequestParam(value = "content") String content,
			@ApiParam("星级") @RequestParam(value = "star") Integer star) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		if(StringUtils.isEmpty(content)) {
			return new ResultBean("025", ResultMsg.C_025);
		}
		
		try {
			// 课程评价
			appCourseService.courseWriteComment(token.getUserId(), courseid, content, star);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
