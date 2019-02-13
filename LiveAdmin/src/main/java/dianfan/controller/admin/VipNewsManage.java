package dianfan.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.teacher.TeaCourseMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.VipANewsService;

/**
 * @ClassName VipNewsManage
 * @Description vip咨讯管理
 * @author cjy
 * @date 2018年2月27日 下午1:36:54
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class VipNewsManage {
	@Autowired
	private RedisService redisService;
	@Autowired
	private VipANewsService vipNewsService;
	@Autowired
	private TeaCourseMapper teaCourseMapper;
	
	/**
	 * @Title: dashboardVipNews
	 * @Description: vip咨讯管理页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 下午1:40:28
	 */
	@LogOp(method = "dashboardvipNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip咨讯管理页")
	@RequestMapping(value = "vipnews", method = {RequestMethod.GET})
	public String dashboardVipNews(Model model) {
		//获取vip策略审核需求
		String check = redisService.get(ConstantIF.DEF_VIP_NEWS_KEY);
		if(check == null || check.trim().isEmpty()) {
			check = "0";
		}
		model.addAttribute("check", check);
		try {
			List<VipLevel> kinds = teaCourseMapper.findVipLevel();
			model.addAttribute("kind", kinds);
			return ConstantIF.ADMIN_NEWS + "vipnews-list";
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
	 * @time: 2018年2月27日 下午1:44:26
	 */
	@LogOp(method = "vipNewsList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip咨讯列表")
	@RequestMapping(value = "vipnewslist")
	public @ResponseBody DataTable vipNewsList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> searchMap = new HashMap<>();
			//搜索条件
			searchMap.put("showflag", request.getParameter("showflag"));
			searchMap.put("articaltitle", request.getParameter("articaltitle"));
			searchMap.put("starttime", request.getParameter("starttime"));
			searchMap.put("endtime", request.getParameter("endtime"));
			searchMap.put("checkflag", request.getParameter("check"));
			searchMap.put("kind", request.getParameter("kind"));
			//基础数据
			searchMap.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			searchMap.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			// 咨讯列表
			table = vipNewsService.findVipNews(searchMap);
		} catch (SQLExecutorException e) {
			// TODO 错误处理
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	/**
	 * @Title: delVipNews
	 * @Description: 批量删除vip咨讯数据
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 下午2:09:18
	 */
	@LogOp(method = "delVipNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量删除咨讯数据")
	@RequestMapping(value = "delvipnews", method = {RequestMethod.POST})
	public String delVipNews(@RequestParam(value = "ids[]") String[] ids) {
		return "forward:/agent/delvipnews";
	}
	
	/**
	 * @Title: vipNewsCheck
	 * @Description: vip咨讯审核
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 下午2:48:29
	 */
	@LogOp(method = "vipNewsCheck", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip咨讯审核")
	@RequestMapping(value = "vipnewscheck", method = {RequestMethod.POST})
	public @ResponseBody ResultBean vipNewsCheck(HttpServletRequest request) {
		try {
			Map<String, Object> checkMap = new HashMap<>();
			checkMap.put("checkflag", request.getParameter("checkflag"));
			checkMap.put("cause", request.getParameter("cause"));
			checkMap.put("id", request.getParameter("id"));
			vipNewsService.checkVipNews(checkMap);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500); 
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: changeAutoCheck
	 * @Description: 更改自动审核
	 * @param check
	 * @return
	 * @throws:
	 * @time: 2018年2月27日 下午3:12:29
	 */
	@LogOp(method = "vipNewsCheck", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip咨讯审核")
	@RequestMapping(value = "changautocheck", method = {RequestMethod.POST})
	public @ResponseBody ResultBean changeAutoCheck(@RequestParam(value = "check") String check) {
		redisService.set(ConstantIF.DEF_VIP_NEWS_KEY, check);
		return new ResultBean();
	}
}
