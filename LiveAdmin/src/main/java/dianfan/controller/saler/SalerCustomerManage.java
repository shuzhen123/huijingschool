package dianfan.controller.saler;

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
import org.springframework.web.multipart.MultipartFile;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.saler.SalerCustomerMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.SalerLevel;
import dianfan.entities.TrackRecord;
import dianfan.entities.UserAnswerList;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.saler.SalerCustomerService;
import dianfan.util.FileUploadUtils;
import dianfan.util.RegexUtils;

/**
 * @ClassName SalerCustomerManage
 * @Description 业务员客户管理
 * @author cjy
 * @date 2018年3月6日 下午1:21:56
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/saler")
public class SalerCustomerManage {
	@Autowired
	private SalerCustomerMapper salerCustomerMapper;
	@Autowired
	private SalerCustomerService salerCustomerService;
	
	@Autowired
	private RedisService<?,?> redisService;
	

	/* *******************************客户资源池 **************************************/

	/**
	 * @Title: customerPool
	 * @Description: 客户资源池页
	 * @return
	 * @throws:
	 * @time: 2018年2月8日 下午2:30:52
	 */
	@LogOp(method = "customerPool", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户资源池页")
	@RequestMapping(value = "customerpool", method = { RequestMethod.GET })
	public String customerPool(HttpSession session, Model model) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		try {
			// 获取业务员对应的等级xyz资源数量
			SalerLevel count = salerCustomerMapper.getSalerCollectCount(userInfo.getUserid());
			model.addAttribute("resource", count);
			// 获取今日已拾取数量
			List<Map<String, Integer>> countToday = salerCustomerMapper.getCollectCountToday(userInfo.getUserid());
			Map<String, Object> counts = new HashMap<>();
			for (Map<String, Integer> map : countToday) {
				if ("x".equals(map.get("restype"))) {
					counts.put("x", map.get("count"));
					continue;
				}
				if ("y".equals(map.get("restype"))) {
					counts.put("y", map.get("count"));
					continue;
				}
				if ("z".equals(map.get("restype"))) {
					counts.put("z", map.get("count"));
					continue;
				}
			}
			model.addAttribute("counts", counts);
			return ConstantIF.SALER_CUSTOMER + "customer-pool";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
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
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 根据条件查询用户列表
			table = salerCustomerService.findUnbindCustomer(userInfo.getUserid(), start, length);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理

		}
		return table;
	}

	/**
	 * @Title: collectCustomer
	 * @Description: 批量拾取用户资源
	 * @param session
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月8日 下午3:17:30
	 */
	@LogOp(method = "collectCustomer", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量拾取用户资源")
	@RequestMapping(value = "collectcustomer", method = RequestMethod.POST)
	public @ResponseBody ResultBean collectCustomer(HttpSession session, @RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		try {
			List<String> lids = new ArrayList<>();
			for (String id : ids) {
				lids.add(id);
			}
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			salerCustomerService.collectCustomer(lids, userInfo.getUserid());
			return new ResultBean();
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/* *******************************客户管理 **************************************/

	/**
	 * @Title: dashboardCourseUser
	 * @Description: 客户管理页
	 * @return
	 * @throws:
	 * @time: 2018年2月2日 上午9:31:55
	 */
	@LogOp(method = "dashboardCourseUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户管理首页")
	@RequestMapping(value = "customer", method = { RequestMethod.GET })
	public String dashboardCustomer(HttpServletRequest request, Model model) {
		// 获取用户等级列表
		try {
			List<BashMap> levels = salerCustomerMapper.findUserLevels();
			model.addAttribute("levels", levels);
			return ConstantIF.SALER_CUSTOMER + "customer-list";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: customerList
	 * @Description: 客户列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月6日 下午2:05:19
	 */
	@LogOp(method = "customerList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户列表")
	@RequestMapping(value = "customerlist", method = { RequestMethod.POST })
	public @ResponseBody DataTable customerList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			Map<String, Object> param = new HashMap<>();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
			param.put("salerid", userInfo.getUserid());
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			/*--添加：搜索条件--*/
			// 手机号
			param.put("telno", request.getParameter("telno"));
			// 姓名
			param.put("realname", request.getParameter("realname"));
			// 用户等级（用户分类）
			param.put("levelid", request.getParameter("levelid"));

			// 根据条件查询用户列表
			table = salerCustomerService.findCustomerUser(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}

	/**
	 * @Title: userDataPage
	 * @Description: 用户信息页
	 * @param userid
	 * @param type
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年4月20日 上午9:34:52
	 */
	@LogOp(method = "userDataPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "用户信息页")
	@RequestMapping(value = "userdatapage", method = RequestMethod.GET)
	public String userDataPage(HttpSession session, String userid, String type, Model model) {
		try {
			// 获取用户信息
			UserInfo info = salerCustomerMapper.findUserInfoById(userid);
			info.setIconurl(ConstantIF.PROJECT + info.getIconurl());
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			if(6 == userInfo.getRole())
			{
				if(info.getTelno() != null && !info.getTelno().isEmpty())
				{
					StringBuilder sb = new StringBuilder(info.getTelno());
					sb.replace(3, 7, "****");
					info.setTelno(sb.toString());
				}
			}
			model.addAttribute("userInfo", info);

			if ("edit".equals(type)) {
				// 获取用户等级列表
				List<BashMap> levels = salerCustomerMapper.findUserLevels();
				model.addAttribute("levels", levels);
				return ConstantIF.SALER_CUSTOMER + "customer-edit";
			} else {
				return ConstantIF.SALER_CUSTOMER + "customer-detail";
			}
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}

	}

	/**
	 * @Title: editUser
	 * @Description: 用户信息修改
	 * @return
	 * @throws:
	 * @time: 2018年3月6日 下午3:16:32
	 */
	@LogOp(method = "editUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "提交修改的用户数据")
	@RequestMapping(value = "edituser", method = RequestMethod.POST)
	public @ResponseBody ResultBean editUser(MultipartFile avator, UserInfo info) {
		if (avator != null && !avator.isEmpty()) {
			try {
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				// 上传头像
				String newfilename = FileUploadUtils.uploadOne(avator, realPath);
				info.setIconurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 展示图上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		}
		try {
			salerCustomerService.updateUserInfo(info);
			return new ResultBean();
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: userAddPage
	 * @Description: 用户添加页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年5月11日 下午3:03:12
	 */
	@LogOp(method = "userAddPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "用户添加页")
	@RequestMapping(value = "useraddpage", method = RequestMethod.GET)
	public String userAddPage(Model model) {
		try {
			// 获取用户等级列表
			List<BashMap> levels = salerCustomerMapper.findUserLevels();
			model.addAttribute("levels", levels);
			return ConstantIF.SALER_CUSTOMER + "customer-add";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}

	/**
	 * @Title: addUser
	 * @Description: 新增用户
	 * @param avator
	 * @param info
	 * @return
	 * @throws:
	 * @time: 2018年5月11日 下午3:14:56
	 */
	@LogOp(method = "addUser", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "新增用户")
	@RequestMapping(value = "adduser", method = RequestMethod.POST)
	public @ResponseBody ResultBean addUser(MultipartFile avator, UserInfo info, HttpSession session) {
		if (info.getTelno() == null || !RegexUtils.phoneRegex(info.getTelno())) {
			return new ResultBean("001", ResultMsg.C_001);
		}
		// 检测手机号码是否冲突
		try {
			boolean ck = salerCustomerMapper.checkRegPhone(info.getTelno());
			if (ck) {
				return new ResultBean("002", ResultMsg.C_002);
			}
		} catch (SQLExecutorException e1) {
			return new ResultBean("500", ResultMsg.C_500);
		}

		if (avator != null && !avator.isEmpty()) {
			try {
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				// 上传头像
				String newfilename = FileUploadUtils.uploadOne(avator, realPath);
				info.setIconurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 展示图上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		} else {
			info.setIconurl(ConstantIF.DEFAULT_ICON);
		}
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			info.setCreateid(userInfo.getUserid());
			salerCustomerService.addUserInfo(info);
			return new ResultBean();
		} catch (Exception e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: userBatchDel
	 * @Description: 批量丢弃用户
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年5月14日 上午9:40:30
	 */
	@LogOp(method = "userBatchDel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量批量丢弃")
	@RequestMapping(value = "userbatchdel", method = RequestMethod.POST)
	public @ResponseBody ResultBean userBatchDel(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}

		List<String> lids = new ArrayList<>();

		for (String id : ids) {
			lids.add(id);
		}

		try {
			// 根据id批量丢弃用户
			salerCustomerService.userBatchDel(lids);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
	
	/**
	 * @Title: userEvaluationResultPage
	 * @Description: 用户测评结果页面
	 * @date 2018年5月15日 下午6:34:48
	 */
	@LogOp(method = "userEvaluationResultPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "用户测评结果页面")
	@RequestMapping(value = "userEvaluationResultPage")
	public String userEvaluationResultPage (Model model,String userid) {
		try {
			//算出题目的总分
//			int allScore = memberMapper.fildAllScore(userid);
			model.addAttribute("allScore", "500");
			//分数线
			String scoreline = redisService.get(ConstantIF.EVALUATE_SCORE);
			if(scoreline == null || scoreline.isEmpty()) {
				scoreline = "0";
			}
			model.addAttribute("scoreline", scoreline);
			
			//算出用户的总分
			int userScore = salerCustomerMapper.fildAllScore(userid);
			model.addAttribute("userScore", userScore);
			
			//查询用户评测答案列表
			List<UserAnswerList> answers = salerCustomerMapper.fildUserResultById(userid);
			model.addAttribute("answers", answers);
			
			//跳转至用户测评结果页面
			return ConstantIF.SALER_CUSTOMER + "member-evaluation-result";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}
	
	/**
	 * @Title: addTrackRecordPage
	 * @Description: 添加客户跟踪记录页面
	 * @param userid 
	 * 			用户的id
	 * @param model
	 * @return
	 * @author: sz
	 * @date 2018年5月16日 下午4:18:24
	 */
	@LogOp(method = "addTrackRecordPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加客户跟踪记录页面")
	@RequestMapping(value = "addTrackRecordPage")
	public String addTrackRecordPage (String userid, Model model) {
		//获取用户的userid
		model.addAttribute("userid", userid);
		return ConstantIF.SALER_CUSTOMER+"add-track-record";
	}
	
	/**
	 * @Title: addTrackRecord
	 * @Description: 添加客户跟踪记录
	 * @param userid
	 * 			用户的id
	 * @param record
	 * 			跟踪记录内容
	 * @param request
	 * 			request
	 * @param session
	 * 			session（用来获取业务员id）
	 * @return ResultBean
	 * @author: sz
	 * @date 2018年5月16日 下午4:35:42
	 */
	@LogOp(method = "addTrackRecord", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加客户跟踪记录")
	@RequestMapping(value = "addTrackRecord")
	public @ResponseBody ResultBean addTrackRecord (TrackRecord record, HttpServletRequest request) {
		//1.获取业务员的id
		UserInfo saler =  (UserInfo) request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
		//2.验证跟踪记录内容
		if (record.getRecord() == null || record.getRecord().isEmpty()) {
			//跟踪记录内容不能为空
			return new ResultBean("027",ResultMsg.C_027);
		}
		//3.添加业务员id
		record.setSalerid(saler.getUserid());
		try {
			//5.添加咨询数据
			salerCustomerService.addTrackRecordInfo(record);
		} catch (SQLExecutorException e) {
			//添加咨询数据失败
			return new ResultBean("500",ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	
	/**
	 * @Title: customerchatPage
	 * @Description: 客户跟踪显示页面
	 * @param userid
	 * @param model
	 * @return
	 * @author: sz
	 * @date 2018年5月16日 下午5:48:18
	 */
	@LogOp(method = "customerchatPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户跟踪显示页面")
	@RequestMapping(value = "customerchatPage")
	public String customerchatPage (String userid, Model model) {
		//添加用户的uesrid
		model.addAttribute("userid", userid);
		return ConstantIF.SALER_CUSTOMER+"track-record-list";
	}
	
	/**
	 * @Title: customerchatList
	 * @Description: 客户跟踪显示列表
	 * @param userid
	 * 			用户的id
	 * @param session
	 * @return ResultBean
	 * @author: sz
	 * @date 2018年5月16日 下午6:01:52
	 */
	@LogOp(method = "customerchatList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "客户跟踪显示列表")
	@RequestMapping(value = "customerchatList")
	public @ResponseBody DataTable customerchatList (String userid, HttpSession session) {
		DataTable result = new DataTable();
		try {
			//查询客户跟踪显示列表
			result = salerCustomerService.fildTrackRecordlist(userid);
		} catch (SQLExecutorException e) {
			//
		}
		return result;
	}
	
	
	
}
