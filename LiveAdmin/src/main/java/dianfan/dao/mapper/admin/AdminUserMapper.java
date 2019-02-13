package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Role;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AdminUserMapper
 * @Description 管理员用户相关信息类dao
 * @author cjy
 * @date 2018年1月2日 下午4:06:26
 */
@Repository
public interface AdminUserMapper {
	
	/**
	 * @Title: findUserByAccount
	 * @Description: 根据手机号码或密码获取用户数据 
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月12日 下午2:08:35
	 */
	@Select("select * from t_userinfo where username = #{username} and password = #{password} and role != 1 limit 0, 1")
	UserInfo findUserByAccount(Map<String, String> param) throws SQLExecutorException;
	
	/**
	 * @Title: updateAdminUser
	 * @Description: 人员信息修改
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月9日 下午4:33:09
	 */
	void updateAdminUser(UserInfo info) throws SQLExecutorException;

	/**
	 * @Title: updateAdminUserPwd
	 * @Description: 修改密码
	 * @param userInfo
	 * @throws:
	 * @time: 2018年1月9日 下午5:07:37
	 */
	@Update("update t_userinfo set password = #{password} where userid = #{userid}")
	void updateAdminUserPwd(UserInfo userInfo) throws SQLExecutorException;
	
	/**
	 * @Title: findAdminUserCount
	 * @Description: 管理员总数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午12:18:15
	 */
	@Select("select count(*) from t_userinfo where role=2 and entkbn != 9 and username !='admin' and userid != #{userid}")
	int findAdminUserCount(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findAdminUsers
	 * @Description: 根据条件查询管理员列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月3日 下午1:41:56
	 */
	@Select("select userid, username, realname, telno, registertime, entkbn from t_userinfo where entkbn!=9  and username !='admin' and userid != #{userid} and role=2 limit #{start},#{length}")
	List<UserInfo> findAdminUsers(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findRoles
	 * @Description: 获取权限列表
	 * @param flag
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 下午4:10:44
	 */
	@Select("select * from t_jurisdiction where flag=1")
	List<Role> findRoles() throws SQLExecutorException;

	/**
	 * @Title: deleteUserById
	 * @Description: 根据用户id删除用户
	 * @param userid
	 * @throws:
	 * @time: 2018年1月4日 下午2:18:13
	 */
	void deleteUserById(List<String> userid) throws SQLExecutorException;

	/**
	 * @Title: getUserDataById
	 * @Description: 根据用户id获取用户数据
	 * @param userid
	 * @throws:
	 * @time: 2018年1月4日 下午3:53:11
	 */
	@Select("select * from t_userinfo where userid=#{userid}")
	UserInfo getUserDataById(String userid) throws SQLExecutorException;

	/**
	 * @Title: updateUserById
	 * @Description: 更新用户数据
	 * @param userInfo
	 * @throws:
	 * @time: 2018年1月5日 上午10:47:18
	 */
	void updateUserById(UserInfo userInfo) throws SQLExecutorException;

	/**
	 * @Title: addUserInfo
	 * @Description: 添加新用户
	 * @param userInfo
	 * @throws:
	 * @time: 2018年1月5日 下午3:51:03
	 */
	@Insert("insert into t_userinfo ( userid, username, password, realname, telno, registertime, registerstatus, role, createtime, entkbn) values "
			+ "(#{userid}, #{username}, #{password}, #{realname}, #{telno}, now(), 1, 2, now(), 0)")
	void addAdminUser(UserInfo userInfo) throws SQLExecutorException;
	
	/**
	 * @Title: addAdminUserRole
	 * @Description: 管理员权限添加
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午3:43:34
	 */
	void addAdminUserRole(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getUserRoleById
	 * @Description: 获取管理员已有权限
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午4:03:05
	 */
	@Select("select juridictionid from t_u_j where userid=#{userid}")
	List<String> getUserRoleById(String userid) throws SQLExecutorException;

	/**
	 * @Title: editAdminUser
	 * @Description: 管理员修改
	 * @param user
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午4:25:54
	 */
	@Insert("update t_userinfo set password=#{password}, realname=#{realname}, telno=#{telno} where userid=#{userid}")
	void editAdminUser(UserInfo user) throws SQLExecutorException;

	/**
	 * @Title: cleanAdminUserRole
	 * @Description: 清空权限
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午5:02:01
	 */
	@Delete("delete from t_u_j where userid=#{userid}")
	void cleanAdminUserRole(String userid) throws SQLExecutorException;

	/**
	 * @Title: delAdminUser
	 * @Description: 删除管理员账号
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月18日 上午9:55:52
	 */
	void delAdminUser(List<String> lids) throws SQLExecutorException;

	/**
	 * @Title: getAgentByUserId
	 * @Description: 根据用户id获取代理商信息
	 * @param userid
	 * @throws:
	 * @time: 2018年1月4日 下午3:53:11
	 */
	@Select("select agentuserid from t_agent_user where salesmanuserid=#{userid} limit 0,1")
	String getAgentByUserId(String userid) throws SQLExecutorException;
	
}
