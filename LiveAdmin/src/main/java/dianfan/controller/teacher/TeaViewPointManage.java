package dianfan.controller.teacher;

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

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.teacher.TeaNewsPointMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Infomation;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.teacher.TeaNewsPointService;
import dianfan.util.UUIDUtil;

/**
 * @ClassName TeaViewPointManage
 * @Description 牛人观点
 * @author cjy
 * @date 2018年3月27日 下午6:17:10
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/tea")
public class TeaViewPointManage {
	@Autowired
	private TeaNewsPointMapper newsPointMapper;
	@Autowired
	private TeaNewsPointService newsPointService;
	
	/**
	 * @Title: dashboardViewPoint
	 * @Description: 代理商牛人观点页
	 * @param session
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月5日 下午5:21:38
	 */
	@LogOp(method = "dashboardViewPoint", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商牛人观点页")
	@RequestMapping(value = "dashboardviewpoint", method = {RequestMethod.GET})
	public String dashboardViewPoint(Model model) {
		return ConstantIF.TEA_VIPNEWS + "viewpoint-list";
	}
	
	/**
	 * @Title: viewpointList
	 * @Description: 牛人观点列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月5日 下午5:24:33
	 */
	@LogOp(method = "viewpointList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "牛人观点列表")
	@RequestMapping(value = "viewpointlist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DataTable viewpointList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			param.put("teacherid", userInfo.getUserid());
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			String infomationtitle = request.getParameter("infomationtitle");
			param.put("infomationtitle", infomationtitle);
			String starttime = request.getParameter("starttime");
			param.put("starttime", starttime);
			String endtime = request.getParameter("endtime");
			param.put("endtime", endtime);
			
			// 咨讯列表
			table = newsPointService.findViewpoint(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	/**
	 * @Title: addViewpointPage
	 * @Description: 添加牛人观点页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月6日 上午9:09:00
	 */
	@LogOp(method = "addViewpointPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加牛人观点页")
	@RequestMapping(value = "addviewpointpage", method = RequestMethod.GET)
	public String addViewpointPage() {
		return ConstantIF.TEA_VIPNEWS + "viewpoint-add";
	}
	
	/**
	 * @Title: addViewpoint
	 * @Description: 添加牛人观点
	 * @param info
	 * @param request
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月6日 上午9:43:42
	 */
	@LogOp(method = "addViewpoint", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加牛人观点数据")
	@RequestMapping(value = "addviewpoint", method = RequestMethod.POST)
	public @ResponseBody ResultBean addViewpoint(Infomation info, HttpSession session) {
        try {
        	//添加数据
        	UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
        	info.setUserid(userInfo.getUserid());
			newsPointService.addNewsInfo(info);
			return new ResultBean();
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: delViewpoint
	 * @Description: 批量删除观点数据
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年3月6日 上午10:07:28
	 */
	@LogOp(method = "delViewpoint", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量删除观点数据")
	@RequestMapping(value = "delviewpoint", method = RequestMethod.POST)
	public @ResponseBody ResultBean delViewpoint(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		List<String> lids = new ArrayList<>();
		
		for(String id : ids) {
			lids.add(id);
		}
		
		// 根据id删除咨讯数据
		try {
			newsPointService.delNews(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: editViewpointPage
	 * @Description: 修改观点页
	 * @param id
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月6日 上午10:09:17
	 */
	@LogOp(method = "editViewpointPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改观点页")
	@RequestMapping(value = "editviewpointpage", method = RequestMethod.GET)
	public String editViewpointPage(@RequestParam(value = "id") String id, Model model) {
		try {
			//观点数据
			Infomation info = newsPointMapper.findNewsPointInfoById(id);
			model.addAttribute("info", info);
		} catch (SQLExecutorException e) {
			return ConstantIF.ERROR_500;
		}
		return ConstantIF.TEA_VIPNEWS + "viewpoint-edit";
	}
	
	/**
	 * @Title: editViewpoint
	 * @Description: 修改观点
	 * @param info
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月6日 上午10:12:42
	 */
	@LogOp(method = "editViewpoint", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改观点")
	@RequestMapping(value = "editviewpoint", method = RequestMethod.POST)
	public @ResponseBody ResultBean editViewpoint(Infomation info, HttpSession session) {
        try {
        	//修改观点数据
			newsPointService.editViewpointInfo(info);
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
