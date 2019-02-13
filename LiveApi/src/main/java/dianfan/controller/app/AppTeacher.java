package dianfan.controller.app;

import java.util.List;
import java.util.TreeSet;

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
import dianfan.dao.mapper.app.AppTeacherMapper;
import dianfan.entities.AppRecommendCourse;
import dianfan.entities.Course;
import dianfan.entities.RecommendTeacherInfo;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.app.AppTeacherService;
/**
 * @ClassName AppTeacher
 * @Description 教师
 * @author cjy
 * @date 2018年3月7日 下午4:30:40
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppTeacher {
	@Autowired
	private AppTeacherService appTeacherService;
	@Autowired
	private AppTeacherMapper appTeacherMapper;
	
	/**
	 * @Title: indexRecommendTeacher
	 * @Description: 首页名师推荐
	 * @return
	 * @throws:
	 * @time: 2018年1月23日 下午4:05:17
	 */
	@LogOp(method = "indexRecommendTeacher", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "首页名师推荐列表")
	@ApiOperation(value = "首页名师推荐列表", httpMethod = "GET", notes = "首页名师推荐", response = ResultBean.class)
	@RequestMapping(value = "indexteacher", method=RequestMethod.GET)
	@UnCheckedFilter	
	public @ResponseBody ResultBean indexRecommendTeacher() {
		try {
			//首页名师推荐
			List<RecommendTeacherInfo> teachers = appTeacherMapper.findIndexRecommendTeacher();
			for(RecommendTeacherInfo rti : teachers) {
				rti.setTeacherurl(ConstantIF.PROJECT + rti.getTeacherurl());
			}
			return new ResultBean(teachers);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: recommendTeacher
	 * @Description: 名师列表
	 * @return
	 * @throws:
	 * @time: 2018年1月23日 下午4:50:40
	 */
	@LogOp(method = "recommendTeacher", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "名师列表")
	@ApiOperation(value = "名师列表", httpMethod = "GET", notes = "名师列表", response = ResultBean.class)
	@RequestMapping(value = "recommendteacher", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean recommendTeacher() {
		try {
			//名师推荐列表
			TreeSet<RecommendTeacherInfo> teachers = appTeacherMapper.findRecommendTeacher();
			for(RecommendTeacherInfo rti : teachers) {
				rti.setTeacherurl(ConstantIF.PROJECT + rti.getTeacherurl());
			}
			return new ResultBean(teachers);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: teacherDetail
	 * @Description: 名师详情页
	 * @return
	 * @throws:
	 * @time: 2018年3月7日 下午4:44:50
	 */
	@LogOp(method = "teacherDetail", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "名师详情页")
	@ApiOperation(value = "名师详情页", httpMethod = "GET", notes = "名师详情页", response = ResultBean.class)
	@RequestMapping(value = "teacherdetail", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean teacherDetail(@ApiParam("教师id") @RequestParam(value = "teacherid") String teacherid) {
		try {
			UserInfo teacherDetail = appTeacherService.getTeacherDetail(teacherid);
			return new ResultBean(teacherDetail);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: teacherCourseList
	 * @Description: 教师的课程列表 
	 * @param teacherid
	 * @param type （1：免费直播课程2：vip实战直播课程3：精品课4：私教课）
	 * @return
	 * @throws:
	 * @time: 2018年3月7日 下午5:23:11
	 */
	@LogOp(method = "teacherCourseList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "教师的课程列表")
	@ApiOperation(value = "教师的课程列表", httpMethod = "GET", notes = "教师的课程列表", response = ResultBean.class)
	@RequestMapping(value = "teachercourselist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean teacherCourseList(
			@ApiParam("教师id") @RequestParam(value = "teacherid") String teacherid,
			@ApiParam("课程类型（1：免费直播课程2：vip实战直播课程3：精品课4：私教课）") @RequestParam(value = "type") int type) {
		try {
			List<AppRecommendCourse> courseList = appTeacherService.findTeacherCourseList(teacherid, type);
			return new ResultBean(courseList);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
}
