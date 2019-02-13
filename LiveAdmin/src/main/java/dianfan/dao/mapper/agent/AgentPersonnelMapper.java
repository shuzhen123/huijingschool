package dianfan.dao.mapper.agent;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Dept;
import dianfan.entities.Position;
import dianfan.entities.Role;
import dianfan.entities.Saler;
import dianfan.entities.SalerTask;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AgentPersonnelMapper
 * @Description 代理商人事管理dao
 * @author cjy
 * @date 2018年2月7日 上午9:46:38
 */
@Repository
public interface AgentPersonnelMapper {
	/* *******************部门********************/
	/**
	 * @Title: findDeptCount
	 * @Description: 根据条件获取部门总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午9:51:10
	 */
	@Select("select count(*) from t_dept where createid=#{agentid} and entkbn = 0")
	int findDeptCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findDept
	 * @Description: 根据条件获取部门数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午10:01:11
	 */
	List<Dept> findDept(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: checkDeptName
	 * @Description: 部门名称重复性检测
	 * @param dept
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:07:50
	 */
	@Select("select count(*) from t_dept where createid = #{createid} and deptname = #{deptname} and entkbn = 0")
	Boolean checkDeptName(Dept dept) throws SQLExecutorException;
	
	/**
	 * @Title: addDept
	 * @Description: 添加新部门
	 * @param dept
	 * @throws:
	 * @time: 2018年2月7日 上午10:29:52
	 */
	@Insert("insert into t_dept (deptid, deptname, deptenable, createid, createtime, entkbn) "
			+ "values (#{deptid}, #{deptname}, 1, #{createid}, now(), 0)")
	void addDept(Dept dept) throws SQLExecutorException;

	/**
	 * @Title: editDept
	 * @Description: 部门修改
	 * @param dept
	 * @throws:
	 * @time: 2018年2月7日 上午11:13:33
	 */
	@Update("update t_dept set deptname = #{deptname} where deptid = #{deptid}")
	void editDept(Dept dept) throws SQLExecutorException;

	/**
	 * @Title: findEnabledDept
	 * @Description: 获取可用部门列表
	 * @param userid
	 * @throws:
	 * @time: 2018年2月7日 上午11:38:53
	 */
	List<Dept> findEnabledDept(String userid) throws SQLExecutorException;

	/**
	 * @Title: findEnabledDeptByPosition
	 * @Description: 获取相应岗位对应可用部门列表
	 * @param userid
	 * @throws:
	 * @time: 2018年2月7日 上午11:38:53
	 */
	List<Dept> findEnabledDeptByPosition(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: changeDeptStatus
	 * @Description: 部门停、启用
	 * @param dept
	 * @throws:
	 * @time: 2018年2月7日 上午11:56:22
	 */
	@Update("update t_dept set deptenable = #{deptenable} where deptid = #{deptid} and createid = #{createid}")
	void changeDeptStatus(Dept dept) throws SQLExecutorException;

	/**
	 * @Title: changeSalerDept
	 * @Description: 批量转移部门下的业务员至新部门
	 * @param param
	 * @throws:
	 * @time: 2018年2月7日 下午1:47:51
	 */
	@Update("update t_agent_user set deptid = #{newdeptid} where deptid = #{olddeptid} and agentuserid = #{userid}")
	void moveSalerToNewDept(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: delDept
	 * @Description: 删除部门
	 * @param lids
	 * @throws:
	 * @time: 2018年2月7日 下午2:00:46
	 */
	void delDept(Map<String, Object> param) throws SQLExecutorException;
	
	/* *******************业务员********************/
	
	/**
	 * @Title: findSalerCount
	 * @Description: 根据条件获取业务员数量
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午2:20:52
	 */
	int findSalerCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findSaler
	 * @Description: 根据条件获取业务员数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 下午2:55:13
	 */
	List<Saler> findSaler(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: addSalerInfo
	 * @Description: 添加业务员数据
	 * @param userInfo
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月16日 上午10:44:27
	 */
	@Insert("insert into t_userinfo (userid, username, password, realname, telno, registertime, registerstatus, role, createtime, entkbn) "
			+ "values (#{userid}, #{username}, #{password}, #{realname}, #{telno}, now(), 2, #{role}, now(), 0)")
	void addSalerInfo(UserInfo userInfo) throws SQLExecutorException;
	
	/**
	 * @Title: addSalerLevel
	 * @Description: 设置业务员等级
	 * @param le
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月16日 上午11:06:19
	 */
	@Insert("insert into t_level_saler (id, levelid, userid) values (replace(uuid(),'-',''), #{levelid}, #{salerid})")
	void addSalerLevel(Map<String, String> le) throws SQLExecutorException;
	
	/**
	 * @Title: addAgentUser
	 * @Description: 绑定代理商 - 员工关系
	 * @param param
	 * @throws:
	 * @time: 2018年2月7日 下午3:59:58
	 */
	@Insert("insert into t_agent_user(id, agentuserid, deptid, salesmanuserid,positionid) values (#{id}, #{agentuserid}, #{deptid},#{salesmanuserid},#{positionid})")
	void addAgentUser(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: unbindAgentUser
	 * @Description: 解绑代理商 - 员工关系
	 * @param param
	 * @throws:
	 * @time: 2018年2月7日 下午4:42:07
	 */
	void unbindAgentUser(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: unbindSalerUserRelation
	 * @Description: 释放业务员-用户关系
	 * @param ids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月8日 下午4:01:23
	 */
	void unbindSalerUserRelation(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerDeptData
	 * @Description: 获取业务员部门数据
	 * @param param
	 * @throws:
	 * @time: 2018年2月7日 下午5:05:51
	 */
	@Select("select dept.deptid, dept.deptname from t_agent_user au, t_dept dept where "
			+ "au.deptid=dept.deptid and dept.entkbn=0 and au.agentuserid=#{agentid} and au.salesmanuserid=#{salerid}")
	Saler findSalerDeptData(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerPositionData
	 * @Description: 获取业务员岗位数据
	 * @param param
	 * @throws:
	 * @time: 2018年2月7日 下午5:05:51
	 */
	@Select("select position.positionid, position.positionname from t_agent_user au, t_position position where "
			+ "au.positionid=position.positionid and position.entkbn=0 and au.agentuserid=#{agentid} and au.salesmanuserid=#{salerid}")
	Saler findSalerPositionData(Map<String, String> param) throws SQLExecutorException;
	
	/**
	 * @Title: findSalerSubdepts
	 * @Description: 获取业务员已有下属部门列表
	 * @param
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 下午4:10:44
	 */
	@Select("select deptid from t_agentuser_depts where userid=#{salerid}")
	List<String> findSalerSubdepts(String salerid) throws SQLExecutorException;

	/**
	 * @Title: changeSalerDept
	 * @Description: 更改业务员的部门
	 * @param param
	 * @throws:
	 * @time: 2018年2月7日 下午5:41:42
	 */
	@Update("update t_agent_user set deptid = #{newdeptid} where salesmanuserid = #{userid}")
	void changeSalerDept(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: changeSalerPosition
	 * @Description: 更改业务员的岗位
	 * @param param
	 * @throws:
	 * @time: 2018年2月7日 下午5:41:42
	 */
	@Update("update t_agent_user set positionid = #{newpositionid} where salesmanuserid = #{userid}")
	void changeSalerPosition(Map<String, String> param) throws SQLExecutorException;
	
	/**
	 * @Title: delSalerSubdeptdBySalerid
	 * @Description: 根据salerid删除对应的业务员-下属部门
	 * @param salerid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月24日 下午2:14:42
	 */
	@Delete("delete from t_agentuser_depts where userid = #{salerid}")
	void delSalerSubdeptdBySalerid(String salerid) throws SQLExecutorException;

	/**
	 * @Title: findSalerTask
	 * @Description: 根据代理商id获取业务员任务数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月24日 上午11:10:37
	 */
	List<SalerTask> findSalerTask(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: updateSalerInfo
	 * @Description: 修改业务员信息
	 * @param userinfo
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月16日 上午11:28:29
	 */
	@Update("update t_userinfo set password=#{password}, realname=#{realname}, telno=#{telno}, entkbn=#{entkbn}, role=#{role} where userid=#{userid}")
	void updateSalerInfo(UserInfo userinfo) throws SQLExecutorException;

	/* *******************岗位********************/
	/**
	 * @Title: findPositionCount
	 * @Description: 根据条件获取岗位总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午9:51:10
	 */
	@Select("select count(*) from t_position where createid=#{agentid} and entkbn = 0")
	int findPositionCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findPosition
	 * @Description: 根据条件获取岗位数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午10:01:11
	 */
	List<Position> findPosition(Map<String, Object> param) throws SQLExecutorException;	

	/**
	 * @Title: checkPositionName
	 * @Description: 岗位名称重复性检测
	 * @param position
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:07:50
	 */
	@Select("select count(*) from t_position where createid = #{createid} and positionname = #{positionname} and entkbn = 0")
	Boolean checkPositionName(Position position) throws SQLExecutorException;
	
	/**
	 * @Title: addPosition
	 * @Description: 添加岗位
	 * @param position
	 * @throws:
	 * @time: 2018年2月7日 上午10:29:52
	 */
	@Insert("insert into t_position (positionid, positionname, positionenable, createid, createtime, entkbn) "
			+ "values (#{positionid}, #{positionname}, 1, #{createid}, now(), 0)")
	void addPosition(Position position) throws SQLExecutorException;

	/**
	 * @Title: editPosition
	 * @Description: 岗位修改
	 * @param position
	 * @throws:
	 * @time: 2018年2月7日 上午11:13:33
	 */
	@Update("update t_position set positionname = #{positionname} where positionid = #{positionid}")
	void editPosition(Position position) throws SQLExecutorException;
	
	/**
	 * @Title: findPositionnameById
	 * @Description: 根据岗位id获取岗位名称
	 * @param positionid
	 * @throws:
	 * @time: 2018年2月7日 上午11:38:53
	 */
	@Select("select * from t_position where positionid = #{positionid} limit 0,1")
	Position findPositionById(String positionid) throws SQLExecutorException;

	/**
	 * @Title: findEnabledPosition
	 * @Description: 获取可用岗位列表
	 * @param userid
	 * @throws:
	 * @time: 2018年2月7日 上午11:38:53
	 */
	List<Position> findEnabledPosition(String userid) throws SQLExecutorException;

	/**
	 * @Title: changePositionStatus
	 * @Description: 岗位停、启用
	 * @param position
	 * @throws:
	 * @time: 2018年2月7日 上午11:56:22
	 */
	@Update("update t_position set positionenable = #{positionenable} where positionid = #{positionid} and createid = #{createid}")
	void changePositionStatus(Position position) throws SQLExecutorException;

	/**
	 * @Title: delPosition
	 * @Description: 删除岗位
	 * @param param
	 * @throws:
	 * @time: 2018年2月7日 下午2:00:46
	 */
	void delPosition(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findAgentRoles
	 * @Description: 获取代理商权限列表
	 * @param
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 下午4:10:44
	 */
	@Select("select * from t_agent_jurisdiction where flag=1")
	List<Role> findAgentRoles() throws SQLExecutorException;
	
	/**
	 * @Title: addPositionRole
	 * @Description: 代理商权限添加
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午3:43:34
	 */
	void addPositionRole(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findPositionRoles
	 * @Description: 获取岗位已有权限列表
	 * @param
	 * @return
	 * @throws:
	 * @time: 2018年1月16日 下午4:10:44
	 */
	@Select("select juridictionid from t_p_agentj where positionid=#{positionid}")
	List<String> findPositionRoles(String positionid) throws SQLExecutorException;
	
	/**
	 * @Title: cleanPositionRole
	 * @Description: 清空权限
	 * @param positionid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午5:02:01
	 */
	@Delete("delete from t_p_agentj where positionid=#{positionid}")
	void cleanPositionRole(String positionid) throws SQLExecutorException;
	
	/**
	 * @Title: addUserSubdepts
	 * @Description: 员工下属部门添加
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 下午3:43:34
	 */
	void addUserSubdepts(Map<String, Object> param) throws SQLExecutorException;


}