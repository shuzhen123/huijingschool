package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AgentMapper
 * @Description 代理商dao
 * @author cjy
 * @date 2018年3月15日 下午3:10:21
 */
@Repository
public interface AgentMapper {

	/**
	 * @Title: findAgentCount
	 * @Description: 根据条件查询代理商总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午3:07:53
	 */
	Integer findAgentCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findAgentInfo
	 * @Description: 根据条件查询代理商数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午3:10:01
	 */
	List<UserInfo> findAgentInfo(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: addAgentInfo
	 * @Description: 添加新代理商
	 * @param user
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午4:33:20
	 */
	@Insert("insert into t_userinfo (userid, username, password, introduction, realname, telno, createid, registertime, registerstatus, role, agentcode, createtime, entkbn)"
			+ " values (#{userid}, #{username}, #{password}, #{introduction}, #{realname}, #{telno}, #{createid}, now(), 2, 4, #{agentcode}, now(), 0)")
	void addAgentInfo(UserInfo user) throws SQLExecutorException;

	/**
	 * @Title: getAgentData
	 * @Description: 根据id获取代理商数据
	 * @param agentid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午4:55:04
	 */
	@Select("select * from t_userinfo where userid=#{agentid}")
	UserInfo getAgentData(String agentid) throws SQLExecutorException;
	
	/**
	 * @Title: updateAgentInfo
	 * @Description: 代理商数据修改
	 * @param user
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午5:53:45
	 */
	@Update("update t_userinfo set password=#{password}, telno = #{telno}, realname = #{realname}, introduction = #{introduction}, agentcode=#{agentcode} where userid=#{userid}")
	void updateAgentInfo(UserInfo user) throws SQLExecutorException;
	
	/**
	 * @Title: updateSelfAgentInfo
	 * @Description: 代理商个人信息修改
	 * @param user
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月22日 下午6:29:18
	 */
	@Update("update t_userinfo set telno = #{telno}, realname = #{realname}, introduction = #{introduction} where userid=#{userid}")
	void updateSelfAgentInfo(UserInfo user) throws SQLExecutorException;

	/**
	 * @Title: findAgentTeaList
	 * @Description: 获取代理商的讲师列表
	 * @param agentid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月28日 下午3:51:49
	 */
	@Select("select info.userid, info.realname, info.trecflag, info.famousteacherflag, info.entkbn from t_agent_teac atea, t_userinfo info "
			+ "where atea.teacherid=info.userid and atea.agentid=#{agentid} and info.entkbn != 9")
	List<UserInfo> findAgentTeaList(String agentid) throws SQLExecutorException;

	/**
	 * @Title: changeCommend
	 * @Description: 讲师推荐设置
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月28日 下午4:50:38
	 */
	void changeCommend(Map<String, Object> data) throws SQLExecutorException;
	
	
	
	
}
