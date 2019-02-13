package dianfan.controller.agent;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.agent.AgentSalerChatMapper;
import dianfan.dao.mapper.agent.AgentPersonnelMapper;
import dianfan.dao.mapper.saler.SalerChatMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.Dept;
import dianfan.entities.Reperecs;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentSalerChatService;

/**
 * @ClassName AgentSalerChatManage
 * @Description 员工通讯管理
 * @author cjy
 * @date 2018年3月16日 下午12:03:12
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentSalerChatManage {
	@Autowired
	private AgentSalerChatService chatService;
	@Autowired
	private SalerChatMapper salerChatMapper;
	@Autowired
	private AgentSalerChatMapper chatMapper;
	@Autowired
	AgentPersonnelMapper agentPersonnelMapper;
	
	/**
	 * @Title: salerChatManage
	 * @Description: 业务员通讯管理页
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年3月16日 下午12:10:55
	 */
	@LogOp(method = "salerChatManage",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员通讯管理页")
	@RequestMapping(value = "salerchatmanage", method = RequestMethod.GET)
	public String salerChatManage(HttpServletRequest request, String type, Model model) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
		//获取处罚方式
		try {
			List<BashMap> method = chatMapper.getPunishMethod();
			model.addAttribute("method", method);
			// 获取代理商下的部门
			List<Dept> dept;
			if(6 == userInfo.getRole())
			{
				Map<String, String> param = new HashMap<>();
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
				dept = agentPersonnelMapper.findEnabledDeptByPosition(param);
			}
			else
			{
				dept = agentPersonnelMapper.findEnabledDept(userInfo.getUserid());
			}
			model.addAttribute("depts", dept);
		} catch (SQLExecutorException e) {
		}
		
		if("tell".equals(type)) {
			//员工通话记录页
			return ConstantIF.AGENT_CHAT + "phone-chat-list";
		}else {
			//员工聊天记录页
			return ConstantIF.AGENT_CHAT + "chat-list";
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
	@LogOp(method = "phoneChatList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户通话记录管理列表")
	@RequestMapping(value = "phonechatlist", method = RequestMethod.POST)
	public @ResponseBody DataTable phoneChatList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("userid", null);
				param.put("agentid", userInfo.getUserid());
			}

			param.put("searchdeptid", request.getParameter("searchdeptid"));
			param.put("searchsalerid", request.getParameter("searchsalerid"));
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			//业务员名称
			param.put("realname", request.getParameter("realname"));
			//客户手机号
			param.put("telno", request.getParameter("telno"));
			//客户昵称
			param.put("nickname", request.getParameter("nickname"));
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
	@LogOp(method = "salerChatList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "业务员聊天记录管理列表")
	@RequestMapping(value = "salerchatlist", method = RequestMethod.POST)
	public @ResponseBody DataTable salerChatList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				param.put("userid", userInfo.getUserid());
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("userid", null);
				param.put("agentid", userInfo.getUserid());
			}
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			//业务员名称
			param.put("realname", request.getParameter("realname"));
			//客户手机号
			param.put("telno", request.getParameter("telno"));
			//客户昵称
			param.put("nickname", request.getParameter("nickname"));
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
	
	/**
	 * @Title: violatorPage
	 * @Description: 客户违规记录
	 * @param tellid
	 * @param chatid
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月16日 下午3:58:35
	 */
	@LogOp(method = "violatorPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户违规记录")
	@RequestMapping(value = "violatorpage", method = RequestMethod.GET)
	public String violatorPage(String tellid, String chatid, Model model) {
		try {
			//获取处罚方式
			List<BashMap> method = chatMapper.getPunishMethod();
			model.addAttribute("method", method);
			
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
			return ConstantIF.AGENT_CHAT + "violator-list";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		
	}
	
	/**
	 * @Title: dealToViolator
	 * @Description: 处理聊天违规记录
	 * @param reperecs
	 * @return
	 * @throws:
	 * @time: 2018年3月20日 上午10:04:57
	 */
	@LogOp(method = "dealToViolator", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "处理聊天违规记录")
	@RequestMapping(value = "dealtoviolator", method = RequestMethod.POST)
	public @ResponseBody ResultBean dealToViolator(String id, String methodid) {
		try {
			chatService.dealToViolator(id, methodid);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: addViolatorInfo
	 * @Description: 添加通话违规记录
	 * @param tellid
	 * @param chatid
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月17日 上午9:57:22
	 */
	@LogOp(method = "addViolatorInfo", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加通话违规记录")
	@RequestMapping(value = "addviolatorinfo", method = RequestMethod.POST)
	public @ResponseBody ResultBean addViolatorInfo(Reperecs reperecs) {
		try {
			chatService.addViolatorInfo(reperecs);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
}
