package dianfan.dao.mapper.agent;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.ChannelBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AgentTeacherMapper
 * @Description 教师相关dao
 * @author cjy
 * @date 2018年3月20日 下午4:27:57
 */
@Repository
public interface AgentTeacherMapper {

	/**
	 * @Title: findTeacherCount
	 * @Description: 根据条件获取教师总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午4:35:04
	 */
	int findTeacherCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findTeachers
	 * @Description: 根据条件获取教师数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午4:35:31
	 */
	List<UserInfo> findTeachers(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: addTeacherChannel
	 * @Description: 添加教师频道数据
	 * @param channel
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月24日 上午11:52:03
	 */
	@Insert("insert into t_channel (id, accid, token, cid, channelname, roomid, chatroomname, pushUrl, httpPullUrl, hlsPullUrl, rtmpPullUrl, ctime) values "
			+ "(#{id}, #{accid}, #{token}, #{cid}, #{channelname}, #{roomid}, #{chatroomname}, #{pushUrl}, #{httpPullUrl}, #{hlsPullUrl}, #{rtmpPullUrl}, #{ctime})")
	void addTeacherChannel(ChannelBean channel) throws SQLExecutorException;

	/**
	 * @Title: addTeacherInfo
	 * @Description: 添加教师数据
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午5:43:50
	 */
	void addTeacherInfo(UserInfo info) throws SQLExecutorException;

	/**
	 * @Title: addAgentTeacherRelation
	 * @Description: 添加代理商-讲师关系
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午5:44:58
	 */
	@Insert("Insert into t_agent_teac (id, agentid, teacherid) values (replace(uuid(),'-',''), #{createid}, #{userid})")
	void addAgentTeacherRelation(UserInfo info) throws SQLExecutorException;

	/**
	 * @Title: getTeacherInfo
	 * @Description: 讲师数据详情
	 * @param teacherid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午6:13:43
	 */
	@Select("select * from t_userinfo where userid=#{teacherid}")
	UserInfo getTeacherInfo(String teacherid) throws SQLExecutorException;

	/**
	 * @Title: editTeacherInfo
	 * @Description: 更新讲师数据
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午6:29:37
	 */
	void editTeacherInfo(UserInfo info) throws SQLExecutorException;

	

}