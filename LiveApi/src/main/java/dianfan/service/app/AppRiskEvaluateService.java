package dianfan.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppRiskEvaluateMapper;
import dianfan.entities.EvaluateAnswer;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;

/**
 * @ClassName AppMyInfoService
 * @Description 我的部分相关服务
 * @author cjy
 * @date 2018年3月29日 上午9:47:17
 */
@Service
public class AppRiskEvaluateService {
	@Autowired
	private AppRiskEvaluateMapper appMyInfoMapper;
	@Autowired
	private RedisService redisService;

	
	/**
	 * @Title: userRiskEvaluate
	 * @Description: 用户风险测评
	 * @param answerid
	 * 				用户提交的答案id
	 * @author: sz
	 * @param accesstoken 
	 * @throws SQLExecutorException 
	 * @date 2018年5月7日 下午3:27:40
	 */
	@Transactional
	public ResultBean userRiskEvaluate(List<String> answerid, String userid) throws SQLExecutorException {
		//获取风险评测问题题目的数量
		int questionCount = appMyInfoMapper.findQuestionCount();
		//判断用户是否完成全部答题
		if (answerid == null || answerid.size() < questionCount) {
			//存在没有回答的题目
			return new ResultBean("031",ResultMsg.C_031);
		}
		
		//1.获取用户答题的答案列表
		List<EvaluateAnswer> answer = appMyInfoMapper.userEvaluateList(answerid);
		
		//2.创建一个map
		Map<String, Object> param = new HashMap<>();
		//3.添加用户答题的答案列表
		param.put("answer", answer);
		//4.添加用的id
		param.put("userid", userid);
		//6.将用户之前测评的记录删除
		appMyInfoMapper.updateEvaluateEntkbn(userid);
		//7.将用户测评分数记录到用户风险评估测评表中
		appMyInfoMapper.saveEvaluateScore(param);
		
		//计算用户答题总分
		int score = 0;
		for(EvaluateAnswer ea : answer) {
			score += ea.getScore();
		}
		
		//评测合格分数线
		String evaluate_score_setting = redisService.get(ConstantIF.EVALUATE_SCORE);
		int evaluate_score = 0;
		if(evaluate_score_setting != null && !evaluate_score_setting.isEmpty()) {
			evaluate_score = Integer.parseInt(evaluate_score_setting);
		}
		//判断用户风险评估是否合格
		if (score < evaluate_score) {
			//风险评估未通过
			return new ResultBean("032",ResultMsg.C_032); 
		} 
		//将用户信息改为通过测评
		appMyInfoMapper.changeRiskratingfalg(userid);
		
		return new ResultBean();
	}

	
	
	
	
	
}
