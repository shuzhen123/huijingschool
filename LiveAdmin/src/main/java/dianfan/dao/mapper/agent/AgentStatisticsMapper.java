package dianfan.dao.mapper.agent;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dianfan.entities.BranchProfit;
import dianfan.entities.Compliance;
import dianfan.entities.TalkTime;
import dianfan.entities.CourseOrderCollect;
import dianfan.entities.Reperecs;
import dianfan.entities.StatementBean;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AgentStatisticsMapper
 * @Description 代理商报表
 * @author cjy
 * @date 2018年2月12日 下午1:36:14
 */
@Repository
public interface AgentStatisticsMapper {

	/**
	 * @Title: findCustomerUser
	 * @Description: 代理商下用户课程消费订单总数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月6日 下午5:21:40
	 */
	int findCourseOrderCount(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findVipOrder
	 * @Description: 代理商下用户课程消费订单数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月12日 下午2:20:20
	 */
	List<CourseOrderCollect> findCourseOrder(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findGoodsOrderCount
	 * @Description: 根据条件获取礼物消费订单总条数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午3:42:26
	 */
	int findGoodsOrderCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findGoodsOrder
	 * @Description: 根据条件获取礼物消费订单数据
	 * @param param
	 * @throws:
	 * @time: 2018年2月12日 下午3:43:07
	 */
	List<StatementBean> findGoodsOrder(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: fingWageStatistics
	 * @Description: 代理商提成汇总
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月27日 下午3:31:38
	 */
	List<BranchProfit> fingWageStatistics(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: fingWageSalerStatistics
	 * @Description: 业务员提成汇总
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月7日 上午9:27:15
	 */
	List<BranchProfit> fingWageSalerStatistics(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerViolatorCount
	 * @Description: 根据条件获取业务员合规处理总条数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午2:45:23
	 */
	int findSalerViolatorCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerViolator
	 * @Description: 根据条件获取业务员合规处理数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 下午3:26:27
	 */
	List<Compliance> findSalerViolator(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: salerViolatorDetail
	 * @Description: 业务员合规处理详情数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 下午4:23:40
	 */
	List<Reperecs> salerViolatorDetail(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findTalktimestatisticsCount
	 * @Description: 根据条件获取业务员通话时长总条数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午2:45:23
	 */
	int findTalktimestatisticsCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findTalktimestatistics
	 * @Description: 根据条件获取业务员通话时长数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 下午3:26:27
	 */
	List<TalkTime> findTalktimestatistics(Map<String, Object> param) throws SQLExecutorException;
}