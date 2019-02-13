package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Role;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AdminRoleMapper
 * @Description 管理员权限类dao
 * @author cjy
 * @date 2018年1月15日 上午11:48:59
 */
@Repository
public interface AdminRoleMapper {
	
	/**
	 * @Title: findRoleByStatus
	 * @Description: 根据status获取权限
	 * @param flag
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 下午4:10:44
	 */
	List<Role> findRoleByStatus(Role roel) throws SQLExecutorException;
	
	/**
	 * @Title: addNewRole
	 * @Description: 添加新权限
	 * @param role
	 * @throws:
	 * @time: 2018年1月16日 上午11:03:03
	 */
	@Insert("insert into t_jurisdiction (jurisdictionid, jurisdictionname, functionname, flag) values (#{jurisdictionid}, #{jurisdictionname}, #{functionname}, #{flag})")
	void addNewRole(Role role) throws SQLExecutorException;

	/**
	 * @Title: changeRoleStatus
	 * @Description: 更改权限状态
	 * @param role
	 * @throws:
	 * @time: 2018年1月16日 下午1:58:16
	 */
	@Update("update t_jurisdiction set flag = #{flag} where jurisdictionid = #{jurisdictionid}")
	void changeRoleStatus(Role role) throws SQLExecutorException;

	/**
	 * @Title: findRoleByAdminId
	 * @Description: 根据管理员id获取管理员已拥有的权限列表
	 * @param userid
	 * @throws:
	 * @time: 2018年1月16日 下午4:53:01
	 */
	List<Role> findRoleByAdminId(String userid) throws SQLExecutorException;

	/**
	 * @Title: authRoleByUserid
	 * @Description: 授权
	 * @param param
	 * @throws:
	 * @time: 2018年1月16日 下午6:17:39
	 */
	@Insert("insert into t_u_j (id, juridictionid, userid) values (#{id}, #{roleid}, #{userid})")
	void authRoleByUserid(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: cancelRoleByUserid
	 * @Description: 撤销权限
	 * @param param
	 * @throws:
	 * @time: 2018年1月16日 下午6:17:48
	 */
	@Delete("delete from t_u_j where juridictionid = #{roleid} and userid = #{userid}")
	void cancelRoleByUserid(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: findAllRoles
	 * @Description: 
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月8日 下午6:03:10
	 */
	@Select("select lower(functionname) from t_jurisdiction")
	List<String> findAllRoles() throws SQLExecutorException;

	/**
	 * @Title: findAgentRoleByUserId
	 * @Description: 根据代理商id获取已拥有的权限列表
	 * @param userid
	 * @throws:
	 * @time: 2018年1月16日 下午4:53:01
	 */
	List<Role> findAgentRoleByUserId(String userid) throws SQLExecutorException;
	
	/**
	 * @Title: findAgentAllRoles
	 * @Description: 
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月8日 下午6:03:10
	 */
	@Select("select lower(functionname) from t_agent_jurisdiction")
	List<String> findAgentAllRoles() throws SQLExecutorException;

	
}
