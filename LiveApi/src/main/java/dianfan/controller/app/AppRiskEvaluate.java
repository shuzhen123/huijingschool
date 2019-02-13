package dianfan.controller.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppRiskEvaluateMapper;
import dianfan.entities.EvaluateClass;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppRiskEvaluateService;

/**
 * @ClassName AppRiskEvaluate
 * @Description 风险评测
 * @author cjy
 * @date 2018年5月7日 上午9:18:09
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppRiskEvaluate {
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private AppRiskEvaluateService riskEvaluateService;
	@Autowired
	private AppRiskEvaluateMapper riskEvaluateMapper;
	
	
	/**
	 * @Title: checkEvaluating
	 * @Description: 获取风险评测题目
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年3月29日 下午2:14:12
	 */
	@LogOp(method = "checkEvaluating", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "获取风险评测题目")
	@ApiOperation(value = "获取风险评测题目", httpMethod = "GET", notes = "获取风险评测题目", response = ResultBean.class)
	@RequestMapping(value = "/evaluate/question", method = {RequestMethod.GET})
	@UnCheckedFilter
	public @ResponseBody ResultBean riskEvaluateQuestion() {
		try {
			List<EvaluateClass> riskEvaluateQuestion = riskEvaluateMapper.getRiskEvaluateQuestion();
			return new ResultBean(riskEvaluateQuestion);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: userRiskEvaluate
	 * @Description: 用户测评结果
	 * @return resultbaean
	 * @throws SQLExecutorException 
	 * @date 2018年5月7日 下午3:01:16
	 */
	@LogOp(method = "userRiskEvaluate", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "用户测评结果")
	@ApiOperation(value = "用户测评结果", httpMethod = "POST", notes = "用户测评结果", response = ResultBean.class)
	@RequestMapping(value = "/evaluate/userRiskEvaluate", method = {RequestMethod.POST})
	public @ResponseBody ResultBean userRiskEvaluate(
			@ApiParam("用户提交的答案id") @RequestParam(value= "ids") String ids,
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken
			) {
		//获取token令牌
		TokenModel token = redisTokenService.getToken(accesstoken);
		//获取用户的id
		String userid = token.getUserId();
		
		//ids无可用数据
		if (ids == null || ids.length() < 1 ) {
			return new ResultBean("501",ResultMsg.C_501);
		} 
		
		//分隔ids
		String [] idStrings = ids.split(",");
		//将idStrings存入  “答案id” 集合中
		List<String> answerid = new ArrayList<>();
		//遍历 ids[] 
		for (String id : idStrings) {
			//添加至answerid集合中
			answerid.add(id);
		}
		try {
			return riskEvaluateService.userRiskEvaluate(answerid,userid);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
	
	
}
