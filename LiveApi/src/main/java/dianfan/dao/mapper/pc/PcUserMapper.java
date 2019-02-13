package dianfan.dao.mapper.pc;

import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.ChannelBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName UserMapper
 * @Description pc用户相关信息类dao
 * @author cjy
 * @date 2017年12月20日 上午10:18:41
 */
@Repository
public interface PcUserMapper {
	
	/**
	 * @Title: findUserInfo
	 * @Description: pc端登录
	 * @param phone
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月15日 下午4:58:57
	 */
	@Select("select * from t_userinfo where username = #{username} and password = #{password} and role = 5 and entkbn = 0")
	UserInfo checkLogin(UserInfo userInfo) throws SQLExecutorException;

	/**
	 * @Title: updateRoomPasswordByUserid
	 * @Description: 更改房间密码
	 * @param param
	 * @throws:
	 * @time: 2018年1月23日 上午10:21:20
	 */
	@Update("update t_userinfo set cidpassword = #{cidpassword}, liveuploadflag = #{liveuploadflag} where userid = #{userid}")
	void updateRoomPasswordByUserid(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: getTeacherCid
	 * @Description: 根据讲师id获取直播频道信息
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月24日 下午2:09:26
	 */
	@Select("select * from t_channel where accid=#{userid}")
	ChannelBean getTeacherChannelInfo(String userid) throws SQLExecutorException;

}
