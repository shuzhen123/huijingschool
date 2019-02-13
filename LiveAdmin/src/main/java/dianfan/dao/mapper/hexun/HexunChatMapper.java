package dianfan.dao.mapper.hexun;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dianfan.entities.ChatWord;
import dianfan.entities.SalerCall;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName SalerChatMapper
 * @Description 业务员通话记录dao
 * @author cjy
 * @date 2018年3月12日 下午12:13:21
 */
@Repository
public interface HexunChatMapper {
	
	/**
	 * @Title: findCustomerUser
	 * @Description: 根据条件获取通话记录总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月6日 下午5:21:40
	 */
	int findSalerPhoneCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCustomerUser
	 * @Description: 根据条件获取客户通话记录列表
	 * @param param
	 * @throws:
	 * @time: 2018年2月6日 下午5:25:46
	 */
	List<SalerCall> findSalerPhoneList(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerChatCount
	 * @Description: 根据条件查询业务员聊天记录数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午3:18:48
	 */
	int findSalerChatCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerChatList
	 * @Description: 根据条件查询业务员通话记录
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午5:29:20
	 */
	List<ChatWord> findSalerChatList(Map<String, Object> param) throws SQLExecutorException;
	
	
}