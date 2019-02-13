package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import dianfan.entities.EvaluateAnswer;
import dianfan.entities.EvaluateList;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName: RiskEvaluateMapper
 * @Description: 风险测评dao
 * @author sz
 * @time 2018年5月8日 上午9:41:57
 */
@Repository
public interface RiskEvaluateMapper {

	/**
	 * @Title: riskEvaluateCount
	 * @Description: 获取风险测评条数
	 * @param param
	 * 			请求参数
	 * @return int
	 * @author: sz
	 * @date 2018年5月8日 上午9:45:11
	 */
	@Select("select count(*) from t_evaluate_answer where entkbn = 0")
	int riskEvaluateCount(Map<String, Object> param) throws SQLExecutorException;

	
	/**
	 * @Title: riskEvaluateList
	 * @Description: 风险测评列表
	 * @param param
	 * 			请求参数
	 * @return EvaluateClass
	 * @author: sz
	 * @date 2018年5月8日 上午9:54:27
	 */
	List<EvaluateList> riskEvaluateList(Map<String, Object> param) throws SQLExecutorException;


	/**
	 * @Title: fildAnswerById
	 * @Description: 根据id获取对应答案数据
	 * @param id
	 * @return EvaluateAnswer
	 * @author: sz
	 * @date 2018年5月8日 上午10:35:49
	 */
	@Select("select id answerid, answername, score from t_evaluate_answer where entkbn = 0 and id = #{answerid}")
	EvaluateAnswer fildAnswerById(String answerid) throws SQLExecutorException;


	/**
	 * @Title: editEvaluateScore
	 * @Description: 测评答案对应的分数修改
	 * @param answer
	 * @author: sz
	 * @date 2018年5月8日 上午11:06:50
	 */
	@Update("update t_evaluate_answer set score = #{score} where id = #{answerid} and entkbn = 0")
	void editEvaluateScore(EvaluateAnswer score) throws SQLExecutorException;
	
	

}
