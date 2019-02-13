package dianfan.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.UserMapper;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.RedisTokenService;

/**
 * @ClassName UserService
 * @Description 业务员信息相关服务
 * @author cjy
 * @date 2018年3月9日 下午4:33:36
 */
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisTokenService tokenService;
	@Autowired
	private RedisService redisService;
	
	/**
	 * @Title: userLogin
	 * @Description: 业务员登录验证
	 * @param info
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月9日 下午4:33:55
	 */
	public ResultBean userLogin(UserInfo info) throws SQLExecutorException {
		info = userMapper.checkLogin(info);
		if(info == null) {
			//登录验证失败
			return new ResultBean("008", ResultMsg.C_001);
		}
		
		if(info.getEntkbn() == 1) {
			//账号被封
			return new ResultBean("002", ResultMsg.C_002);
		}
		
		//验证通过，缓存token
		String token = tokenService.createToken(info.getUserid());
				
		//构建返回实体
		return new ResultBean(info.getUserid() + "_" + token);
	}

	/**
	 * @Title: userLogout
	 * @Description: 业务员登出
	 * @param accesstoken
	 * @throws:
	 * @time: 2018年3月14日 下午4:17:35
	 */
	public void userLogout(String accesstoken) {
		TokenModel model = tokenService.getToken(accesstoken);
		redisService.del(model.getUserId());
	}
}
