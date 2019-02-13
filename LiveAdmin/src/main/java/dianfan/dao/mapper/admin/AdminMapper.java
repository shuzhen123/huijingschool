package dianfan.dao.mapper.admin;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AdminSelfMapper
 * @Description 管理员dao
 * @author cjy
 * @date 2018年1月9日 下午3:48:14
 */
@Repository
public interface AdminMapper {

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


	
	
}