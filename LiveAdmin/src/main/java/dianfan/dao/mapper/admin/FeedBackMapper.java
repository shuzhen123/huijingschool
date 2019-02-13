package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.FeedBack;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName FeedBackMapper
 * @Description 用户反馈dao
 * @author cjy
 * @date 2018年1月11日 下午4:34:23
 */
@Repository
public interface FeedBackMapper {
	
	/**
	 * @Title: findFeedBacksCount
	 * @Description: 根据条件获取用户反馈条数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午4:34:11
	 */
	int findFeedBacksCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findFeedBacks
	 * @Description: 根据条件获取用户反馈数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午4:34:47
	 */
	List<FeedBack> findFeedBacks(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: delFeedbackInfoByIds
	 * @Description: 根据id批量删除用户反馈数据
	 * @param ids
	 * @throws:
	 * @time: 2018年1月11日 下午5:27:55
	 */
	void delFeedbackInfoByIds(List<String> ids) throws SQLExecutorException;

	/**
	 * @Title: updateFeedbackStatus
	 * @Description: 根据id更新反馈信息状态
	 * @param param
	 * @throws:
	 * @time: 2018年1月11日 下午6:13:16
	 */
	@Update("update t_userfeedback set entkbn = #{status} where id=#{id}")
	void updateFeedbackStatus(Map<String, String> param) throws SQLExecutorException;
}