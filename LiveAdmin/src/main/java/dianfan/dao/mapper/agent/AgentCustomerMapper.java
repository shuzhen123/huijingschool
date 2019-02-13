package dianfan.dao.mapper.agent;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.AgentCustomer;
import dianfan.entities.Saler;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AgentIndexMapper
 * @Description 代理商维护用户分流dao
 * @author cjy
 * @date 2018年2月6日 上午9:26:37
 */
@Repository
public interface AgentCustomerMapper {

	/* ***************************客户资源池*****************************/
	
	/**
	 * @Title: findUnbindCustomerCount
	 * @Description: 获取代理商资源池用户数量
	 * @param agentid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月28日 下午5:59:57
	 */
	@Select("select count(*) from t_customer cust, t_userinfo u " +
			"where cust.userid=u.userid and cust.dtagentuserid=#{agentid} and cust.dtsmuserid is null and u.entkbn=1")
	int findUnbindCustomerCount(String agentid) throws SQLExecutorException;

	/**
	 * @Title: findUnbindCustomer
	 * @Description: 获取代理商资源池用户数据
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月8日 下午2:33:59
	 */
	List<UserInfo> findUnbindCustomer(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: checkRegisterTelno
	 * @Description: 查找telnos中已被注册的手机号码
	 * @param telnos
	 * @throws:
	 * @time: 2018年5月2日 上午11:35:17
	 */
	List<String> checkRegisterTelno(List<String> telnos) throws SQLExecutorException;

	/**
	 * @Title: telnoBatchImport
	 * @Description: 批量插入号码
	 * @param telnos
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午12:10:55
	 */
	void telnoBatchImport(List<UserInfo> telnos) throws SQLExecutorException;

	
	/**
	 * @Title: createAgentUserRelation
	 * @Description: 建立用户与代理商关系
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午1:16:06
	 */
	void createAgentUserRelation(List<UserInfo> data) throws SQLExecutorException;

	/**
	 * @Title: createUserClassRelation
	 * @Description: 建立用户等级关系
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午4:21:01
	 */
	void createUserClassRelation(List<UserInfo> data) throws SQLExecutorException;
	
	/**
	 * @Title: changeUserRestype
	 * @Description: 更改用户资源类型
	 * @param userid
	 * @param restype
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月14日 上午11:06:07
	 */
	@Update("update t_userinfo set restype=#{restype} where userid=#{userid}")
	void changeUserRestype(@Param(value="userid") String userid, @Param(value="restype") String restype) throws SQLExecutorException;
	
	/* ***************************客户管理*****************************/
	
	/**
	 * @Title: findCustomerUserCount
	 * @Description: 根据条件获取代理商下用户总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月6日 下午5:21:40
	 */
	int findCustomerUserCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCustomerUser
	 * @Description: 根据条件获取代理商下用户数据
	 * @param param
	 * @throws:
	 * @time: 2018年2月6日 下午5:25:46
	 */
	List<AgentCustomer> findCustomerUser(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCustomerUserCount_Position
	 * @Description: 根据条件获取代理商下用户总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月6日 下午5:21:40
	 */
	int findCustomerUserCount_Position(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCustomerUser_Position
	 * @Description: 根据条件获取代理商下用户数据
	 * @param param
	 * @throws:
	 * @time: 2018年2月6日 下午5:25:46
	 */
	List<AgentCustomer> findCustomerUser_Position(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: checkAgentCustomer
	 * @Description: 检测业务员与用户关系重复数据
	 * @param lids
	 * @throws:
	 * @time: 2018年2月8日 下午3:22:21
	 */
	List<String> checkAgentCustomer(List<String> ids) throws SQLExecutorException;

	/**
	 * @Title: moveCustomer
	 * @Description: 批量转移用户资源
	 * @param param
	 * @throws:
	 * @time: 2018年2月8日 下午4:44:10
	 */
	void moveCustomer(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findSalerByDeptid
	 * @Description: 根据部门id获取员工
	 * @param param
	 * @throws:
	 * @time: 2018年2月26日 上午11:42:06
	 */
	List<Saler> findSalerByDeptid(Map<String, String> param) throws SQLExecutorException;

}