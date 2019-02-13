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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.WriterException;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.exception.VCloudException;
import dianfan.service.admin.MemberService;
import dianfan.service.admin.TurnplateUserService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName TurnplateUserManage
 * @Description 大转盘提交用户
 * @author cjy
 * @date 2018年5月3日 上午10:55:41
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class TurnplateUserManage {

	@Autowired
	private TurnplateUserService memberService;

	/**
	 * @Title: dashboardTurnplateUser
	 * @Description: 大转盘提交用户列表页
	 * @return
	 * @throws:
	 * @time: 2018年5月3日 上午10:58:48
	 */
	@LogOp(method = "dashboardTurnplateUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "大转盘提交用户列表页")
	@RequestMapping(value = "turnplateuserpage", method = RequestMethod.GET)
	public String dashboardTurnplateUser() {
		return ConstantIF.ADMIN_MEMBER + "turnplate-user-list";
	}

	/**
	 * @Title: turnplateUserList
	 * @Description: 大转盘提交用户列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年5月3日 上午11:00:43
	 */
	@LogOp(method = "turnplateUserList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "大转盘提交用户列表")
	@RequestMapping(value = "turnplateuserlist", method = RequestMethod.POST)
	public @ResponseBody DataTable turnplateUserList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 根据条件查询用户列表
			table = memberService.findTurnplateUsers(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

}
