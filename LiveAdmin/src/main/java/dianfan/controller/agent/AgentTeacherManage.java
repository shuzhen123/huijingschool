package dianfan.controller.agent;

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
import dianfan.dao.mapper.agent.AgentTeacherMapper;
import dianfan.dao.mapper.teacher.TeaCourseMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.exception.VCloudException;
import dianfan.service.RedisService;
import dianfan.service.admin.AgentService;
import dianfan.service.agent.AgentTeacherService;
import dianfan.service.teacher.TeaCourseService;
import dianfan.service.teacher.TeaLiveCourseService;
import dianfan.util.FileUploadUtils;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AgentTeacherManage
 * @Description 教师管理
 * @author cjy
 * @date 2018年3月20日 下午4:20:06
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentTeacherManage {
	@Autowired
	private AgentTeacherService teacherService;
	@Autowired
	private AgentTeacherMapper teacherMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private TeaCourseService teaCourseService;
	@Autowired
	private TeaCourseMapper teaCourseMapper;
	@Autowired
	private TeaLiveCourseService teaLiveCourseService;

	/**
	 * @Title: dashboardTeacher
	 * @Description: 教师管理首页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 下午4:21:04
	 */
	@LogOp(method = "dashboardTeacher", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "教师管理首页")
	@RequestMapping(value = "teacher", method = { RequestMethod.GET })
	public String dashboardTeacher(Model model) {
		return ConstantIF.AGENT_TEA + "teacher-list";
	}

	/**
	 * @Title: teacherList
	 * @Description: 教师列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 下午4:25:29
	 */
	@LogOp(method = "teacherList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "教师列表")
	@RequestMapping(value = "teacherlist", method = RequestMethod.POST)
	public @ResponseBody DataTable teacherList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("agentid", userInfo.getUserid());
			}
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 讲师账号
			param.put("username", request.getParameter("username"));
			// 讲师名称
			param.put("realname", request.getParameter("realname"));
			// 直播推荐
			param.put("trecflag", request.getParameter("live"));
			// 名师推荐
			param.put("famousteacherflag", request.getParameter("tea"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			// 根据条件查询用户列表
			table = teacherService.findTeachers(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}

	/**
	 * @Title: addTeacherPage
	 * @Description: 讲师添加页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 下午5:28:22
	 */
	@LogOp(method = "addTeacherPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "讲师添加页")
	@RequestMapping(value = "addteacherpage", method = RequestMethod.GET)
	public String addTeacherPage(Model model) {
		// 生成代理商用户名，且不能更改
		String username = redisService.incr(ConstantIF.USERNAME_KEY);
		model.addAttribute("username", username);
		return ConstantIF.AGENT_TEA + "teacher-add";
	}

	/**
	 * @Title: addTeacher
	 * @Description: 添加教师数据
	 * @param avator
	 * @param pic
	 * @param info
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 下午5:38:05
	 */
	@LogOp(method = "addTeacher", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加教师数据")
	@RequestMapping(value = "addteacher", method = RequestMethod.POST)
	public @ResponseBody ResultBean addTeacher(MultipartFile avator, MultipartFile pic, UserInfo info,
			HttpSession session) {
		// 绝对上传路径
		String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
		if (avator != null && !avator.isEmpty()) {
			try {
				// 上传头像
				String newfilename = FileUploadUtils.uploadOne(avator, realPath);
				info.setIconurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 头像上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		}
		if (pic != null && !pic.isEmpty()) {
			try {
				// 上传教师展示图
				String newfilename = FileUploadUtils.uploadOne(pic, realPath);
				info.setTeacherurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 展示图上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		}

		try {
			// 添加创建者id
			UserInfo sess = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			info.setUserid(UUIDUtil.getUUID());
			if(6 == sess.getRole())
			{
				info.setCreateid(sess.getAgentid());
			}
			else
			{
				info.setCreateid(sess.getUserid());
			}
			// 添加新讲师
			teacherService.addTeacherInfo(info);
			return new ResultBean();
		} catch (SQLExecutorException | IOException | VCloudException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: teacherData
	 * @Description: 讲师数据页
	 * @param agentid
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 下午6:11:53
	 */
	@LogOp(method = "teacherData", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "讲师数据编辑页")
	@RequestMapping(value = "teacherdate", method = RequestMethod.GET)
	public String teacherData(String teacherid, String type, Model model) {
		try {
			// 讲师数据详情
			UserInfo info = teacherMapper.getTeacherInfo(teacherid);
			if (info != null) {
				info.setIconurl(ConstantIF.PROJECT + info.getIconurl());
				info.setTeacherurl(ConstantIF.PROJECT + info.getTeacherurl());
			}
			model.addAttribute("userInfo", info);

			if (type == null) {
				// 讲师详情页
				return ConstantIF.AGENT_TEA + "teacher-detail";
			} else {
				return ConstantIF.AGENT_TEA + "teacher-edit";
			}
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: editTeacher
	 * @Description: 保存修改的教师数据
	 * @param avator
	 * @param pic
	 * @param info
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 下午6:27:44
	 */
	@LogOp(method = "editTeacher", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "保存修改的教师数据")
	@RequestMapping(value = "editteacher", method = RequestMethod.POST)
	public @ResponseBody ResultBean editTeacher(MultipartFile avator, MultipartFile pic, UserInfo info,
			HttpSession session) {
		// 绝对上传路径
		String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
		if (avator != null && !avator.isEmpty()) {
			try {
				// 上传头像
				String newfilename = FileUploadUtils.uploadOne(avator, realPath);
				info.setIconurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 头像上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		}
		if (pic != null && !pic.isEmpty()) {
			try {
				// 上传教师展示图
				String newfilename = FileUploadUtils.uploadOne(pic, realPath);
				info.setTeacherurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 展示图上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		}

		try {
			// 更新讲师数据
			teacherService.editTeacherInfo(info);
			return new ResultBean();
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: teacherCoursePage
	 * @Description: 讲师课程列表页
	 * @param teacherid
	 * @param type
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月28日 上午10:25:19
	 */
	@LogOp(method = "teacherCoursePage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "讲师课程列表页")
	@RequestMapping(value = "teachercoursepage", method = RequestMethod.GET)
	public String teacherCoursePage(String teacherid, String type, Model model) {
		try {
			model.addAttribute("teacherid", teacherid);
			if (type == null) {
				// 获取课程类型列表
				List<BashMap> kinds = teaCourseMapper.findCourseKind();
				model.addAttribute("kind", kinds);
				// 上传课程列表页
				return ConstantIF.AGENT_TEA + "teacher-upload-course";
			} else {
				// 课程类型列表
				List<VipLevel> kinds = teaCourseMapper.findVipLevel();
				model.addAttribute("kind", kinds);
				// 直播课程列表页
				return ConstantIF.AGENT_TEA + "teacher-live-course";
			}
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: teacherCourseList
	 * @Description: 获取讲师上传课程列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月21日 下午1:38:05
	 */
	@LogOp(method = "teacherCourseList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取讲师上传课程列表")
	@RequestMapping(value = "teachercourselist", method = RequestMethod.POST)
	public @ResponseBody DataTable teacherCourseList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			// 根据条件查询用户列表
			param.put("teacherid", request.getParameter("teacherid"));
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
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}

	/**
	 * @Title: liveCourseList
	 * @Description: 获取直播课程列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 上午10:48:42
	 */
	@LogOp(method = "liveCourseList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取直播课程列表")
	@RequestMapping(value = "livecourselist", method = RequestMethod.POST)
	public @ResponseBody DataTable liveCourseList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			// 根据条件查询用户列表
			param.put("teacherid", request.getParameter("teacherid"));
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
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
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
	public String addLiveCoursePage(String teacherid, Model model) {
		model.addAttribute("teacherid", teacherid);
		try {
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.TEA_COURSE + "live-course-add";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}
}
