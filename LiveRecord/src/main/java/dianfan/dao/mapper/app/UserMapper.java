package dianfan.dao.mapper.app;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
@Repository
public interface UserMapper {

	/**
	 * @Title: checkLogin
	 * @Description: 根据账号密码获取业务员信息
	 * @param info
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月9日 下午4:26:42
	 */
	@Select("select * from t_userinfo where username=#{username} and password=#{password} and entkbn != 9")
	UserInfo checkLogin(UserInfo info) throws SQLExecutorException;

}
