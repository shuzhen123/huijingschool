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
import dianfan.dao.mapper.teacher.TeaVipNewsMapper;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.entities.VipInfomation;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.teacher.TeaVipNewsService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName TeaVipNewsManage
 * @Description VIP咨讯管理
 * @author cjy
 * @date 2018年4月4日 上午9:20:11
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/tea")
public class TeaVipNewsManage {
	@Autowired
	private TeaVipNewsMapper vipNewsMapper;
	@Autowired
	private TeaVipNewsService vipNewsService;
	@Autowired
	private TeaCourseMapper teaCourseMapper;

	/**
	 * @Title: dashboardVipNews
	 * @Description: vip咨讯管理页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年2月26日 下午5:26:28
	 */
	@LogOp(method = "dashboardVipNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip咨讯管理页")
	@RequestMapping(value = "vipnews", method = { RequestMethod.GET })
	public String dashboardVipNews(Model model) {
		try {
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.TEA_VIPNEWS + "vipnews-list";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: vipNewsList
	 * @Description: vip咨讯列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月26日 下午5:39:07
	 */
	@LogOp(method = "newsList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip咨讯列表")
	@RequestMapping(value = "vipnewslist", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody DataTable vipNewsList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);

			Map<String, Object> param = new HashMap<>();
			// 搜索条件
			param.put("showflag", request.getParameter("showflag"));
			param.put("articaltitle", request.getParameter("articaltitle"));
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			param.put("checkflag", request.getParameter("check"));
			param.put("kind", request.getParameter("kind"));
			// 基础数据
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			param.put("teacherid", userInfo.getUserid());
			// 咨讯列表
			table = vipNewsService.findVipNews(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: addVipNewsPage
	 * @Description: 添加vip咨讯页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年2月26日 下午6:10:11
	 */
	@LogOp(method = "addVipNewsPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加vip咨讯页")
	@RequestMapping(value = "addvipnewspage", method = { RequestMethod.GET })
	public String addVipNewsPage(Model model) {
		try {
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.TEA_VIPNEWS + "vipnews-add";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: addNews
	 * @Description: 添加咨讯数据
	 * @param avator
	 * @param info
	 * @param request
	 * @param session
	 * @return
	 * @throws IOException
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月11日 上午11:58:44
	 */
	@LogOp(method = "addVipNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加咨讯数据")
	@RequestMapping(value = "addvipnews", method = { RequestMethod.POST })
	public @ResponseBody ResultBean addVipNews(MultipartFile pic, VipInfomation info, HttpServletRequest request,
			HttpSession session) {
		try {
			if (pic != null) {
				// 绝对上传路径
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				// 上传缩略图
				String newfilename = FileUploadUtils.uploadOne(pic, realPath);
				info.setPicurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			}
			// 添加咨讯数据
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			info.setUserid(userInfo.getUserid());
			vipNewsService.addVipNewsInfo(info);
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: delVipNews
	 * @Description: 批量删除咨讯数据
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 上午9:13:39
	 */
	@LogOp(method = "delVipNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量删除咨讯数据")
	@RequestMapping(value = "delvipnews", method = { RequestMethod.POST })
	public @ResponseBody ResultBean delVipNews(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}

		List<String> lids = new ArrayList<>();

		for (String id : ids) {
			lids.add(id);
		}

		// 根据id删除咨讯数据
		try {
			vipNewsService.delVipNews(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: editVipNewsPage
	 * @Description: 修改咨讯页
	 * @param id
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 上午9:19:17
	 */
	@LogOp(method = "editVipNewsPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改咨讯页")
	@RequestMapping(value = "editvipnewspage", method = { RequestMethod.GET })
	public String editVipNewsPage(@RequestParam(value = "id") String id, Model model) {
		try {
			// 咨讯数据
			VipInfomation infomation = vipNewsMapper.findNewsInfoById(id);
			if (infomation.getPicurl() != null && !infomation.getPicurl().isEmpty()) {
				infomation.setPicurl(ConstantIF.PROJECT + infomation.getPicurl());
			}
			model.addAttribute("info", infomation);
			// 类型列表
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.TEA_VIPNEWS + "vipnews-edit";
		} catch (SQLExecutorException e) {
			return ConstantIF.ERROR_500;
		}
	}

	/**
	 * @Title: editVipNews
	 * @Description: 修改咨讯
	 * @param pic
	 * @param info
	 * @param request
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 上午9:20:15
	 */
	@LogOp(method = "editVipNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改咨讯页")
	@RequestMapping(value = "editvipnews", method = { RequestMethod.POST })
	public @ResponseBody ResultBean editVipNews(MultipartFile pic, VipInfomation info, HttpServletRequest request,
			HttpSession session) {

		try {
			if (pic != null) {
				// 绝对上传路径
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				// 上传缩略图
				String newfilename = FileUploadUtils.uploadOne(pic, realPath);
				info.setPicurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			}
			// 修改咨讯数据
			vipNewsService.editVipNewsInfo(info);
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

}
