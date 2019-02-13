package dianfan.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.FeedBackMapper;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.FeedBackService;

/**
 * @ClassName FeedBackManage
 * @Description 用户反馈
 * @author cjy
 * @date 2018年1月9日 下午2:19:59
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class FeedBackManage {

	@Autowired
	private FeedBackService feedBackService;
	@Autowired
	FeedBackMapper feedBackMapper;
	
	/**
	 * @Title: dashboardFeedback
	 * @Description: 反馈管理首页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午2:32:16
	 */
	@LogOp(method = "dashboardFeedback", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "反馈管理首页")
	@RequestMapping(value = "feedback")
	public String dashboardFeedback() {
		return ConstantIF.ADMIN_FEEDBACK + "feedback-list";
	}
	
	/**
	 * @Title: feedBackList
	 * @Description: 反馈列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午2:32:25
	 */
	@LogOp(method = "feedBackList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "反馈列表")
	@RequestMapping(value = "feedbacklist")
	public @ResponseBody DataTable feedBackList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			String search = request.getParameter(ConstantIF.DT_SEARCH).trim();
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 反馈列表
			table = feedBackService.findFeedBacks(search, start, length);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	
	/**
	 * @Title: delFeedbackInfo
	 * @Description: 批量删除用户反馈数据
	 * @param fbids
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午5:26:10
	 */
	@LogOp(method = "delNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量删除咨讯数据")
	@RequestMapping(value = "delfeedback")
	public @ResponseBody ResultBean delFeedbackInfo(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		List<String> lids = new ArrayList<>();
		
		for(String id : ids) {
			lids.add(id);
		}
		
		// 根据id批量删除用户反馈数据
		try {
			feedBackService.delFeedbackInfo(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: changeFeedbackStatus
	 * @Description: 更新反馈信息状态
	 * @param id
	 * @param status
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 上午10:22:47
	 */
	@LogOp(method = "changeFeedbackStatus", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "更新反馈信息状态")
	@RequestMapping(value = "changefbstatus")
	public @ResponseBody ResultBean changeFeedbackStatus(@RequestParam(value = "id") String id, @RequestParam(value = "status") String status) {
		if (id == null || "".equals(id)) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		try {
			// 根据id更新反馈信息状态
			feedBackService.changeFeedbackStatus(id, status);
			
			if("1".equals(status)) {
				//获取待处理的总数量
				Map<String, Object> param = new HashMap<>();
				param.put("type", "wait");
				int count = feedBackMapper.findFeedBacksCount(param);
				return new ResultBean(count);
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
}
