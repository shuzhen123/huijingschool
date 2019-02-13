package dianfan.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.FeedBackMapper;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AdminIndex
 * @Description 管理员首页
 * @author cjy
 * @date 2018年1月2日 下午5:25:23
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class AdminIndex {
	@Autowired
	private FeedBackMapper feedBackMapper;
	
	/**
	 * @Title: dashboard
	 * @Description: 后台首页
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午5:52:50
	 */
	@LogOp(method = "dashboard",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "后台首页")
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String dashboard(HttpSession session, Model model) {
		try {
			//获取待处理反馈数量
			Map<String, Object> param = new HashMap<>();
			//分类搜索条件
			param.put("type", "wait");
			int count = feedBackMapper.findFeedBacksCount(param);
			model.addAttribute("feedback", count);
		} catch (SQLExecutorException e) {
			return ConstantIF.ERROR_500;
		}
		return ConstantIF.ADMIN_PATH + "index";
	}
	
	/**
	 * @Title: welcome
	 * @Description: 我的桌面
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午5:53:01
	 */
	@LogOp(method = "desktop",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "我的桌面")
	@RequestMapping(value = "welcome", method = RequestMethod.GET)
	public String desktop() {
		return ConstantIF.ADMIN_PATH + "welcome";
	}
	
}
