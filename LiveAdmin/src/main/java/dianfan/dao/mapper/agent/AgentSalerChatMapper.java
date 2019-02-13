package dianfan.dao.mapper.agent;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.ChatWord;
import dianfan.entities.Reperecs;
import dianfan.entities.SalerCall;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName SalerChatMapper
 * @Description 业务员通话记录dao
 * @author cjy
 * @date 2018年3月12日 下午12:13:21
 */
@Repository
public interface AgentSalerChatMapper {
	
	/**
	 * @Title: getPunishMethod
	 * @Description: 获取处罚方式
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月16日 下午4:42:49
	 */
	@Select("select id, if(type=1, concat('罚款 - ￥', money), if(type=2, '封号', '')) name  from t_handlemethod where entkbn=0")
	List<BashMap> getPunishMethod() throws SQLExecutorException;
	
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
	 * @Title: findViolator
	 * @Description: 客户通话违规记录
	 * @param tellid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月12日 下午5:47:29
	 */
	@Select("select rep.id, rep.detail, rep.money, rep.createtime, hm.counts, hm.type "
			+ "from t_reperecs rep, t_handlemethod hm "
			+ "where rep.handlemethodid=hm.id and rep.type=2 and rep.salecustomer_tellog_id=#{tellid} order by rep.createtime desc")
	List<Reperecs> findTellViolator(String tellid) throws SQLExecutorException;

	/**
	 * @Title: findChatViolator
	 * @Description: 客户聊天违规记录
	 * @param tellid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午3:49:17
	 */
	@Select("select rep.id, rep.detail, rep.money, rep.createtime, hm.counts, hm.type, rep.handlemethodid "
			+ "from t_reperecs rep left join t_handlemethod hm on rep.handlemethodid=hm.id "
			+ "where rep.type=1 and rep.wordsid=#{wordsid} order by rep.createtime desc")
	List<Reperecs> findChatViolator(String wordsid) throws SQLExecutorException;
	
	/**
	 * @Title: addViolatorInfo
	 * @Description: 添加通话违规记录
	 * @param reperecs
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月17日 上午10:35:01
	 */
	@Insert("insert into t_reperecs (id, type, detail, salecustomer_tellog_id, saleid, money, handlemethodid, createtime, entkbn) "
			+ "values (replace(uuid(),'-',''), 2, #{detail}, #{salecustomer_tellog_id}, #{saleid}, "
			+ "(select money from t_handlemethod where id=#{handlemethodid}), #{handlemethodid}, now(), 0)")
	void addViolatorInfo(Reperecs reperecs) throws SQLExecutorException;

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
	
	/**
	 * @Title: dealToViolator
	 * @Description: 处理通话违规记录
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 上午10:07:50
	 */
	@Update("update t_reperecs set handlemethodid=#{methodid}, money=(select money from t_handlemethod where id=#{methodid}) where id=#{id}")
	void dealToViolator(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: getMethodType
	 * @Description: 查看处罚类型1：罚款2：封号
	 * @param methodid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 上午10:23:06
	 */
	@Select("select type from t_handlemethod where id=#{methodid}")
	Integer getMethodType(String methodid) throws SQLExecutorException;
	
	/**
	 * @Title: setSalerAccountForbid
	 * @Description: 业务员封号操作
	 * @param id
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 上午10:26:43
	 */
	@Update("update t_userinfo set entkbn=1 where userid=(select saleid from t_reperecs where id=#{id})")
	void setSalerAccountForbid(String id) throws SQLExecutorException;
	
}