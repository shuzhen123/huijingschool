package dianfan.controller.admin;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.RiskEvaluateMapper;
import dianfan.entities.DataTable;
import dianfan.entities.EvaluateAnswer;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.RiskEvaluateService;

/**
 * @ClassName: RiskEvaluateManage
 * @Description: 
 * @author sz
 * @time 2018年5月8日 上午9:20:52
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class RiskEvaluateManage {
	
	/**
	 * 注入RiskEvaluateService
	 */
	@Autowired
	private RiskEvaluateService riskEvaluateService;
	/**
	 * 注入RiskEvaluateMapper
	 */
	@Autowired
	private RiskEvaluateMapper riskEvaluateMapper;
	/**
	 * 注入RedisService
	 */
	@Autowired
	private RedisService redisService;
	
	/**
	 * @Title: dashboardRiskEvaluate
	 * @Description: 风险测评管理页
	 * @return String
	 * @author: sz
	 * @date 2018年5月8日 上午9:32:32
	 */
	@LogOp(method = "dashboardRiskEvaluate", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "风险测评管理页")
	@RequestMapping(value = "dashboardRiskEvaluate")
	public String dashboardRiskEvaluate(Model model) {
		//获取评测通过分数线
		String score = redisService.get(ConstantIF.EVALUATE_SCORE);
		model.addAttribute("score", score);
		//跳转至风险测评管理页
		return ConstantIF.ADMIN_EVALUATE + "evaluate-list";
	}

	/**
	 * @Title: riskEvaluateList
	 * @Description: 风险测评列表
	 * @param request
	 * @return table
	 * @author: sz
	 * @date 2018年5月8日 上午9:28:00
	 */
	@LogOp(method = "riskEvaluateList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "风险测评列表")
	@RequestMapping(value = "riskEvaluateList")
	public @ResponseBody DataTable riskEvaluateList (HttpServletRequest request) {
		//返回数据模型
		DataTable table = new DataTable();
		//创建map，添加分页查询参数
		Map<String, Object> param = new HashMap<>();
		param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
		param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
		//显示风险测评列表
		table = riskEvaluateService.fildEvaluateList(param);
		//设置每次请求的key
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		return table;
	}
	
	/**
	 * @Title: editRiskEvaluatePassScore
	 * @Description: 修改评测通过分数
	 * @param score
	 * @return
	 * @throws:
	 * @time: 2018年5月18日 上午11:42:42
	 */
	@LogOp(method = "editRiskEvaluatePassScore", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改评测通过分数")
	@RequestMapping(value = "editpassscore" , method = RequestMethod.POST)
	public @ResponseBody ResultBean editRiskEvaluatePassScore (String score) {
		if (score == null || score.isEmpty() || Integer.parseInt(score) < 0) {
			//评测通过分数设置不正确
			return new ResultBean("501",ResultMsg.C_501);
		}
		redisService.set(ConstantIF.EVALUATE_SCORE, score);
		return new ResultBean();
	}
	
	/**
	 * @Title: editRiskEvaluate
	 * @Description: 风险测评修分数改页
	 * @return String
	 * @author: sz
	 * @throws SQLExecutorException 
	 * @date 2018年5月8日 上午9:32:32
	 */
	@LogOp(method = "editRiskEvaluate", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "风险测评修分数改页")
	@RequestMapping(value = "editRiskEvaluate")
	public String editRiskEvaluate(String answerid, Model model, HttpSession session){
		try {
			//获取答案数据
			EvaluateAnswer answer = riskEvaluateMapper.fildAnswerById(answerid);
			model.addAttribute("answer", answer);
			//跳转至风险测评管理页
			return ConstantIF.ADMIN_EVALUATE + "evaluate-edit";
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
	}
	
	/**
	 * @Title: editRiskEvaluateScore
	 * @Description: 风险评测答案项对应的分数修改
	 * @param answer
	 * @return ResultBean 
	 * @author: sz
	 * @date 2018年5月8日 上午10:26:24
	 */
	@LogOp(method = "editRiskEvaluateScore", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = " 风险评测答案项对应的分数修改")
	@RequestMapping(value = "editRiskEvaluateScore" , method = RequestMethod.POST)
	public @ResponseBody ResultBean editRiskEvaluateScore (EvaluateAnswer score) {
		//answer基础验证 : 限制分数的类型只能是大于0 的整数
		if (score.getScore() == null || score.getScore() < 1 || score.getScore() % 1 != 0) {
			//错误提示：答案分数设置不正确
			return new ResultBean("026",ResultMsg.C_026);
		}
		//分数修改
		try {
			riskEvaluateService.editEvaluateScore(score);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
