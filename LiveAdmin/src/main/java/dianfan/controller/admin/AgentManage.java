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

import com.google.zxing.WriterException;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.AgentMapper;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.AgentService;

/**
 * @ClassName AgentManage
 * @Description 代理商管理
 * @author cjy
 * @date 2018年1月17日 下午3:26:51
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class AgentManage {
	@Autowired
	private AgentService agentService;
	@Autowired
	private AgentMapper agentMapper;
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: dashboardAgent
	 * @Description: 代理商管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月17日 下午3:26:24
	 */
	@LogOp(method = "dashboardAgent", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商管理页")
	@RequestMapping(value = "agent", method = RequestMethod.GET)
	public String dashboardAgent() {
		return ConstantIF.ADMIN_AGENT + "agent-list";
	}

	/**
	 * @Title: agentList
	 * @Description: 获取代理商列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月15日 下午3:00:17
	 */
	@LogOp(method = "agentList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取代理商列表")
	@RequestMapping(value = "agentlist", method = RequestMethod.POST)
	public @ResponseBody DataTable agentList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));

			Map<String, Object> param = new HashMap<>();
			param.put("start", start);
			param.put("length", length);
			// 账号
			param.put("username", request.getParameter("username"));
			// 用户名
			param.put("realname", request.getParameter("realname"));
			// 手机号码
			param.put("phone", request.getParameter("phone"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			// 根据条件查询代理商列表
			table = agentService.findAgentUsers(param);
			
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: addAgentPage
	 * @Description: 代理商添加页
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月18日 下午3:58:22
	 */
	@LogOp(method = "addAgentPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商添加页")
	@RequestMapping(value = "addagentpage", method = RequestMethod.GET)
	public String addAgentPage(Model model) {
		// 生成代理商用户名，且不能更改
		String username = redisService.incr(ConstantIF.USERNAME_KEY);
		model.addAttribute("username", username);
		return ConstantIF.ADMIN_AGENT + "agent-add";
	}

	/**
	 * @Title: addAgent
	 * @Description: 添加代理商数据
	 * @param user
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月15日 下午4:31:05
	 */
	@LogOp(method = "addAgent", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加代理商数据")
	@RequestMapping(value = "addagent", method = RequestMethod.POST)
	public @ResponseBody ResultBean addAgent(UserInfo user, HttpSession session) {
		try {
			// 添加创建者id
			UserInfo sess = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			user.setCreateid(sess.getUserid());
			// 添加新代理商
			agentService.addAgentInfo(user);
			return new ResultBean();
		} catch (SQLExecutorException | NumberFormatException | WriterException | IOException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: delAgent
	 * @Description: 删除代理商
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年3月15日 下午4:42:21
	 */
	@LogOp(method = "delAgent", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除代理商")
	@RequestMapping(value = "delagent", method = RequestMethod.POST)
	public @ResponseBody ResultBean delAgent(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}

		List<String> lids = new ArrayList<>();

		for (String id : ids) {
			lids.add(id);
		}

		// 根据用户id删除代理商
		try {
			agentService.delAgent(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: userDetailPage
	 * @Description: 代理商数据页(转向详情页（edit），还是编辑页（detail）)
	 * @param request
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月5日 下午1:25:37
	 */
	@LogOp(method = "agentData", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商数据页")
	@RequestMapping(value = "agentdata", method = RequestMethod.GET)
	public String agentData(String agentid, String type, Model model) {
		try {
			// 代理商详情
			UserInfo userInfo = agentMapper.getAgentData(agentid);
			model.addAttribute("userInfo", userInfo);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}

		if (type != null && "edit".equals(type.trim())) {
			return ConstantIF.ADMIN_AGENT + "agent-edit";
		} else {
			return ConstantIF.ADMIN_AGENT + "agent-detail";
		}

	}

	/**
	 * @Title: editAgent
	 * @Description: 代理商数据修改
	 * @param user
	 * @return
	 * @throws:
	 * @time: 2018年3月15日 下午5:52:43
	 */
	@LogOp(method = "editAgent", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商数据修改")
	@RequestMapping(value = "editagent", method = RequestMethod.POST)
	public @ResponseBody ResultBean editAgent(UserInfo user) {
		try {
			agentService.updateAgentInfo(user);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: agentTeaPage
	 * @Description: 代理商下教师列表页
	 * @param agentid
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月28日 下午3:50:11
	 */
	@LogOp(method = "agentTeaPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商下教师列表页")
	@RequestMapping(value = "agentteapage", method = RequestMethod.GET)
	public String agentTeaPage(String agentid, Model model) {
		try {
			// 获取代理商的讲师列表
			List<UserInfo> teaList = agentMapper.findAgentTeaList(agentid);
			model.addAttribute("teaList", teaList);
			return ConstantIF.ADMIN_AGENT + "agent-teacher-list";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: agentTeacherCommend
	 * @Description: 代理商教师推荐修改
	 * @param teacherid
	 *            讲师id
	 * @param type
	 *            推荐修改类型（trecflag 名师推荐, untrecflag 名师不推荐, famous 直播推荐, unfamous
	 *            直播不推荐）
	 * @return
	 * @throws:
	 * @time: 2018年3月28日 下午4:27:44
	 */
	@LogOp(method = "agentTeacherCommend", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "代理商教师推荐修改")
	@RequestMapping(value = "changeteacommend", method = RequestMethod.POST)
	public @ResponseBody ResultBean agentTeacherCommend(String teacherid, String type) {
		try {
			agentService.agentTeacherCommend(teacherid, type);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
}
