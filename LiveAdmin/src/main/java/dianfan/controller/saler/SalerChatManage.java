package dianfan.controller.saler;

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

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.saler.SalerChatMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.Reperecs;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.saler.SalerChatService;

/**
 * @ClassName SalerChatManage
 * @Description 客户交互管理
 * @author cjy
 * @date 2018年3月12日 上午11:48:04
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/saler")
public class SalerChatManage {

	@Autowired
	private SalerChatService salerChatService;
	@Autowired
	private SalerChatMapper salerChatMapper;
	
	/**
	 * @Title: dashboardChatRecord
	 * @Description: 客户记录管理页
	 * @param request 
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月13日 上午11:48:50
	 */
	@LogOp(method = "dashboardChatRecord", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户记录管理页")
	@RequestMapping(value = "chatrecord", method = RequestMethod.GET)
	public String dashboardChatRecord(HttpServletRequest request, Model model) {
		String type = request.getParameter("type");
		if(type == null || (!"1".equals(type.trim()) && !"2".equals(type.trim()))) {
			//未知记录类型
			return ResultMsg.ADMIN_500;
		}
		
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			Map<String, String> param = new HashMap<>();
			param.put("saleid", userInfo.getUserid());
			param.put("type", type);
			//获取客户记录违规总次数
			int count = salerChatMapper.getViolatorCount(param);
			model.addAttribute("count", count);
		} catch (SQLExecutorException e) {
			model.addAttribute("count", "未知");
		}
		//分发页面
		if("1".equals(type.trim())) {
			//文字聊天管理页
			return ConstantIF.SALER_CHAT + "chat-list";
		}else if("2".equals(type.trim())) {
			//通话录音管理页
			return ConstantIF.SALER_CHAT + "phone-chat-list";
		}else {
			return ResultMsg.ADMIN_500;
		}
	}
	
	/**
	 * @Title: phoneChatList
	 * @Description: 客户通话记录管理列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月12日 上午11:50:29
	 */
	@LogOp(method = "phoneChatList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户通话记录管理列表")
	@RequestMapping(value = "phonechatlist", method = RequestMethod.POST)
	public @ResponseBody DataTable phoneChatList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			param.put("saleid", userInfo.getUserid());
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			//客户账号
			param.put("account", request.getParameter("account"));
			//客户昵称
			param.put("nickname", request.getParameter("nickname"));
			//拨打手机号
			param.put("phone", request.getParameter("phone"));
			//收藏
			param.put("collect", request.getParameter("collect"));
			//通话状态
			param.put("status", request.getParameter("status"));
			//使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			
			// 根据条件查询客户通话记录
			table = salerChatService.findSalerPhoneList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: phoneChatCollect
	 * @Description: 客户通话记录收藏、取消收藏
	 * @param tellid 通话记录id
	 * @param action 收藏动作（0不收藏， 1收藏）
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月12日 下午3:50:42
	 */
	@LogOp(method = "phoneChatCollect", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户通话记录收藏、取消收藏")
	@RequestMapping(value = "phonechatcollect", method = RequestMethod.POST)
	public @ResponseBody ResultBean phoneChatCollect(String tellid, String action) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("tellid", tellid);
			param.put("action", action);
			//更改收藏状态
			salerChatService.ChangePhoneChatCollect(param);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: violatorPage
	 * @Description: 客户通话违规记录
	 * @param tellid
	 * @param action
	 * @return
	 * @throws:
	 * @time: 2018年3月12日 下午4:47:57
	 */
	@LogOp(method = "violatorPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户通话违规记录")
	@RequestMapping(value = "violatorpage", method = RequestMethod.GET)
	public String violatorPage(String tellid, String chatid, Model model) {
		try {
			if(tellid != null) {
				//获取客户通话违规记录
				List<Reperecs> violator = salerChatMapper.findTellViolator(tellid);
				model.addAttribute("violator", violator);
			}
			
			if(chatid != null) {
				//获取业务员聊天违规记录
				List<Reperecs> violator = salerChatMapper.findChatViolator(chatid);
				model.addAttribute("violator", violator);
			}
			return ConstantIF.SALER_CHAT + "violator-list";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		
	}
	
	/**
	 * @Title: chatList
	 * @Description: 客户聊天记录管理列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月13日 下午12:58:11
	 */
	@LogOp(method = "chatList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户聊天记录管理列表")
	@RequestMapping(value = "chatlist", method = RequestMethod.POST)
	public @ResponseBody DataTable chatList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			param.put("saleid", userInfo.getUserid());
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			//客户账号
			param.put("account", request.getParameter("account"));
			//客户昵称
			param.put("nickname", request.getParameter("nickname"));
			//使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			
			// 根据条件查询客户通话记录
			table = salerChatService.findSalerChatList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: addChatInfoPage
	 * @Description: 添加客户聊天记录页
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月13日 下午4:48:38
	 */
	@LogOp(method = "addChatInfoPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加客户聊天记录页")
	@RequestMapping(value = "addchatinfopage", method = RequestMethod.GET)
	public String addChatInfoPage(HttpSession session, Model model) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		//获取业务员对应的用户账号和昵称
		try {
			List<BashMap> info = salerChatMapper.findSalerCustomerInfo(userInfo.getUserid());
			model.addAttribute("info", info);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.SALER_CHAT + "chat-add";
	}
	
	/**
	 * @Title: addChatInfo
	 * @Description: 添加客户聊天记录
	 * @param userid
	 * @param content
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午6:19:29
	 */
	@LogOp(method = "addChatInfo", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加客户聊天记录")
	@RequestMapping(value = "addchatinfo", method = RequestMethod.POST)
	public @ResponseBody ResultBean addChatInfo(String customerid, String content, HttpSession session) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			salerChatService.addChatInfo(userInfo.getUserid(), customerid, content);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
	/**
	 * @Title: customerChatPage
	 * @Description: 客户跟踪记录页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年5月2日 下午5:50:36
	 */
	@LogOp(method = "customerChatPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户跟踪记录页")
	@RequestMapping(value = "customerchatpage", method = { RequestMethod.GET })
	public String customerChatPage(String userid, Model model) {
		model.addAttribute("userid", userid);
		return ConstantIF.SALER_CUSTOMER + "customer-chat-list";
	}
	
	/**
	 * @Title: userChatRecordList
	 * @Description: 单客户跟踪记录列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年4月27日 下午1:46:59
	 */
	@LogOp(method = "userChatRecordList1", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "单客户跟踪记录列表")
	@RequestMapping(value = "customerchatlist", method = RequestMethod.POST)
	public @ResponseBody DataTable userChatRecordList1(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			//客户账号
			param.put("userid", request.getParameter("userid"));
			
			// 根据条件查询客户通话记录
			table = salerChatService.findSalerAndUserChatList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: getAllUserPhone
	 * @Description: 获取当前业务员下的客户手机号
	 * @param key
	 * @param page
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月13日 下午5:56:12
	 */
	/*@LogOp(method = "getAllUserPhone", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取当前业务员下的客户手机号")
	@RequestMapping(value = "findphones", method = RequestMethod.POST)
	public @ResponseBody Map getAllUserPhone(String key, int page, HttpSession session) {
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("length", 10);
			
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			Map<String, Object> param = new HashMap<>();
			param.put("salerid", userInfo.getUserid());
			param.put("key", key + "%");
			param.put("start", (page-1) * 10);
			param.put("length", 10);
			//获取条件下的总条数
			int count = salerChatMapper.findSalerCustomerCount(param);
			data.put("total", count);
			if(count <= 0) {
				//无数据
				data.put("items", new ArrayList());
				return data;
			}
			//获取条件下的数据
			
			List<Select2> list = salerChatMapper.getSalerCustomerPhone(param);
			data.put("items", list);
			return data;
		} catch (SQLExecutorException e) {
			return null;
		}
	}*/
}
