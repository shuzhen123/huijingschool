package dianfan.controller.agent;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.MemberMapper;
import dianfan.dao.mapper.agent.AgentCustomerMapper;
import dianfan.dao.mapper.agent.AgentPersonnelMapper;
import dianfan.dao.mapper.saler.SalerCustomerMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.Dept;
import dianfan.entities.ResultBean;
import dianfan.entities.Saler;
import dianfan.entities.UserAnswerList;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.agent.AgentCustomerService;

/**
 * @ClassName AgentCustomerManage
 * @Description 我的客户管理
 * @author cjy
 * @date 2018年2月2日 上午9:30:43
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentCustomerManage {
	
	@Autowired
	private SalerCustomerMapper salerCustomerMapper;
	@Autowired
	private AgentCustomerService agentCustomerService;
	@Autowired
	private AgentCustomerMapper agentCustomerMapper;
	@Autowired
	private AgentPersonnelMapper agentPersonnelMapper;
	
	/* ***************************客户资源池*****************************/
	
	/**
	 * @Title: customerPool
	 * @Description: 客户资源池页
	 * @return
	 * @throws:
	 * @time: 2018年2月8日 下午2:30:52
	 */
	@LogOp(method = "customerPool", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户资源池页")
	@RequestMapping(value = "customerpool", method = { RequestMethod.GET })
	public String customerPool() {
		return ConstantIF.AGENT_CUSTOMER + "customer-pool";
	}

	/**
	 * @Title: customerPoolList
	 * @Description: 客户资源池列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月26日 上午11:28:05
	 */
	@LogOp(method = "customerPoolList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户资源池列表")
	@RequestMapping(value = "customerpoollist")
	public @ResponseBody DataTable customerPoolList(HttpServletRequest request) {
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
			// 手机号
			param.put("telno", request.getParameter("telno"));
			// 姓名
			param.put("realname", request.getParameter("realname"));
			// 性别
			param.put("sex", request.getParameter("sex"));
			
			// 根据条件查询用户列表
			table = agentCustomerService.findUnbindCustomer(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理

		}
		return table;
	}
	
	/**
	 * @Title: telnoBatchImportPage
	 * @Description: 批量导入手机号页
	 * @return
	 * @throws:
	 * @time: 2018年5月2日 上午10:08:02
	 */
	@LogOp(method = "telnoBatchImportPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量导入手机号页")
	@RequestMapping(value = "telnobatchimportpage", method = { RequestMethod.GET })
	public String telnoBatchImportPage() {
		return ConstantIF.AGENT_CUSTOMER + "telno-batch-import";
	}
	
	/**
	 * @Title: telnoBatchImport
	 * @Description: 批量导入手机号
	 * @param restype 用户资源类型（x后台导入，y智能机器人导入，z媒体推广）
	 * @param temptel
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws:
	 * @time: 2018年5月2日 上午11:27:41
	 */
	@LogOp(method = "telnoBatchImport", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量导入手机号")
	@RequestMapping(value = "telnobatchimport", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean telnoBatchImport(String restype, String temptel, HttpSession session) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<List<String>> data = mapper.readValue(temptel, ArrayList.class);
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			String agentid;
			if(userInfo == null) agentid = ConstantIF.AGENT_ID;
			else{
				if(6 == userInfo.getRole())
				{
					agentid = userInfo.getAgentid();
				}
				else
				{
					agentid = userInfo.getUserid();
				}
			}
			Map<String, Object> ret = agentCustomerService.telnoBatchImport(data, agentid, restype);
			return new ResultBean(ret);
		} catch (SQLExecutorException | IOException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
	
	
	/**
	 * @Title: changeUserRestype
	 * @Description: 更改用户资源类型
	 * @param restype
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年5月14日 上午10:55:18
	 */
	@LogOp(method = "changeUserRestype", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "更改用户资源类型")
	@RequestMapping(value = "changeuserrestype", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean changeUserRestype(String restype, String userid) {
		try {
			agentCustomerService.changeUserRestype(userid, restype);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/* ***************************客户管理*****************************/

	/**
	 * @Title: dashboardCourseUser
	 * @Description: 客户管理页
	 * @return
	 * @throws:
	 * @time: 2018年2月2日 上午9:31:55
	 */
	@LogOp(method = "dashboardCourseUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户管理首页")
	@RequestMapping(value = "courseuser", method = { RequestMethod.GET })
	public String dashboardCourseUser(HttpServletRequest request, Model model) {
		try {
			//获取用户等级列表
			List<BashMap> levels = salerCustomerMapper.findUserLevels();
			model.addAttribute("levels", levels);
			
			//获取代理商下的部门
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
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
			//获取无部门的员工
			Map<String, String> param = new HashMap<>();
			if(6 == userInfo.getRole())
			{
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("agentid", userInfo.getUserid());
			}
			param.put("deptid", null);
			List<Saler> salers = agentCustomerMapper.findSalerByDeptid(param);
			model.addAttribute("salers", salers);
			
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_CUSTOMER + "customer-list";
	}

	/**
	 * @Title: agentCustomerList
	 * @Description: 代理商下用户列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月6日 下午4:32:47
	 */
	@LogOp(method = "agentUserList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商维护用户分流列表")
	@RequestMapping(value = "agentcustomerlist", method = { RequestMethod.POST })
	public @ResponseBody DataTable agentCustomerList(HttpServletRequest request) {
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
			/*--添加：搜索条件--*/
			//手机号
			param.put("telno", request.getParameter("telno"));
			//姓名
			param.put("realname", request.getParameter("realname"));
			//用户等级（用户分类）
			param.put("levelid", request.getParameter("levelid"));
			// 根据条件查询用户列表
			table = agentCustomerService.findCustomerUser(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理

		}
		return table;
	}

	/**
	 * @Title: agentSaler
	 * @Description: 根据代理商部门id获取业务员列表
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年2月8日 下午4:22:21
	 */
	@LogOp(method = "agentSaler", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商下的业务员列表")
	@RequestMapping(value = "agentdeptidsaler", method = RequestMethod.GET)
	public @ResponseBody ResultBean agentSaler(@RequestParam(value = "deptid") String deptid, HttpSession session) {
		try {
			// 获取无部门的员工
			Map<String, String> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				param.put("agentid", userInfo.getAgentid());
			}
			else
			{
				param.put("agentid", userInfo.getUserid());
			}
			param.put("deptid", deptid);
			List<Saler> salers = agentCustomerMapper.findSalerByDeptid(param);
			return new ResultBean(salers);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: moveCustomer
	 * @Description: 批量转移用户资源
	 * @param session
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月8日 下午4:41:29
	 */
	@LogOp(method = "moveCustomer", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量转移用户资源")
	@RequestMapping(value = "movecustomer", method = RequestMethod.POST)
	public @ResponseBody ResultBean moveCustomer(HttpSession session, @RequestParam(value = "ids[]") String[] ids,
			@RequestParam(value = "salerid") String salerid) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		List<String> lids = new ArrayList<>();
		for (String id : ids) {
			lids.add(id);
		}
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				agentCustomerService.moveCustomer(lids, salerid, userInfo.getAgentid());
			}
			else
			{
				agentCustomerService.moveCustomer(lids, salerid, userInfo.getUserid());
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

}
