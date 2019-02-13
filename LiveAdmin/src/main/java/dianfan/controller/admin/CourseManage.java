package dianfan.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.CourseMapper;
import dianfan.entities.BashMap;
import dianfan.entities.Course;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.CourseService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName CourseManage
 * @Description 课程管理
 * @author cjy
 * @date 2018年3月22日 下午1:32:46
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class CourseManage {
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private RedisService<?,?> redisService;

	/**
	 * @Title: dashboardLiveCourse
	 * @Description: 课程管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月5日 下午3:03:22
	 */
	@LogOp(method = "dashboardLiveCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程管理首页")
	@RequestMapping(value = "dashboardcourse", method = { RequestMethod.GET })
	public String dashboardLiveCourse(HttpServletRequest request, Model model) {
		try {
			String time_limit = redisService.get(ConstantIF.COURSE_BY_TIME);
			if (time_limit == null || time_limit.trim().isEmpty()) {
				time_limit = "30";
			}
			model.addAttribute("time_limit", time_limit);
			// 获取课程分类列表
			List<BashMap> kinds = courseMapper.findCourseKind();
			model.addAttribute("kind", kinds);
			return ConstantIF.ADMIN_COURSE + "course-list";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: courseList
	 * @Description: 课程列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月6日 下午1:18:59
	 */
	@LogOp(method = "courseList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取用户列表")
	@RequestMapping(value = "courselist", method = { RequestMethod.POST })
	public @ResponseBody DataTable courseList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 代理商
			param.put("agentname", request.getParameter("agentname"));
			// 教师名称
			param.put("realname", request.getParameter("realname"));
			// 课程名称
			param.put("coursename", request.getParameter("coursename"));
			// 课程类型
			param.put("coursetype", request.getParameter("coursetype"));
			// 课程分类
			param.put("courseclass", request.getParameter("courseclass"));
			// 推荐分类
			param.put("recommend", request.getParameter("recommend"));
			// 审核状态
			param.put("check", request.getParameter("check"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			// 根据条件查询用户列表
			table = courseService.findCourses(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: setCourseByTimeLimit
	 * @Description: 设置课程购买期限
	 * @param days
	 * @return
	 * @throws:
	 * @time: 2018年4月4日 下午3:11:46
	 */
	@LogOp(method = "setCourseByTimeLimit", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "设置课程购买期限")
	@RequestMapping(value = "setcoursebytimelimit", method = { RequestMethod.POST })
	public @ResponseBody ResultBean setCourseByTimeLimit(String days) {
		if (days == null || Integer.parseInt(days) < 0) {
			return new ResultBean("501", ResultMsg.C_501);
		}
		redisService.set(ConstantIF.COURSE_BY_TIME, days);
		return new ResultBean();
	}

	/**
	 * @Title: courseEditPage
	 * @Description: 课程修改页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月6日 下午2:30:05
	 */
	@LogOp(method = "courseEditPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程修改页")
	@RequestMapping(value = "courseeditpage", method = { RequestMethod.GET })
	public String courseEditPage(String courseid, Model model) {
		try {
			// 课程详情
			Course course = courseMapper.findCourseInfoById(courseid);
			if (course != null) {
				course.setCoursepic(ConstantIF.PROJECT + course.getCoursepic());
			}
			model.addAttribute("course", course);
			// 获取课程分类列表
			List<BashMap> kinds = courseMapper.findCourseKind();
			model.addAttribute("kind", kinds);
			return ConstantIF.ADMIN_COURSE + "course-edit";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: editCourse
	 * @Description: 课程修改
	 * @param course
	 * @return
	 * @throws:
	 * @time: 2018年1月8日 上午10:05:36
	 */
	@LogOp(method = "editCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程修改")
	@RequestMapping(value = "editcourse", method = { RequestMethod.POST })
	public @ResponseBody ResultBean editCourse(MultipartFile courseimg, Course course, HttpSession session) {
		if (courseimg != null) {
			try {
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				// 上传展示图
				String newfilename = FileUploadUtils.uploadOne(courseimg, realPath);
				course.setCoursepic(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 展示图上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		}

		try {
			courseService.editCourse(course);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: delCourse
	 * @Description: 删除课程
	 * @param userids
	 * @return
	 * @throws:
	 * @time: 2018年1月6日 下午2:04:03
	 */
	@LogOp(method = "delCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除课程")
	@RequestMapping(value = "delcourse", method = { RequestMethod.POST })
	public @ResponseBody ResultBean delCourse(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		List<String> lids = new ArrayList<>();
		for (String id : ids) {
			lids.add(id);
		}
		// 根据课程id删除课程
		try {
			courseService.delCourse(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
