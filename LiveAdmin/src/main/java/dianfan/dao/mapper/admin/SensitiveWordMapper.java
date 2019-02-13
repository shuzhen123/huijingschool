package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.HandleMethod;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName SensitiveWordMapper
 * @Description 文字聊天与合规dao
 * @author cjy
 * @date 2018年3月26日 下午2:01:09
 */
@Repository
public interface SensitiveWordMapper {

	@Select("select id, words name from t_sensitive_words where entkbn = 0 order by createtime desc")
	List<BashMap> findWords() throws SQLExecutorException;

	/**
	 * @Title: checkSensitiveWord
	 * @Description: 检测文字是否已添加
	 * @param word
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 上午10:49:17
	 */
	@Select("select count(*) from t_sensitive_words where words=#{word} and entkbn=0")
	int checkSensitiveWord(String word) throws SQLExecutorException;
	
	/**
	 * @Title: addSensitiveWord
	 * @Description: 添加文字
	 * @param bm
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 上午10:35:55
	 */
	@Insert("insert into t_sensitive_words (id, words, createtime, entkbn) values (#{id}, #{name}, now(), 0)")
	void addSensitiveWord(BashMap bm) throws SQLExecutorException;

	/**
	 * @Title: stopSensitiveWord
	 * @Description: 停用敏感文字
	 * @param wordid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 上午10:44:21
	 */
	@Select("update t_sensitive_words set entkbn = 9 where id=#{wordid}")
	void stopSensitiveWord(String wordid) throws SQLExecutorException;

	/**
	 * @Title: findComplianceCount
	 * @Description: 根据条件查询合规总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 上午11:33:37
	 */
	@Select("select count(*) from t_handlemethod where entkbn=0")
	Integer findComplianceCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCompliance
	 * @Description: 根据条件查询合规数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 上午11:34:35
	 */
	@Select("select id, counts, type, money, createtime from t_handlemethod where entkbn=0 order by createtime desc limit #{start}, #{length}")
	List<HandleMethod> findCompliance(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: addCompliance
	 * @Description: 添加合规处罚项
	 * @param hm
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 下午12:07:51
	 */
	@Insert("insert into t_handlemethod (id, type, money, createtime, entkbn) values (replace(uuid(),'-',''), #{type}, #{money}, now(), 0)")
	void addCompliance(HandleMethod hm) throws SQLExecutorException;

	/**
	 * @Title: editCompliance
	 * @Description: 修改合规处罚项
	 * @param hm
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 下午1:50:57
	 */
	@Update("update t_handlemethod set type=#{type}, money=#{money} where id=#{id}")
	void editCompliance(HandleMethod hm) throws SQLExecutorException;

	/**
	 * @Title: delCompliance
	 * @Description: 删除合规
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 下午2:00:10
	 */
	void delCompliance(List<String> lids) throws SQLExecutorException;

	


	
	
}