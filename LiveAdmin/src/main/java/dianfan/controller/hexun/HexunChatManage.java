package dianfan.controller.hexun;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import dianfan.entities.DataTable;
import dianfan.exception.SQLExecutorException;
import dianfan.service.hexun.HexunChatService;

/**
 * @ClassName HexunChatManage
 * @Description 记录管理
 * @author cjy
 * @date 2018年3月20日 下午12:49:11
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/hx")
public class HexunChatManage {
	@Autowired
	private HexunChatService chatService;
	
	/**
	 * @Title: chatManage
	 * @Description: 业务员通讯管理页
	 * @param type
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 下午12:51:18
	 */
	@LogOp(method = "chatManage",  logtype = ConstantIF.LOG_TYPE_3, userid = "", description = "业务员通讯管理页")
	@RequestMapping(value = "chatmanage", method = RequestMethod.GET)
	public String chatManage(String type, Model model) {
		if("tell".equals(type)) {
			//员工通话记录页
			return ConstantIF.HX_CHAT + "phone-chat-list";
		}else {
			//员工聊天记录页
			return ConstantIF.HX_CHAT + "chat-list";
		}
	}
	
	/**
	 * @Title: phoneChatList
	 * @Description: 客户通话记录管理列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月16日 下午12:11:28
	 */
	@LogOp(method = "phoneChatList", logtype = ConstantIF.LOG_TYPE_3, userid = "", description = "客户通话记录管理列表")
	@RequestMapping(value = "phonechatlist", method = RequestMethod.POST)
	public @ResponseBody DataTable phoneChatList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			//通话状态
			param.put("status", request.getParameter("status"));
			//使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			
			// 根据条件查询客户通话记录
			table = chatService.findSalerPhoneList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: salerChatList
	 * @Description: 业务员聊天记录管理列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月17日 上午11:01:27
	 */
	@LogOp(method = "salerChatList", logtype = ConstantIF.LOG_TYPE_3, userid = "", description = "业务员聊天记录管理列表")
	@RequestMapping(value = "salerchatlist", method = RequestMethod.POST)
	public @ResponseBody DataTable salerChatList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			//业务员名称
			param.put("realname", request.getParameter("realname"));
			//使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));
			
			// 根据条件查询业务员聊天记录
			table = chatService.findSalerChatList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
}
