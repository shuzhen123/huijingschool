package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Shares;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName VipNewsMapper
 * @Description vip咨讯dao
 * @author cjy
 * @date 2018年2月27日 下午1:39:16
 */
@Repository
public interface SharesMapper {

	/**
	 * @Title: findSharesCount
	 * @Description: 获取诊股总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月28日 下午1:32:18
	 */
	@Select("select count(*) from t_clinicshares where entkbn=0")
	int findSharesCount(Map<String, Object> param) throws SQLExecutorException;

	@Select("select clin.*, user.telno from t_clinicshares clin, t_userinfo user " + 
			"where clin.createid=user.userid and clin.entkbn=0 order by createtime desc")
	List<Shares> findShares(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: sharesAnswer
	 * @Description: 诊股设置已回答
	 * @param sharesid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 下午5:45:54
	 */
	@Update("update t_clinicshares set flag=1, answertime=now() where id=#{sharesid}")
	void sharesAnswer(String sharesid) throws SQLExecutorException;

}