package dianfan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
/**
 * @ClassName TaskMapper
 * @Description 任务调度相关dao
 * @author cjy
 * @date 2018年2月23日 上午11:43:45
 */
@Repository
public interface UserMapper {

	@Select("select userid, nickname, iconurl, sex from t_userinfo where role=1 and entkbn=0")
	List<UserInfo> users();
	
	@Update("update t_userinfo set tokenid=#{token} where userid=#{userid}")
	void update(@Param(value="userid") String userid, @Param(value="token") String token);
}
