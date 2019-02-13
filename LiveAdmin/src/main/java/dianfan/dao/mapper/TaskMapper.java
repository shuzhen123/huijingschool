package dianfan.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName TaskMapper
 * @Description 任务调度相关dao
 * @author cjy
 * @date 2018年2月23日 上午11:43:45
 */
@Repository
public interface TaskMapper {

	/**
	 * @Title: findAllSaler
	 * @Description: 获取所有有效业务员
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 上午11:45:27
	 */
	@Select("select userid from t_userinfo where role = 3 and entkbn = 0")
	List<String> findAllSaler() throws SQLExecutorException;

	/**
	 * @Title: findSalerLevelTaskNum
	 * @Description: 获取业务员对应等级的任务数
	 * @param ids
	 * @throws:
	 * @time: 2018年2月23日 下午5:46:08
	 */
	List<BashMap> findSalerLevelTaskNum(List<String> ids) throws SQLExecutorException;
	
	/**
	 * @Title: addSalerDistribute
	 * @Description: 给所有业务员添加任务数
	 * @param data
	 * @throws:
	 * @time: 2018年2月23日 下午12:12:52
	 */
	void addSalerDistribute(List<Map<String, String>> data) throws SQLExecutorException;

	/**
	 * @Title: scanLiveCourse
	 * @Description: 检测预告直播，更改直播状态
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 下午7:02:12
	 */
	@Update("update t_course set liveflag=1 where courseid in " + 
			"(select  courseid from  t_course_live_notice where starttime <= now() and endtime > now()) " + 
			"and (coursekind=1 or coursekind=2) and auth=2 and flag=1 and liveflag=3 and entkbn=0")
	void scanLiveCourse() throws SQLExecutorException;

}
