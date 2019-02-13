package dianfan.dao.mapper.app;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.CallVoice;
import dianfan.entities.Reperece;
import dianfan.exception.SQLExecutorException;
@Repository
public interface PhoneChatMapper {

	/**
	 * @Title: getNickNameById
	 * @Description: 根据客户id获取客户昵称
	 * @param customerid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 下午12:54:41
	 */
	@Select("select nickname from t_userinfo where userid = #{customerid}")
	String getNickNameById(String customerid) throws SQLExecutorException;

	/**
	 * @Title: addCallVoice
	 * @Description: 存储语音通话记录
	 * @param call
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 下午1:03:05
	 */
	@Insert("insert into t_salecustomer_tellog (id, saleid, customerid, telno, status, calltimes, voicetitle, voicepath, collectionflag, starttime, endtime, createtime, entkbn) values "
			+ "(#{id}, #{saleid}, #{customerid}, #{telno}, 0, #{calltimes}, #{voicetitle}, #{voicepath}, #{collectionflag}, #{starttime}, #{endtime}, now(), 0)")
	void addCallVoice(CallVoice call) throws SQLExecutorException;

	/**
	 * @Title: addChatWord
	 * @Description: 将业务员聊天文字写入主记录表
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 下午1:58:07
	 */
	@Insert("insert into t_words (id, saleid, customerid, createtime, entkbn) values (#{id}, #{saleid}, #{customerid}, now(), 0)")
	void addChatWord(Map<String, String> data) throws SQLExecutorException;

	/**
	 * @Title: addChatWordHistory
	 * @Description: 将业务员聊天文字写入历史记录表
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 下午2:05:04
	 */
	@Insert("insert into t_words_history (id, words, saleid, customerid, createtime, entkbn) values "
			+ "(#{id}, #{word}, #{saleid}, #{customerid}, now(), 0)")
	void addChatWordHistory(Map<String, String> data) throws SQLExecutorException;

	/**
	 * @Title: findSensitiveWord
	 * @Description: 
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 下午2:29:51
	 */
	@Select("select words from t_sensitive_words where entkbn=0")
	String[] findSensitiveWord() throws SQLExecutorException;

	/**
	 * @Title: addReperece
	 * @Description: 添加违规记录
	 * @param rep
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 下午3:29:56
	 */
	@Insert("insert into t_reperecs (id, type, detail, wordsid, saleid, createtime, entkbn) values "
			+ "(#{id}, #{type}, #{detail}, #{wordsid}, #{saleid}, now(), 0)")
	void addReperece(Reperece rep) throws SQLExecutorException;

}
