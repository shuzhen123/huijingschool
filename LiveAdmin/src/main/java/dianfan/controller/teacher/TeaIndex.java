package dianfan.controller.teacher;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.AdminUserMapper;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.AdminUserService;
import dianfan.service.agent.AgentTeacherService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName TeacherIndex
 * @Description 老师首页
 * @author cjy
 * @date 2018年3月10日 下午4:38:14
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/tea")
public class TeaIndex {
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AgentTeacherService teacherService;
	@Autowired
	private AdminUserMapper adminUserMapper;

	/**
	 * @Title: dashboard
	 * @Description: 后台首页
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午5:52:50
	 */
	@LogOp(method = "dashboard", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "后台首页")
	@RequestMapping(value = "index", method = { RequestMethod.GET })
	public String dashboard() {
		return ConstantIF.TEA_PATH + "index";
	}

	/**
	 * @Title: welcome
	 * @Description: 我的桌面
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午5:53:01
	 */
	@LogOp(method = "desktop", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "我的桌面")
	@RequestMapping(value = "welcome", method = { RequestMethod.GET })
	public String desktop() {
		return ConstantIF.TEA_PATH + "welcome";
	}

	/**
	 * @Title: dashboardAdmin
	 * @Description: 管理员个人信息页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午2:46:06
	 */
	@LogOp(method = "dashboardAdmin", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "管理员信息页")
	@RequestMapping(value = "mydata")
	public String dashboardAdmin(HttpSession session, Model model) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		userInfo.setIconurl(ConstantIF.PROJECT + userInfo.getIconurl());
		userInfo.setTeacherurl(ConstantIF.PROJECT + userInfo.getTeacherurl());
		model.addAttribute("userinfo", userInfo);
		return ConstantIF.TEA_PATH + "self-info";
	}

	/**
	 * @Title: editSelfInfo
	 * @Description: 个人信息修改
	 * @param avator
	 * @param pic
	 * @param info
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月22日 下午5:21:56
	 */
	@LogOp(method = "editSelfInfo", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "个人信息修改")
	@RequestMapping(value = "editselfinfo", method = RequestMethod.POST)
	public @ResponseBody ResultBean editSelfInfo(MultipartFile avator, MultipartFile pic, UserInfo info,
			HttpSession session) {
		// 绝对上传路径
		String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
		if (avator != null && !avator.isEmpty()) {
			try {
				// 上传头像
				String newfilename = FileUploadUtils.uploadOne(avator, realPath);
				info.setIconurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 头像上传失败
			}
		}
		if (pic != null && !pic.isEmpty()) {
			try {
				// 上传教师展示图
				String newfilename = FileUploadUtils.uploadOne(pic, realPath);
				info.setTeacherurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 展示图上传失败
			}
		}

		try {
			// 更新讲师数据
			teacherService.editTeacherInfo(info);
			// 更新session
			UserInfo sess = adminUserMapper.getUserDataById(info.getUserid());
			session.setAttribute(ConstantIF.PC_SESSION_KEY, sess);
			return new ResultBean();
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: editAdminUserPwd
	 * @Description: 密码修改
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月22日 下午5:59:55
	 */
	@LogOp(method = "changePwd", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "密码修改")
	@RequestMapping(value = "changepwd")
	public @ResponseBody ResultBean changePwd(HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);

		String oldpwd = request.getParameter("oldpwd");
		// 检测旧密码正确性
		if (!userInfo.getPassword().equals(oldpwd)) {
			return new ResultBean("017", ResultMsg.C_017);
		}

		String newpwd = request.getParameter("newpwd");
		String password = request.getParameter("password");
		// 检测新密码是否一致
		if (!newpwd.equals(password)) {
			return new ResultBean("018", ResultMsg.C_018);
		}
		// 检测新旧密码是否相同
		if (oldpwd.equals(password)) {
			return new ResultBean("019", ResultMsg.C_019);
		}

		userInfo.setPassword(password);
		try {
			adminUserService.updateAdminUserPwd(userInfo);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
