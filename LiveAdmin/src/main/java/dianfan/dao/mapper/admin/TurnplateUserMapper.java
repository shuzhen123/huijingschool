package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName TurnplateUserMapper
 * @Description 大转盘提交用户dao
 * @author cjy
 * @date 2018年5月3日 上午10:57:27
 */
@Repository
public interface TurnplateUserMapper {
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
	 * @Title: findTurnplateUserCount
	 * @Description: 大转盘提交用户数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 上午11:12:20
	 */
	@Select("select count(*) from t_intention_customer where entkbn=0")
	int findTurnplateUserCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findTurnplateUsers
	 * @Description: 大转盘提交用户数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 上午11:12:39
	 */
	@Select("select  " +
			"  incu.telno, incu.username realname, user.userid " +
			"from  " +
			"  t_intention_customer incu left join ( " +
			"    select  " +
			"      user.telno, user.userid " +
			"    from  " +
			"      t_intention_customer incu left join t_userinfo user on incu.telno=user.telno " +
			"    where  " +
			"      incu.entkbn=0 and user.role=1 " +
			"  ) user on incu.telno=user.telno " +
			"where  " +
			"  incu.entkbn=0 limit #{start}, #{length}")
	List<UserInfo> findTurnplateUsers(Map<String, Object> param) throws SQLExecutorException;
}
