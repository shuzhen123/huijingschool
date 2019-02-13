package dianfan.dao.mapper.saler;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dianfan.entities.CourseOrderCollect;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName SalerStatisticsMapper
 * @Description 业务员报表dao
 * @author cjy
 * @date 2018年4月23日 下午3:11:08
 */
@Repository
public interface SalerStatisticsMapper {

	/**
	 * @Title: findCustomerUser
	 * @Description: 业务员下用户课程消费订单总数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月6日 下午5:21:40
	 */
	int findCourseOrderCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCourseOrder
	 * @Description: 业务员下用户课程消费订单数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月23日 下午3:12:22
	 */
	List<CourseOrderCollect> findCourseOrder(Map<String, Object> param) throws SQLExecutorException;
}