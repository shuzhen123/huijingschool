package dianfan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.entities.TokenModel;
import dianfan.util.MD5Utils;
import dianfan.util.UUIDUtil;
/**
 * @ClassName TokenService
 * @Description token服务
 * @author cjy
 * @date 2017年12月20日 下午3:35:07
 */
@Service
public class RedisTokenService {
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: createToken
	 * @Description: 创建Token令牌
	 * @param userId 用户id
	 * @return token 
	 * @throws:
	 * @time: 2017年12月20日 下午3:23:40
	 */
	public String createToken(String key) {
		// 使用uuid作为源token
		String token = UUIDUtil.getUUID();
		// 存储到redis并设置过期时间
		redisService.set(key, token);
		return token;
	}
	
	/**
	 * @Title: getToken
	 * @Description: 获取token令牌
	 * @param accesstoken 请求传入的accesstoken
	 * @return
	 * @throws:
	 * @time: 2017年12月20日 下午4:41:54
	 */
	public TokenModel getToken(String accesstoken) {
		if (accesstoken == null || accesstoken.length() == 0) {
			return null;
		}
		String[] param = accesstoken.split("_");
		if (param.length != 2) {
			return null;
		}
		// 使用userId和源token简单拼接成的token，可以增加加密措施
		String userId = param[0];
		String token = param[1];
		return new TokenModel(userId, token);
	}
	
	/**
	 * @Title: checkToken
	 * @Description: 检查token是否有效、有效则延长返回true,无效则返回false
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2017年12月20日 下午5:22:07
	 */
	public boolean checkToken(TokenModel model) {
		if (model == null) {
			return false;
		}
		String token = redisService.get(model.getUserId());
		if (token == null || !token.equals(model.getToken())) {
			return false;
		}
		// 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
//		redisService.set(model.getUserId(), token);
		return true;
	}
}
