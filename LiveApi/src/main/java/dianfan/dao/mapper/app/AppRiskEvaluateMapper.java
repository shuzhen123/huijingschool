package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.EvaluateAnswer;
import dianfan.entities.EvaluateClass;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AppMyInfoMapper
 * @Description 我的部分dao
 * @author cjy
 * @date 2018年3月29日 上午9:47:04
 */
@Repository
public interface AppRiskEvaluateMapper {

	/**
	 * @Title: getRiskEvaluateQuestion
	 * @Description: 获取风险评测列表
	 * @return EvaluateClass
	 * @throws SQLExecutorException
	 * @date 2018年5月7日 上午10:50:27
	 */
	List<EvaluateClass> getRiskEvaluateQuestion() throws SQLExecutorException;

	/**
	 * @Title: findQuestionCount
	 * @Description: 获取风险评测问题题目的数量
	 * @return int 
	 * @date 2018年5月7日 下午3:35:31
	 */
	@Select("select count(*) from t_evaluate_question where entkbn=0")
	int findQuestionCount() throws SQLExecutorException;

	/**
	 * @Title: userEvaluateList
	 * @Description: 风险评测用户答安列表
	 * @param answerid 
	 * 			用户选择的答案id
	 * @return
	 * @author: sz
	 * @date 2018年5月7日 下午4:35:44
	 */
	List<EvaluateAnswer> userEvaluateList(List<String> answerid) throws SQLExecutorException;
	
	/**
	 * @Title: saveEvaluateScore
	 * @Description: 将用户的风险评测的分数持久化
	 * @param param
	 * 			用户分数
	 * @author: sz
	 * @date 2018年5月7日 下午4:18:17
	 */
	void saveEvaluateScore(Map<String, Object> param) throws SQLExecutorException;


	/**
	 * @Title: changeRiskratingfalg
	 * @Description: 修改用户的风险测评通过flag
	 * @param userid
	 * @author: sz
	 * @date 2018年5月7日 下午5:48:22
	 */
	@Update("update t_userinfo set riskratingfalg = 1 where userid = #{userid}")
	void changeRiskratingfalg(String userid) throws SQLExecutorException;


	/**
	 * @Title: updateEvaluateEntkbn
	 * @Description: 将用户之前测评的记录删除
	 * @param userid
	 * @author: sz
	 * @date 2018年5月7日 下午7:13:32
	 */
	@Update("update t_evaluate_user_answer set entkbn = 9 where userid = #{userid}")
	void updateEvaluateEntkbn(String userid) throws SQLExecutorException;


	



}
