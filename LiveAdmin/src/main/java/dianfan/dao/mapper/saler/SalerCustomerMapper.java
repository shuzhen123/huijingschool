package dianfan.dao.mapper.saler;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.SalerLevel;
import dianfan.entities.TrackRecord;
import dianfan.entities.UserAnswerList;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName SalerCustomerMapper
 * @Description 业务员客户dao
 * @author cjy
 * @date 2018年4月20日 上午10:37:20
 */
@Repository
public interface SalerCustomerMapper {
	
	/**
	 * @Title: findUnbindCustomerCount
	 * @Description: 获取业务员的代理商下导入的用户数量
	 * @param salerid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午2:58:38
	 */
	@Select("select count(*) " +
			"from (" +
			"  select userid from t_customer where dtagentuserid=(" +
			"    select agentuserid from t_agent_user where salesmanuserid=#{salerid}" +
			"  )and dtsmuserid is null" +
			")d, t_userinfo user " +
			"where d.userid=user.userid and user.role=1 and user.entkbn=1")
	int findUnbindCustomerCount(String salerid) throws SQLExecutorException;
	
	/**
	 * @Title: findUnbindCustomer
	 * @Description: 获取业务员的代理商下导入的用户数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午2:59:55
	 */
	@Select("select user.userid, user.telno, user.nickname, user.realname, user.sex, user.prov, user.restype, user.createtime " +
			"from (" +
			"  select userid from t_customer where dtagentuserid=(" +
			"    select agentuserid from t_agent_user where salesmanuserid=#{salerid}" +
			"  )and dtsmuserid is null" +
			")d, t_userinfo user " +
			"where d.userid=user.userid and user.role=1 and user.entkbn=1 limit #{start}, #{length}")
	List<UserInfo> findUnbindCustomer(Map<String, Object> param) throws SQLExecutorException;
	
	
	
	
	
	
	

	/**
	 * @Title: getSalerCollectCount
	 * @Description: 获取业务员拾取数量
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月14日 下午2:35:14
	 */
	@Select("select levels.xcount, levels.ycount, levels.zcount from t_level_saler lesa, t_level levels  " +
			"where lesa.levelid=levels.id and lesa.userid=#{salerid}")
	SalerLevel getSalerCollectCount(String salerid) throws SQLExecutorException;
	
	/**
	 * @Title: getCollectCountToday
	 * @Description: 获取今日已拾取数量
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月14日 下午2:26:13
	 */
	@Select("select  "+ 
			"	user.restype, count(user.restype) count "+ 
			"from  "+ 
			"	t_customer cust, t_userinfo user "+ 
			"where  "+ 
			"cust.userid=user.userid and cust.dtsmuserid=#{salerid} and  "+ 
			"cust.updatetime between date_format(now(),'%Y-%m-%d 00:00:00') and now() "+ 
			"group by user.restype ")
	List<Map<String, Integer>> getCollectCountToday(String salerid) throws SQLExecutorException;
			
	
	
	
	
	
	
	/**
	 * @Title: collectCustomer
	 * @Description: 批量拾取
	 * @param param
	 * @throws:
	 * @time: 2018年2月8日 下午3:39:52
	 */
	void collectCustomer(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findCustomerUserCount
	 * @Description: 根据业务员条件获取用户总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月20日 上午10:39:50
	 */
	int findCustomerUserCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCustomerUser
	 * @Description: 根据业务员条件获取用户数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月20日 上午10:41:51
	 */
	List<UserInfo> findCustomerUser(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findUserInfoById
	 * @Description: 根据用户id获取用户信息
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月19日 下午5:22:52
	 */
	UserInfo findUserInfoById(String userid) throws SQLExecutorException;

	/**
	 * @Title: findUserLevels
	 * @Description: 获取用户等级列表
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月19日 下午5:26:38
	 */
	@Select("select id, name from t_csclassify")
	List<BashMap> findUserLevels() throws SQLExecutorException;

	/**
	 * @Title: updateUserInfo
	 * @Description: 更新用户信息
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月19日 下午5:45:34
	 */
	void updateUserInfo(UserInfo info) throws SQLExecutorException;

	/**
	 * @Title: delUserLevel
	 * @Description: 删除用户等级
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月19日 下午7:21:24
	 */
	@Update("update t_csclassify_customer_related set csclassifyid=#{levelid}, allcontent=#{remark} where customerid=#{userid}")
	void updateUserLevel(UserInfo info) throws SQLExecutorException;

	/**
	 * @Title: checkRegPhone
	 * @Description: 检测手机号码是否已注册
	 * @param telno
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月11日 下午5:24:17
	 */
	@Select("select count(*) from t_userinfo where telno=#{telno} and role=1 and entkbn <> 9")
	boolean checkRegPhone(String telno) throws SQLExecutorException;
	
	/**
	 * @Title: addUserInfo
	 * @Description: 添加用户信息
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月11日 下午3:25:46
	 */
	@Insert("insert into t_userinfo " + 
			"(userid, password, realname, telno, sex, prov, restype, tokenid, iconurl, nickname, registertime, registerstatus, role, riskratingfalg, createtime, createid, entkbn) values " + 
			"(#{userid}, #{password}, #{realname}, #{telno}, #{sex}, #{prov}, #{restype}, #{tokenid}, #{iconurl}, #{nickname}, now(), 1, 1, 0, now(), #{createid}, 0)")
	void addUserInfo(UserInfo info) throws SQLExecutorException;

	/**
	 * @Title: getAgentBySalerid
	 * @Description: 
	 * @param createid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月11日 下午5:06:30
	 */
	@Select("select agentuserid from t_agent_user where salesmanuserid=#{salerid}")
	String getAgentBySalerid(String salerid) throws SQLExecutorException;

	/**
	 * @Title: addAgentUserRelation
	 * @Description: 添加代理商-用户关系
	 * @param userid
	 * @param agentid
	 * @param createid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月11日 下午5:08:59
	 */
	@Insert("insert into t_customer (id, userid, dtagentuserid, dtsmuserid, entkbn) values (replace(uuid(),'-',''), #{userid}, #{agentid}, #{salerid}, 0)")
	void addAgentUserRelation(@Param(value="userid") String userid, @Param(value="agentid") String agentid, @Param(value="salerid") String salerid) throws SQLExecutorException;

	/**
	 * @Title: addUserLevelRelation
	 * @Description: 添加用户等级关系
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月11日 下午5:14:24
	 */
	@Insert("insert into t_csclassify_customer_related (id, csclassifyid, allcontent, customerid, createid, createtime, entkbn) "
			+ "values (replace(uuid(),'-',''), #{levelid}, #{remark}, #{userid}, #{createid}, now(), 0)")
	void addUserLevelRelation(UserInfo info) throws SQLExecutorException;

	/**
	 * @Title: userBatchDel
	 * @Description: 根据id批量丢弃用户
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月14日 上午9:44:45
	 */
	void userBatchDel(List<String> lids) throws SQLExecutorException;
	
	/**
	 * @Title: fildUserResultById
	 * @Description: 查询用户测评数据
	 * @param userid
	 * 			用户的id
	 * @return UserAnswerList
	 * @author: sz
	 * @date 2018年5月15日 下午7:09:08
	 */
	List<UserAnswerList> fildUserResultById(String userid) throws SQLExecutorException;

	/**
	 * @Title: fildAllScore
	 * @Description: 获取用的总分
	 * @param userid
	 * @return
	 * @author: sz
	 * @date 2018年5月16日 上午10:53:09
	 */
	@Select("select sum(score) from t_evaluate_user_answer where userid = #{userid} and entkbn = 0")
	int fildAllScore(String userid) throws SQLExecutorException;

	/**
	 * @Title: addTrackRecord 
	 * @Description: 添加客户跟踪记录
	 * @param record
	 * 			客户跟踪记录pojo
	 * @author: sz
	 * @date 2018年5月16日 下午4:55:16
	 */
	@Insert("insert into t_track_record (id, salerid, userid, record, createtime) " +
			"values (replace(uuid(),'-',''), #{salerid}, #{userid}, #{record}, now())")
	void addTrackRecord(TrackRecord record) throws SQLExecutorException;

	/**
	 * @Title: trackRecordCount
	 * @Description: 查询用户对应的跟踪记录条数
	 * @param userid
	 * 			用户 id
	 * @return int
	 * @author: sz
	 * @date 2018年5月16日 下午6:07:50
	 */
	@Select("select count(*) from t_track_record where userid = #{userid} and entkbn = 0")
	int trackRecordCount(String userid) throws SQLExecutorException;

	/**
	 * @Title: fildtRackRecord
	 * @Description: 查询客户对应的跟踪数据
	 * @param userid
	 * 				用户的id
	 * @return TrackRecord
	 * @author: sz
	 * @date 2018年5月16日 下午6:12:36
	 */
	@Select("select saler.userid, saler.realname salername, re.record, re.createtime " +
			"from t_userinfo saler, t_track_record re " +
			"where saler.userid = re.salerid and re.userid=#{userid} and re.entkbn = 0 order by re.createtime desc ")
	List<TrackRecord> fildtRackRecord(String userid) throws SQLExecutorException;
	
	

}