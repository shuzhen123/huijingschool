package dianfan.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.Popu;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentQRService;

/**
 * @ClassName Pupo
 * @Description 推广接口
 * @author cjy
 * @date 2018年2月2日 上午11:31:04
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/popu")
public class PopuCheck {
	@Autowired
	private AgentQRService agentQRService;
	
	/**
	 * @Title: qr
	 * @Description: 二维码扫码接口
	 * @return
	 * @throws:
	 * @time: 2018年2月2日 上午11:32:31
	 */
	@LogOp(method = "qr",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "二维码扫码接口")
	@RequestMapping(value = "qr")
	@UnCheckedFilter
	public String qr(HttpServletRequest request, Model model) {
		Popu popu = new Popu();
		popu.setUserid(request.getParameter("uid"));
		popu.setUpdatetime(new Timestamp(Long.parseLong(request.getParameter("t"))));
		popu.setCheckcode(request.getParameter("c"));
		
		if(request.getParameter("type") != null && "regqr".equals(request.getParameter("type"))) {
			popu.setType(1);
		}else if(request.getParameter("type") != null && "live".equals(request.getParameter("type"))) {
			popu.setType(2);
		}else {
			model.addAttribute("data", new ResultBean("501", ResultMsg.C_501));
		}
		
		try {
			popu = agentQRService.checkQR(popu);
			if(popu != null) {
				model.addAttribute("data", new ResultBean(popu));
			}else {
				model.addAttribute("data", new ResultBean("021", ResultMsg.C_021));
			}
		} catch (SQLExecutorException e) {
			model.addAttribute("data", new ResultBean("500", ResultMsg.C_500));
		}
		return "qr";
	}
	
}
