package dianfan.controller.hexun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.service.agent.AgentIndexService;

/**
 * @ClassName HexunIndex
 * @Description 和讯人员登录首页
 * @author cjy
 * @date 2018年3月20日 上午11:49:14
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/hx")
public class HexunIndex {

	@Autowired
	private AgentIndexService agentIndexService;
	
	/**
	 * @Title: dashboard
	 * @Description: 后台首页
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午5:52:50
	 */
	@LogOp(method = "dashboard",  logtype = ConstantIF.LOG_TYPE_3, userid = "", description = "后台首页")
	@RequestMapping(value = "index", method = { RequestMethod.GET })
	public String dashboard() {
		return ConstantIF.HX_PATH + "index";
	}
	
	/**
	 * @Title: welcome
	 * @Description: 我的桌面
	 * @return
	 * @throws:
	 * @time: 2018年1月2日 下午5:53:01
	 */
	@LogOp(method = "desktop",  logtype = ConstantIF.LOG_TYPE_3, userid = "", description = "我的桌面")
	@RequestMapping(value = "welcome", method = { RequestMethod.GET })
	public String desktop() {
		return ConstantIF.HX_PATH + "welcome";
	}
	
}
