package dianfan.dao.mapper.saler;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.ChatWord;
import dianfan.entities.Reperecs;
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
public interface SalerChatMapper {
	/**
	 * @Title: getViolatorCount
	 * @Description: 获取业务员记录违规总次数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 上午10:29:32
	 */
	@Select("select count(*) from t_reperecs rep where saleid=#{saleid} and type=#{type} and entkbn=0")
	int getViolatorCount(Map<String, String> param) throws SQLExecutorException;
	
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
	 * @Title: updatePhoneChatCollect
	 * @Description: 更改收藏状态
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月12日 下午3:55:26
	 */
	@Update("update t_salecustomer_tellog set collectionflag = #{action} where id = #{tellid}")
	void updatePhoneChatCollect(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findViolator
	 * @Description: 客户通话违规记录
	 * @param tellid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月12日 下午5:47:29
	 */
	@Select("select rep.id, rep.detail, rep.money, rep.createtime, rep.handlemethodid, hm.counts, hm.type "
			+ "from t_reperecs rep left join t_handlemethod hm on rep.handlemethodid=hm.id "
			+ "where rep.type=2 and rep.salecustomer_tellog_id=#{tellid} order by rep.createtime desc")
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
	@Select("select rep.id, rep.detail, rep.money, rep.createtime, rep.handlemethodid, hm.counts, hm.type, rep.handlemethodid "
			+ "from t_reperecs rep left join t_handlemethod hm on rep.handlemethodid=hm.id "
			+ "where rep.type=1 and rep.wordsid=#{wordsid} order by rep.createtime desc")
	List<Reperecs> findChatViolator(String wordsid) throws SQLExecutorException;

	/**
	 * @Title: findSalerChatCount
	 * @Description: 根据条件查询客户聊天记录数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午3:18:48
	 */
	int findSalerChatCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerChatList
	 * @Description: 根据条件查询客户通话记录
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午5:29:20
	 */
	List<ChatWord> findSalerChatList(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findSalerCustomerCount
	 * @Description: 获取当前业务员下的客户手机号数量
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午5:58:05
	 */
	/*
	@Select("select count(*) from t_customer ct, t_userinfo info " + 
			"where ct.userid=info.userid and ct.dtsmuserid=#{salerid} and ct.entkbn=0 and info.entkbn!=9 and info.telno like #{key}")
	int findSalerCustomerCount(Map<String, Object> param) throws SQLExecutorException;
	*/
	/**
	 * @Title: ChangePhoneChatCollect
	 * @Description: 获取当前业务员下的客户手机号
	 * @param salerid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午5:30:38
	 */
	/*
	@Select("select info.userid id, info.telno text from t_customer ct, t_userinfo info where ct.userid=info.userid and "
			+ "ct.dtsmuserid=#{salerid} and ct.entkbn=0 and info.entkbn!=9 and info.telno like #{key} limit #{start}, #{length}")
	List<Select2> getSalerCustomerPhone(Map<String, Object> param) throws SQLExecutorException;
	*/
	
	/**
	 * @Title: findSalerCustomerInfo
	 * @Description: 获取业务员对应的用户账号和昵称
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 下午5:41:51
	 */
	@Select("select info.userid id, concat(info.telno, '（', IFNULL(info.nickname,'未知'), '）') name from t_customer ct, t_userinfo info " + 
			"where ct.userid=info.userid and ct.dtsmuserid=#{userid} and ct.entkbn=0 and info.entkbn!=9 order by info.registertime desc")
	List<BashMap> findSalerCustomerInfo(String userid) throws SQLExecutorException;

	/**
	 * @Title: addChatWord
	 * @Description: 客户聊天记录写入主记录表
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午12:05:00
	 */
	@Insert("insert into t_words (id, saleid, customerid, createtime, entkbn) values (#{id}, #{saleid}, #{customerid}, now(), 0)")
	void addChatWordInfo(Map<String, String> data) throws SQLExecutorException;

	/**
	 * @Title: addChatHistoryInfo
	 * @Description: 客户聊天记录写入历史记录表
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午12:06:03
	 */
	@Insert("insert into t_words_history (id, wordid, words, saleid, customerid, createtime, entkbn) "
			+ "values (#{id}, #{wordid}, #{words}, #{saleid}, #{customerid}, now(), 0)")
	void addChatHistoryInfo(Map<String, String> data) throws SQLExecutorException;

	/**
	 * @Title: findSensitiveWord
	 * @Description: 获取敏感文字数组
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午12:33:42
	 */
	@Select("select words from t_sensitive_words where entkbn=0")
	String[] findSensitiveWord() throws SQLExecutorException;

	/**
	 * @Title: addReperece
	 * @Description: 添加违规记录
	 * @param rep
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午12:37:31
	 */
	@Insert("insert into t_reperecs (id, type, detail, wordsid, saleid, createtime, entkbn) values "
			+ "(#{id}, #{type}, #{detail}, #{wordsid}, #{saleid}, now(), 0)")
	void addReperece(Reperecs rep) throws SQLExecutorException;

	/**
	 * @Title: findSalerChatAndUserCount
	 * @Description: 单客户跟踪记录总数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午6:49:50
	 */
	@Select("select count(*) from t_words word where word.customerid=#{userid} and word.entkbn=0")
	int findSalerChatAndUserCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerChatAndUserList
	 * @Description: 单客户跟踪记录列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午6:49:53
	 */
	List<ChatWord> findSalerChatAndUserList(Map<String, Object> param) throws SQLExecutorException;

	


	
}