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
import dianfan.entities.CourseType;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.CourseService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName CourseTypeManage
 * @Description 课程分类管理
 * @author cjy
 * @date 2018年1月31日 上午10:14:13
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class CourseTypeManage {
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private CourseService courseService;
	@Autowired
	private RedisService<?,?> redisService;

	/**
	 * @Title: dashboardCourseType
	 * @Description: 课程分类管理页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午10:16:58
	 */
	@LogOp(method = "dashboardCourseType", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程分类管理页")
	@RequestMapping(value = "coursetypepage", method = { RequestMethod.GET })
	public String dashboardCourseType(HttpServletRequest request, Model model) {
		return ConstantIF.ADMIN_COURSE + "course-type-list";
	}

	/**
	 * @Title: courseTypeList
	 * @Description: 课程分类列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午10:24:05
	 */
	@LogOp(method = "courseTypeList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程分类列表")
	@RequestMapping(value = "coursetypelist", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody DataTable courseTypeList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			// 根据条件查询列表
			table = courseService.findCourseTypeList(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: delCourseType
	 * @Description: 删除课程类型
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午10:44:48
	 */
	@LogOp(method = "delCourseType", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除课程类型")
	@RequestMapping(value = "delcoursetype", method = RequestMethod.POST)
	public @ResponseBody ResultBean delCourseType(@RequestParam(value = "ids[]") String[] ids) {
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
			courseService.delCourseType(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: addCourseType
	 * @Description: 课程类型添加
	 * @param session
	 * @param name
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午11:12:26
	 */
	@LogOp(method = "addCourseType", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程类型添加")
	@RequestMapping(value = "addcoursetype", method = RequestMethod.POST)
	public @ResponseBody ResultBean addCourseType(String name) {
		try {
			// 类型名称重复性检测
			Boolean bool = courseMapper.checkCourseTypeName(name);
			if (bool) {
				return new ResultBean("020", ResultMsg.C_020);
			}
			courseService.addCourseType(name);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: editCourseType
	 * @Description: 课程类型修改
	 * @param session
	 * @param name
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午11:26:59
	 */
	@LogOp(method = "editCourseType", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程类型修改")
	@RequestMapping(value = "editcoursetype", method = RequestMethod.POST)
	public @ResponseBody ResultBean editCourseType(HttpSession session, CourseType type) {
		try {
			// 类型名称重复性检测
			Boolean bool = courseMapper.checkCourseTypeId(type);
			if (bool) {
				return new ResultBean("020", ResultMsg.C_020);
			}
			courseService.editCourseType(type);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: dashboardLivePoster
	 * @Description: 直播展示图管理页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午11:39:47
	 */
	@LogOp(method = "dashboardLivePoster", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "直播展示图管理页")
	@RequestMapping(value = "liveposterpage", method = { RequestMethod.GET })
	public String dashboardLivePoster(HttpServletRequest request, Model model) {
		model.addAttribute("logol", ConstantIF.PROJECT + redisService.get("livelogol"));
		model.addAttribute("logor", ConstantIF.PROJECT + redisService.get("livelogor"));
		return ConstantIF.ADMIN_COURSE + "live-poster";
	}

	/**
	 * @Title: changeLiveLogo
	 * @Description: 上传/更改直播展示图
	 * @param logo
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午11:53:19
	 */
	@LogOp(method = "changeQRCode", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "上传/更改直播展示图")
	@RequestMapping(value = "changelivelogo", method = RequestMethod.POST)
	public @ResponseBody ResultBean changeLiveLogo(@RequestParam("logo") MultipartFile logo,
			HttpServletRequest request) {
		String type = (String) request.getParameter("type");
		if (type == null && !"l".equals(type.trim()) && !"r".equals(type.trim())) {
			return new ResultBean("500", ResultMsg.C_501);
		}
		String domain = PropertyUtil.getProperty("domain");
		String url = PropertyUtil.getProperty("uploadlivelogopath");
		try {
			String newname = FileUploadUtils.uploadOne(logo, domain + url);
			// 存入redis
			redisService.set("livelogo" + type, url + newname);
			return new ResultBean(ConstantIF.PROJECT + url + newname);
		} catch (IOException e) {
			// 上传失败
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
}
