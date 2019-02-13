package dianfan.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.entities.ResultBean;
import dianfan.service.RedisService;

/**
 * @ClassName TaskManage
 * @Description 业务管理
 * @author cjy
 * @date 2018年2月23日 下午2:15:40
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class BusiManage {
	@Autowired
	private RedisService redisService;
	
	/**
	 * @Title: dashboardProfit
	 * @Description: 分润设置页
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午4:35:03
	 */
	@LogOp(method = "dashboardProfit", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "分润设置页")
	@RequestMapping(value = "dashboardprofit", method = {RequestMethod.GET})
	public String dashboardProfit(Model model) {
		//获取默认代理商课程分润
    	String agent = redisService.get(ConstantIF.DEF_AGENT_PROFIT_KEY);
    	if(agent == null || agent.trim().isEmpty()) {
    		agent = "0";
    	}else {
    		int ag = (int) (Float.parseFloat(agent) * 100);
    		agent = ag + "";
    	}
    	//获取默认业务员课程分润
    	String saler = redisService.get(ConstantIF.DEF_SALER_PROFIT_KEY);
    	if(saler == null || saler.trim().isEmpty()) {
    		saler = "0";
    	}else {
    		int ag = (int) (Float.parseFloat(saler) * 100);
    		saler = ag + "";
    	}
    	model.addAttribute("agent", agent);
    	model.addAttribute("saler", saler);
		return ConstantIF.ADMIN_STATISTICS + "profit-manage";
	}
	
	/**
	 * @Title: addSalerLevel
	 * @Description: 分润设置
	 * @param salerLevel
	 * @return
	 * @throws:
	 * @time: 2018年2月24日 下午4:34:00
	 */
	@LogOp(method = "addSalerLevel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "分润设置")
	@RequestMapping(value = "setvipprofit", method = {RequestMethod.POST})
	public @ResponseBody ResultBean setVipProfit(@RequestParam(value="agent") float agent, @RequestParam(value="saler") float saler) {
		redisService.set(ConstantIF.DEF_AGENT_PROFIT_KEY, agent/100 + "");
		redisService.set(ConstantIF.DEF_SALER_PROFIT_KEY, saler/100 + "");
		return new ResultBean();
	}
	
}
