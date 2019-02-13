package dianfan.dao.mapper.agent;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dianfan.entities.ChartsBean;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AgentIndexMapper
 * @Description 首页数据dao
 * @author cjy
 * @date 2018年2月6日 上午9:26:37
 */
@Repository
public interface AgentIndexMapper {

	/**
	 * @Title: findRegisterUserByTime
	 * @Description: 获取时间段内的注册人数
	 * @param param
	 * @throws:
	 * @time: 2018年2月6日 上午11:03:12
	 */
	LinkedList<ChartsBean> findRegisterUserByTime(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findBuyCourseByTime
	 * @Description: 获取时间段内的课程购买金额
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年2月6日 上午11:45:04
	 */
	List<ChartsBean> findBuyCourseByTime(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findBuyGoodsByTime
	 * @Description: 获取时间段内的礼物购买金额
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月12日 上午11:19:45
	 */
	List<ChartsBean> findBuyGoodsByTime(Map<String, Object> param) throws SQLExecutorException;
}