package dianfan.controller.admin;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.teacher.TeaCourseMapper;
import dianfan.entities.DataTable;
import dianfan.entities.LiveCourse;
import dianfan.entities.ResultBean;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.LiveCourseService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName LiveCourseManage
 * @Description 直播课程管理
 * @author cjy
 * @date 2018年3月8日 上午11:01:28
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class LiveCourseManage {
	@Autowired
	private TeaCourseMapper teaCourseMapper;
	@Autowired
	private LiveCourseService liveCourseService;

	/**
	 * @Title: dashboardLiveCourse
	 * @Description: 直播课程管理页
	 * @return
	 * @throws:
	 * @time: 2018年3月8日 上午11:23:47
	 */
	@LogOp(method = "dashboardLiveCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播课程管理页")
	@RequestMapping(value = "livecourse", method = { RequestMethod.GET })
	public String dashboardLiveCourse(Model model) {
		try {
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.ADMIN_COURSE + "live-course-list";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: liveCourseList
	 * @Description: 获取直播课程列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月8日 上午11:23:29
	 */
	@LogOp(method = "liveCourseList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取直播课程列表")
	@RequestMapping(value = "livecourselist")
	public @ResponseBody DataTable liveCourseList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 代理商
			param.put("agentname", request.getParameter("agentname"));
			// 讲师
			param.put("teachername", request.getParameter("teachername"));
			// 使用课程名称搜索
			param.put("coursename", request.getParameter("coursename"));
			// 直播类型
			param.put("coursekind", request.getParameter("coursekind"));
			// 课程类型
			param.put("kind", request.getParameter("kind"));
			// 课程上下架
			param.put("flag", request.getParameter("flag"));
			// 直播状态
			param.put("liveflag", request.getParameter("liveflag"));
			// 审核状态
			param.put("auth", request.getParameter("auth"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			// 根据条件查询用户列表
			table = liveCourseService.findLiveCourses(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: liveCourseData
	 * @Description: 获取课程数据
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月9日 上午11:51:55
	 */
	@LogOp(method = "liveCourseData", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取课程数据")
	@RequestMapping(value = "livecoursedata")
	public String liveCourseData(HttpServletRequest request, Model model) {
		String courseid = request.getParameter("courseid");
		if (courseid == null || "".equals(courseid)) {
			// 参数错误
			return ResultMsg.ADMIN_500;
		}

		// 是否修改操作
		boolean edit = false;
		if ("edit".equals(request.getParameter("type").trim())) {
			edit = true;
		}

		try {
			// 获取直播课程详情
			LiveCourse courseData = liveCourseService.getLiveCourseData(courseid);
			model.addAttribute("course", courseData);
			if (edit) {
				// 课程类型列表
				List<VipLevel> kinds = teaCourseMapper.findVipLevel();
				model.addAttribute("kind", kinds);
				return ConstantIF.ADMIN_COURSE + "live-course-edit";
			} else {
				return ConstantIF.ADMIN_COURSE + "live-course-detail";
			}
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: editLiveCourse
	 * @Description: 直播课程修改
	 * @param courseimg
	 * @param course
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月9日 下午1:39:15
	 */
	@LogOp(method = "editLiveCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播课程修改")
	@RequestMapping(value = "editlivecourse")
	public @ResponseBody ResultBean editLiveCourse(MultipartFile courseimg, LiveCourse course, HttpSession session) {
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
			liveCourseService.editLiveCourse(course);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
