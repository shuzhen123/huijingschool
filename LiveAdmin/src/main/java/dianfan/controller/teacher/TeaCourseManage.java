package dianfan.controller.teacher;

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
import dianfan.dao.mapper.teacher.TeaCourseMapper;
import dianfan.entities.BashMap;
import dianfan.entities.Course;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.entities.Video;
import dianfan.exception.SQLExecutorException;
import dianfan.service.teacher.TeaCourseService;
import dianfan.util.FileUploadUtils;
import dianfan.vcloud.auth.SumBuilder;
import dianfan.vcloud.config.VcoludConf;

/**
 * @ClassName TeaCourseManage
 * @Description 我的课程管理
 * @author cjy
 * @date 2018年3月10日 下午4:58:48
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/tea")
public class TeaCourseManage {
	@Autowired
	private TeaCourseMapper teaCourseMapper;
	@Autowired
	private TeaCourseService teaCourseService;

	/**
	 * @Title: dashboardCourse
	 * @Description: 课程管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月5日 下午3:03:22
	 */
	@LogOp(method = "dashboardCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程管理首页")
	@RequestMapping(value = "course", method = { RequestMethod.GET })
	public String dashboardCourse(Model model) {
		try {
			List<BashMap> kinds = teaCourseMapper.findCourseKind();
			model.addAttribute("kind", kinds);
			return ConstantIF.TEA_COURSE + "course-list";
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
	@LogOp(method = "courseList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取课程列表")
	@RequestMapping(value = "courselist", method = RequestMethod.POST)
	public @ResponseBody DataTable courseList(HttpServletRequest request) {
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
			// 课程分类
			param.put("coursetypecode", request.getParameter("coursetypecode"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			table = teaCourseService.findCourses(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: courseData
	 * @Description: 课程数据
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月12日 上午9:37:35
	 */
	@LogOp(method = "courseData", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取课程数据")
	@RequestMapping(value = "coursedata", method = RequestMethod.GET)
	public String courseData(String type, String courseid, Model model) {
		try {
			// 课程详情
			Course course = teaCourseMapper.findCourseInfoById(courseid);
			course.setCoursepic(ConstantIF.PROJECT + course.getCoursepic());
			if ("edit".equals(type)) {
				// 编辑模式，获取课程分类
				List<BashMap> kinds = teaCourseMapper.findCourseKind();
				model.addAttribute("kind", kinds);
			}
			model.addAttribute("course", course);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}

		if ("edit".equals(type)) {
			// 编辑模式
			return ConstantIF.TEA_COURSE + "course-edit";
		} else {
			// 课程详情
			return ConstantIF.TEA_COURSE + "course-detail";
		}
	}

	/**
	 * @Title: editCourse
	 * @Description: 课程修改
	 * @param courseimg
	 * @param course
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月12日 上午10:39:57
	 */
	@LogOp(method = "editCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程修改")
	@RequestMapping(value = "editcourse", method = RequestMethod.POST)
	public @ResponseBody ResultBean editCourse(MultipartFile courseimg, Course course, HttpSession session) {
		if (courseimg != null && !courseimg.isEmpty()) {
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
			// 课程修改
			teaCourseService.editCourse(course);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: addCoursePage
	 * @Description: 课程添加页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月12日 上午10:57:55
	 */
	@LogOp(method = "addCoursePage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程添加页")
	@RequestMapping(value = "addcoursepage", method = RequestMethod.GET)
	public String addCoursePage(Model model) {
		try {
			// 获取课程分类
			List<BashMap> courseKind = teaCourseMapper.findCourseKind();
			model.addAttribute("coursekind", courseKind);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.TEA_COURSE + "course-add";
	}

	/**
	 * @Title: addCourse
	 * @Description: 课程添加
	 * @param courseimg
	 * @param session
	 * @param course
	 * @return
	 * @throws:
	 * @time: 2018年3月12日 上午11:01:25
	 */
	@LogOp(method = "addCourse", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程添加")
	@RequestMapping(value = "addcourse", method = RequestMethod.POST)
	public @ResponseBody ResultBean addCourse(MultipartFile courseimg, HttpSession session, Course course) {
		try {
			if (courseimg != null) {
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				String newfilename = FileUploadUtils.uploadOne(courseimg, realPath);
				course.setCoursepic(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			}
		} catch (IOException e) {
			// 上传失败
			return new ResultBean("500", ResultMsg.C_500);
		}

		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			course.setUserid(userInfo.getUserid());
			// 持久化课程数据
			teaCourseService.addCourse(course);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/* *************************课程视频*********************** */
	/**
	 * @Title: courseVideoPage
	 * @Description: 课程视频列表页
	 * @param courseid
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月12日 上午11:16:28
	 */
	@LogOp(method = "courseVideoPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程视频列表")
	@RequestMapping(value = "coursevideopage")
	public String courseVideoPage(@RequestParam String courseid, Model model) {
		model.addAttribute("courseid", courseid);
		try {
			List<Video> videos = teaCourseMapper.findCourseVideoByCourseId(courseid);
			for (Video v : videos) {
				v.setVideoppicurl(ConstantIF.PROJECT + v.getVideoppicurl());
			}
			model.addAttribute("videos", videos);

			// 网易云点播
			String appKey = VcoludConf.config().getConfig("vcloud.key"); // 开发者平台分配的AppKey
			String appSecret = VcoludConf.config().getConfig("vcloud.secret"); // 开发者平台分配的Secret
			// 获取随机数
			StringBuffer random = new StringBuffer();
			for (int i = 0; i < 32; i++) {
				random.append((int) (Math.random() * 9));
			}
			String nonce = random.toString();
			// 当前UTC时间戳
			String curTime = System.currentTimeMillis() + "";
			// SHA1
			String checkSum = SumBuilder.getCheckSum(appSecret, nonce, curTime);
			model.addAttribute("appKey", appKey);
			model.addAttribute("nonce", nonce);
			model.addAttribute("curTime", curTime);
			model.addAttribute("checkSum", checkSum);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.TEA_COURSE + "course-video-list";
	}

	/**
	 * @Title: addVideo
	 * @Description: 上传课程视频
	 * @param videoppicurl
	 * @param vediourl
	 * @param video
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月19日 下午12:54:37
	 */
	@LogOp(method = "addVideo", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "上传课程视频")
	@RequestMapping(value = "addvideo", method = RequestMethod.POST)
	public @ResponseBody ResultBean addVideo(MultipartFile logo, HttpServletRequest request) {
		Video video = new Video();
		video.setCourseid(request.getParameter("courseid"));
		video.setVideoname(request.getParameter("videoname"));
		video.setVideointroduce(request.getParameter("videointroduce"));
		video.setVideocontent(request.getParameter("videocontent"));

		// 绝对上传路径
		String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadvideopath");
		try {
			String newvideoname = FileUploadUtils.uploadOne(logo, realPath);
			video.setVideoppicurl(PropertyUtil.getProperty("uploadvideopath") + newvideoname);
		} catch (IOException e) {
			// 上传失败
			new ResultBean("500", ResultMsg.C_500);
		}

		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			video.setUserid(userInfo.getUserid());
			video.setCreateid(userInfo.getUserid());
			video = teaCourseService.addCourseVideo(video);
		} catch (SQLExecutorException e) {
			new ResultBean("500", ResultMsg.C_500, video.getCreatetime());
		}
		video.setVideoppicurl(ConstantIF.PROJECT + video.getVideoppicurl());
		return new ResultBean(video);
	}

	/**
	 * @Title: updateVideoUrl
	 * @Description: 更新课程视频地址
	 * @return
	 * @throws:
	 * @time: 2018年2月1日 上午11:48:31
	 */
	@LogOp(method = "updateVideoUrl", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "更新课程视频地址")
	@RequestMapping(value = "updatevideourl", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateVideoUrl(String videoid, String filename) {
		try {
			teaCourseService.updateCourseVideoUrl(videoid, filename);
			return new ResultBean(videoid);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500, videoid);
		}
	}

	/**
	 * @Title: editVideo
	 * @Description: 修改课程视频
	 * @param vfile
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月29日 上午9:33:01
	 */
	@LogOp(method = "editVideo", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改课程视频")
	@RequestMapping(value = "editvideo", method = RequestMethod.POST)
	public @ResponseBody ResultBean editVideo(MultipartFile logo, HttpServletRequest request) {
		Video video = new Video();
		video.setVideoid(request.getParameter("videoid"));
		video.setVideoname(request.getParameter("videoname"));
		video.setVideointroduce(request.getParameter("videointroduce"));
		video.setVideocontent(request.getParameter("videocontent"));
		video.setEnableflag(Integer.parseInt(request.getParameter("enableflag")));

		try {
			// 绝对上传路径
			String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadvideopath");

			if (logo != null && !logo.isEmpty()) {
				String newvideoname = FileUploadUtils.uploadOne(logo, realPath);
				video.setVideoppicurl(PropertyUtil.getProperty("uploadvideopath") + newvideoname);
			}
		} catch (IOException e) {
			// 上传失败
			return new ResultBean("500", ResultMsg.C_500, video.getVideoid());
		}

		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			video.setUserid(userInfo.getUserid());
			video.setCreateid(userInfo.getUserid());
			teaCourseService.editCourseVideo(video);
		} catch (SQLExecutorException e) {
			new ResultBean("500", ResultMsg.C_500, video.getVideoid());
		}

		if (video.getVideoppicurl() != null) {
			video.setVideoppicurl(ConstantIF.PROJECT + video.getVideoppicurl());
		}

		return new ResultBean(video);
	}
	
	/**
	 * @Title: videoTranscodeStatus
	 * @Description: 获取视频转码状态
	 * @param vids
	 * @return
	 * @throws:
	 * @time: 2018年5月17日 下午4:07:27
	 */
	@LogOp(method = "videoTranscodeStatus", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取视频转码状态")
	@RequestMapping(value = "videotcsttatus", method = RequestMethod.POST)
	public @ResponseBody ResultBean videoTranscodeStatus(@RequestParam(value="vids[]") String[] vids) {
		try {
			List<Video> status = teaCourseMapper.videoTranscodeStatus(vids);
			return new ResultBean(status);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: delVideo
	 * @Description: 删除课程视频
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年1月26日 下午3:24:55
	 */
	@LogOp(method = "delVideo", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除课程视频")
	@RequestMapping(value = "delvideo", method = RequestMethod.POST)
	public @ResponseBody ResultBean delVideo(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		List<String> lids = new ArrayList<>();
		for (String id : ids) {
			lids.add(id);
		}

		try {
			teaCourseService.delCourseVideo(lids);
		} catch (SQLExecutorException e) {
			new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/* ************************课程评价************************ */

	/**
	 * @Title: dashboardCourseCommont
	 * @Description: 课程评论管理页
	 * @param courseid
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 上午10:43:51
	 */
	@LogOp(method = "dashboardCourseCommont", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程评论管理页")
	@RequestMapping(value = "coursecommentpage")
	public String dashboardCourseCommont(@RequestParam String courseid, String role, Model model) {
		model.addAttribute("role", role);
		model.addAttribute("courseid", courseid);
		return ConstantIF.TEA_COURSE + "commont";
	}

	/**
	 * @Title: courseCommontList
	 * @Description: 课程评论列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 上午11:48:12
	 */
	@LogOp(method = "courseCommontList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程评论列表")
	@RequestMapping(value = "coursecommentlist")
	public @ResponseBody DataTable courseCommontList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			String courseid = request.getParameter("courseid").trim();
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 反馈列表
			table = teaCourseService.findCourseCommontList(courseid, start, length);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: delCourseCommont
	 * @Description: 删除评论
	 * @param id
	 *            评论id
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 下午1:41:17
	 */
	@LogOp(method = "delCourseCommont", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除评论")
	@RequestMapping(value = "delcoursecomment")
	public @ResponseBody ResultBean delCourseCommont(@RequestParam String commentid) {
		if (commentid == null || "".equals(commentid)) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}

		try {
			// 根据评论id删除评论
			teaCourseService.delCourseCommont(commentid);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

}
