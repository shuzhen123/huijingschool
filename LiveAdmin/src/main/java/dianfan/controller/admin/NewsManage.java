package dianfan.controller.admin;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.NewsMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.Infomation;
import dianfan.entities.NewsModel;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.NewsService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName NewsManage
 * @Description 咨讯管理
 * @author cjy
 * @date 2018年1月10日 上午10:42:14
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class NewsManage {
	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private NewsService newsService;

	/**
	 * @Title: dashboardNews
	 * @Description: 咨讯管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午5:29:58
	 */
	@LogOp(method = "dashboardNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "咨讯管理页")
	@RequestMapping(value = "news")
	public String dashboardNews(Model model) {
		try {
			// 获取可用模块列表
			List<NewsModel> modelList = newsMapper.findNewsModel(new HashMap<>());
			if (modelList.size() == 0) {
				model.addAttribute("model", null);
			} else {
				model.addAttribute("model", modelList);
			}
		} catch (SQLExecutorException e) {
			return ConstantIF.ERROR_500;
		}
		return ConstantIF.ADMIN_NEWS + "news-list";
	}

	/**
	 * @Title: newsList
	 * @Description: 咨讯列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午5:41:19
	 */
	@LogOp(method = "newsList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "咨讯列表")
	@RequestMapping(value = "newslist")
	public @ResponseBody DataTable newsList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 模块类型
			param.put("informationmodelid", request.getParameter("informationmodelid"));
			// 讲师名称
			param.put("realname", request.getParameter("realname"));
			// 标题
			param.put("infomationtitle", request.getParameter("infomationtitle"));
			// 标签
			param.put("tag", request.getParameter("tag"));
			// 作者
			param.put("singname", request.getParameter("singname"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			// 咨讯列表
			table = newsService.findNews(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: addNewsPage
	 * @Description: 添加咨讯页
	 * @return
	 * @throws:
	 * @time: 2018年1月10日 上午11:35:53
	 */
	@LogOp(method = "addNewsPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加咨讯页")
	@RequestMapping(value = "addnewspage")
	public String addNewsPage(Model model) {
		try {
			// 获取可用模块列表
			List<NewsModel> modelList = newsMapper.findNewsModel(new HashMap<>());
			model.addAttribute("model", modelList);
			// 获取代理商列表
			List<BashMap> agentList = newsMapper.findAgentList();
			model.addAttribute("agent", agentList);
		} catch (SQLExecutorException e) {
			return ConstantIF.ERROR_500;
		}
		return ConstantIF.ADMIN_NEWS + "news-add";
	}

	/**
	 * @Title: findTeacherByAgentid
	 * @Description: 根据代理商获取讲师列表
	 * @param agentid
	 * @return
	 * @throws:
	 * @time: 2018年3月22日 下午1:43:37
	 */
	@LogOp(method = "findTeacherByAgentid", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "根据代理商获取讲师列表")
	@RequestMapping(value = "findteachers")
	public @ResponseBody ResultBean findTeacherByAgentid(String agentid) {
		try {
			List<BashMap> teachers = newsMapper.findTeacherByAgentid(agentid);
			return new ResultBean(teachers);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
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
	 * @throws:
	 * @time: 2018年1月11日 上午11:58:44
	 */
	@LogOp(method = "addNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加咨讯数据")
	@RequestMapping(value = "addnews")
	public @ResponseBody ResultBean addNews(MultipartFile pic, Infomation info, HttpServletRequest request,
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
			newsService.addNewsInfo(info);
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: delNews
	 * @Description: 批量删除咨讯数据
	 * @param modelids
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午1:49:13
	 */
	@LogOp(method = "delNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量删除咨讯数据")
	@RequestMapping(value = "delnews")
	public @ResponseBody ResultBean delNews(@RequestParam(value = "ids[]") String[] ids) {
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
			newsService.delNews(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: editNewsPage
	 * @Description: 修改咨讯页
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午2:19:32
	 */
	@LogOp(method = "editNewsModel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改咨讯页")
	@RequestMapping(value = "editnewspage")
	public String editNewsPage(@RequestParam(value = "id") String id, Model model) {
		try {
			// 获取可用模块列表
			List<NewsModel> modelList = newsMapper.findNewsModel(new HashMap<>());
			model.addAttribute("model", modelList);
			// 获取代理商列表
			List<BashMap> agentList = newsMapper.findAgentList();
			model.addAttribute("agent", agentList);
			// 咨讯数据
			Infomation info = newsService.findNewsInfo(id);
			model.addAttribute("info", info);

			if (info.getUserid() != null) {
				// 根据讲师id获取相同代理商下的讲师
				List<BashMap> teas = newsMapper.findTeachersByTid(info.getUserid());
				model.addAttribute("teas", teas);
				// 根据讲师id获取代理商id
				String agentid = newsMapper.findAgentidByTid(info.getUserid());
				model.addAttribute("agentid", agentid);
			}
		} catch (SQLExecutorException e) {
			return ConstantIF.ERROR_500;
		}
		return ConstantIF.ADMIN_NEWS + "news-edit";
	}

	/**
	 * @Title: editNews
	 * @Description: 修改咨讯
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午2:19:32
	 */
	@LogOp(method = "editNewsModel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改咨讯页")
	@RequestMapping(value = "editnews")
	public @ResponseBody ResultBean editNews(MultipartFile pic, Infomation info, HttpServletRequest request,
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
			newsService.editNewsInfo(info);
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

}
