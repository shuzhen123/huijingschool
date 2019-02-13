package dianfan.controller.teacher;

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
import dianfan.dao.mapper.teacher.TeaLiveCourseMapper;
import dianfan.entities.Course;
import dianfan.entities.DataTable;
import dianfan.entities.LiveCourse;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.teacher.TeaLiveCourseService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName TeaLiveCourseManage
 * @Description 我的直播课程管理
 * @author cjy
 * @date 2018年3月26日 上午9:50:33
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/tea")
public class TeaLiveCourseManage {
	@Autowired
	private TeaLiveCourseService teaLiveCourseService;
	@Autowired
	private TeaLiveCourseMapper teaLiveCourseMapper;
	@Autowired
	private TeaCourseMapper teaCourseMapper;

	/**
	 * @Title: dashboardLiveCourse
	 * @Description: 直播课程管理首页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 上午9:51:21
	 */
	@LogOp(method = "dashboardLiveCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播课程管理首页")
	@RequestMapping(value = "livecourse", method = { RequestMethod.GET })
	public String dashboardLiveCourse(Model model) {
		try {
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.TEA_COURSE + "live-course-list";
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
	 * @time: 2018年3月26日 上午10:48:42
	 */
	@LogOp(method = "courseList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取直播课程列表")
	@RequestMapping(value = "livecourselist", method = RequestMethod.POST)
	public @ResponseBody DataTable liveCourseList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			// 根据条件查询用户列表
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			param.put("teacherid", userInfo.getUserid());
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 使用课程名称搜索
			param.put("coursename", request.getParameter("coursename"));
			// 课程类型
			param.put("coursekind", request.getParameter("coursekind"));
			// 课程上下架
			param.put("flag", request.getParameter("flag"));
			// 直播状态
			param.put("liveflag", request.getParameter("liveflag"));
			// 审核状态
			param.put("auth", request.getParameter("auth"));
			// 课程分类
			param.put("kind", request.getParameter("kind"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			table = teaLiveCourseService.findLiveCourses(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: addLiveCoursePage
	 * @Description: 直播课程添加页
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午6:09:17
	 */
	@LogOp(method = "addLiveCoursePage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播课程添加页")
	@RequestMapping(value = "addlivecoursepage", method = { RequestMethod.GET })
	public String addLiveCoursePage(Model model, HttpSession session) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			model.addAttribute("teacherid", userInfo.getUserid());
			// 课程类型列表
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.TEA_COURSE + "live-course-add";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: addLiveCourse
	 * @Description: 直播课程添加
	 * @param course
	 * @param file
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws:
	 * @time: 2018年3月27日 上午9:22:24
	 */
	@LogOp(method = "addLiveCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播课程添加")
	@RequestMapping(value = "addlivecourse", method = { RequestMethod.POST })
	public @ResponseBody ResultBean addLiveCourse(Course course, MultipartFile file, String starttime, String endtime,
			HttpSession session) {
		try {
			//直播课程时间冲突性检测
			boolean repet = teaLiveCourseMapper.checkLiveCourseTime(course.getUserid(), starttime, endtime);
			if(repet) {
				return new ResultBean("025", ResultMsg.C_025);
			}
		} catch (SQLExecutorException e1) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
		if (file != null && !file.isEmpty()) {
			try {
				//上传展示图
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				String newfilename = FileUploadUtils.uploadOne(file, realPath);
				course.setCoursepic(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 课程展示图上传失败
			}
		}

		try {
			// 添加直播课程
			teaLiveCourseService.addLiveCourse(course, starttime, endtime);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: editLiveCoursePage
	 * @Description: 直播课程修改页
	 * @return
	 * @throws:
	 * @time: 2018年3月27日 下午1:30:28
	 */
	@LogOp(method = "editLiveCoursePage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播课程修改页")
	@RequestMapping(value = "editlivecoursepage", method = { RequestMethod.GET })
	public String editLiveCoursePage(String courseid, Model model) {
		try {
			LiveCourse course = teaLiveCourseMapper.getLiveCourseInfo(courseid);
			if (course != null) {
				course.setCoursepic(ConstantIF.PROJECT + course.getCoursepic());
			}
			model.addAttribute("course", course);
			// 课程类型列表
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.TEA_COURSE + "live-course-edit";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: editLiveCourse
	 * @Description: 直播课程修改
	 * @param course
	 * @param file
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月27日 下午3:07:23
	 */
	@LogOp(method = "editLiveCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播课程修改")
	@RequestMapping(value = "editlivecourse", method = { RequestMethod.POST })
	public @ResponseBody ResultBean editLiveCourse(Course course, MultipartFile file, String starttime, String endtime,
			HttpSession session) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		course.setUserid(userInfo.getUserid());
		try {
			//直播课程时间冲突性检测(排除当前直播课程)
			boolean repet = teaLiveCourseMapper.checkLiveCourseTimeExcCourseid(userInfo.getUserid(), course.getCourseid(), starttime, endtime);
			if(repet) {
				return new ResultBean("025", ResultMsg.C_025);
			}
		} catch (SQLExecutorException e1) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
		if (file != null && !file.isEmpty()) {
			try {
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				String newfilename = FileUploadUtils.uploadOne(file, realPath);
				course.setCoursepic(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 课程展示图上传失败
			}
		}

		try {
			// 直播课程修改
			teaLiveCourseService.editLiveCourse(course, starttime, endtime);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: liveCourseDetail
	 * @Description: 直播课程详情页
	 * @param courseid
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月27日 下午4:11:28
	 */
	@LogOp(method = "liveCourseDetail", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播课程详情页")
	@RequestMapping(value = "livecoursedetail", method = { RequestMethod.GET })
	public String liveCourseDetail(String courseid, Model model) {
		try {
			LiveCourse course = teaLiveCourseMapper.getLiveCourseInfoAndVideo(courseid);
			if (course != null) {
				course.setCoursepic(ConstantIF.PROJECT + course.getCoursepic());
			}
			model.addAttribute("course", course);
			return ConstantIF.TEA_COURSE + "live-course-detail";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}
}
