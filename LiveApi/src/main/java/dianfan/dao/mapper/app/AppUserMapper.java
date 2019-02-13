package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AppUserMapper
 * @Description 用户相关dao
 * @author cjy
 * @date 2018年3月2日 上午9:24:46
 */
@Repository
public interface AppUserMapper {

	/**
	 * @Title: getUserCountByPhone
	 * @Description: 根据手机号码获取用户数量
	 * @param phone
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午1:41:59
	 */
	@Select("select count(*) from  t_userinfo where telno = #{phone} and role = 1 and entkbn != 9")
	int getUserCountByPhone(String phone) throws SQLExecutorException;

	/**
	 * @Title: checkInviteCode
	 * @Description: 检测邀请码对应业务员信息
	 * @param invitecode
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月11日 下午3:18:52
	 */
	@Select("select userid from t_popu where invitecode = #{invitecode} and entkbn=0 limit 0, 1")
	String checkInviteCode(String invitecode) throws SQLExecutorException;
	
	/**
	 * @Title: checkImportUser
	 * @Description: 
	 * @param telno
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午4:59:42
	 */
	@Select("select * from t_userinfo where telno=#{telno} and role=1 and entkbn=1")
	UserInfo checkImportUser(String telno) throws SQLExecutorException;

	/**
	 * @Title: addUser
	 * @Description: 新增注册用户
	 * @param userInfo
	 *            用户数据
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午1:41:50
	 */
	/*
	 * 新增注册用户 password:密码 telno:电话号码 invitecode:邀请码 registertime:注册时间
	 * registerstatus:注册状态（1：未实名注册2：已实名注册） role:角色【1：用户(客户) 2：后台管理3：业务员4：主播代理商】
	 * createtime:创建时间 entkbn:数据有效区分（0：正常数据1:无效数据9：逻辑删除）
	 */
	@Insert("insert into t_userinfo (userid, password, telno, invitecode, nickname, iconurl, tokenid, registertime, registerstatus, role, entkbn) "
			+ "values (#{userid}, #{password}, #{telno}, #{invitecode}, #{nickname}, #{iconurl}, #{tokenid}, now(), 1, 1, 0 )")
	void addUser(UserInfo userInfo) throws SQLExecutorException;
	
	/**
	 * @Title: updateReregUserInfo
	 * @Description: 导入用户注册行为,更新用户信息
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午5:08:25
	 */
	@Update("update t_userinfo set password=#{password}, invitecode=#{invitecode}, iconurl=#{iconurl}, tokenid=#{tokenid}, registertime=now(), entkbn=0 where userid=#{userid}")
	void updateReregUserInfo(UserInfo info) throws SQLExecutorException;
	
	/**
	 * @Title: getCouponValidityDay
	 * @Description: 获取卡券时长
	 * @param couponid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月26日 下午3:25:12
	 */
	@Select("select validity from t_cashcoupon where id=#{couponid}")
	int getCouponValidityDay(String couponid) throws SQLExecutorException;
	
	/**
	 * @Title: provideCoupon
	 * @Description: 赠送新用户注册代金券
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月26日 下午3:23:08
	 */
	@Insert("insert into t_ccrelation (id, cashid, userid, validity, expire) values "
			+ "(replace(uuid(),'-',''), #{couponid}, #{userid},#{validityDay}, DATE_ADD(DATE_FORMAT(now(),'%Y-%m-%d 00:00:00'),INTERVAL ${validityDay}+1 day))")
	void provideCoupon(@Param(value="couponid") String couponid, 
			@Param(value="userid") String userid, @Param(value="validityDay") int validityDay) throws SQLExecutorException;

	/**
	 * @Title: checkLogin
	 * @Description: 登录认证
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月23日 上午9:30:39
	 */
	@Select("select count(*) from t_userinfo where telno = #{telno} and password = #{password} and role = 1 and entkbn = 0")
	int checkLogin(UserInfo userInfo) throws SQLExecutorException;

	/**
	 * @Title: findUserInfo
	 * @Description: 根据账号获取用户数据
	 * @param userInfo
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午1:41:27
	 */
	@Select("select  " +
			"	user.userid, user.openid, user.riskratingfalg, IFNULL(cust.dtsmuserid, cust.dtagentuserid) agentid " +
			"from  " +
			"	t_userinfo user, t_customer cust " +
			"where  " +
			"	user.userid=cust.userid and user.telno =#{telno} and user.role = 1 and user.entkbn = 0 ")
	UserInfo findUserInfo(UserInfo userInfo) throws SQLExecutorException;

	/**
	 * @Title: getCustomerUserCountByPhone
	 * @Description: 根据手机号码查找意向用户数量
	 * @param phone
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午1:41:16
	 */
	@Select("select count(*) from  t_intention_customer  where telno = #{phone}")
	int getCustomerUserCountByPhone(String phone) throws SQLExecutorException;

	/**
	 * @Title: addCustomerUser
	 * @Description: 保存意向用户数据
	 * @param param（phone,
	 *            realname）
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午1:41:04
	 */
	@Insert("insert into t_intention_customer (id, telno, username, entkbn) values (replace(uuid(),'-',''), #{phone}, #{realname}, 1)")
	void addCustomerUser(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: resetPassword
	 * @Description: 重置密码
	 * @param telno
	 * @param password
	 * @throws:
	 * @time: 2018年1月22日 上午11:22:38
	 */
	@Update("update t_userinfo set password = #{password} where telno = #{telno}")
	void resetPassword(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: findAgentidBySalerid
	 * @Description: 获取业务员的上级代理商id
	 * @param salerid
	 * @throws:
	 * @time: 2018年2月9日 下午5:44:53
	 */
	@Select("select agentuserid from t_agent_user where salesmanuserid = #{salerid}")
	String findAgentidBySalerid(String salerid) throws SQLExecutorException;

	/**
	 * @Title: addCustomerRelation
	 * @Description: 添加用户与业务员关系
	 * @param param
	 * @throws:
	 * @time: 2018年2月9日 下午5:50:57
	 */
	@Insert("insert into t_customer (id, userid, dtagentuserid, dtsmuserid, entkbn) "
			+ "values (replace(uuid(),'-',''), #{userid}, #{agentid}, #{dtsmuserid}, 0)")
	void addCustomerRelation(Map<String, String> param) throws SQLExecutorException;
	
	/**
	 * @Title: updateCustomerRelation
	 * @Description: 更新所属代理商-业务员数据
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午5:22:13
	 */
	@Update("update t_customer set dtagentuserid=#{agentid}, dtsmuserid=#{dtsmuserid} where userid=#{userid}")
	void updateCustomerRelation(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: addUserLevelInfo
	 * @Description: 写入用户分类数据
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月20日 上午9:11:30
	 */
	@Insert("insert into t_csclassify_customer_related (id, customerid, createtime, entkbn) "
			+ "values (replace(uuid(),'-',''), #{userid}, now(), 0)")
	void addUserLevelInfo(String userid) throws SQLExecutorException;

	/**
	 * @Title: saveOpenid
	 * @Description: 保存用户openid
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月17日 下午5:38:02
	 */
	@Update("update t_userinfo set openid=#{openid} where userid=#{userid}")
	void saveOpenid(Map<String, String> data) throws SQLExecutorException;

	/**
	 * @Title: checkUserVipLevel
	 * @Description: 获取用户的VIP等级列表
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月5日 上午10:52:46
	 */
	@Select("select viplevelid from t_user_viplevel_time where userid=#{userid} and endtime >= now()")
	List<String> getUserVipLevel(String userid) throws SQLExecutorException;

	/**
	 * @Title: findUserInfoById
	 * @Description: 根据用户id获取用户信息
	 * @param userId
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月27日 下午7:24:54
	 */
	@Select("select * from t_userinfo where userid=#{userId}")
	UserInfo findUserInfoById(String userId) throws SQLExecutorException;

	
}
