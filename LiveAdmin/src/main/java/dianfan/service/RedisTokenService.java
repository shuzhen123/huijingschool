package dianfan.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.propertymanager.PropertyUtil;
import dianfan.constant.ConstantIF;
import dianfan.entities.TokenModel;
import dianfan.util.HttpClientHelper;
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
	public String createToken(String userId) {
		// 使用uuid作为源token
		String token = UUID.randomUUID().toString().replace("-", "");
		// 存储到redis并设置过期时间
		redisService.set(userId, token, ConstantIF.TOKEN_EXPIRES_HOUR);
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
		redisService.set(model.getUserId(), token, ConstantIF.TOKEN_EXPIRES_HOUR);
		return true;
	}

	/**
	 * 删除token
	 * 
	 * @param userId
	 */
	public void deleteToken(String userId) {
		redisService.del(userId);
	}
	
	/**
	 * @Title: getWxAccessToken
	 * @Description: 获取微信access_token
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws:
	 * @time: 2018年3月29日 下午3:43:07
	 */
	public String getWxAccessToken() throws JsonParseException, JsonMappingException, IOException {
		//获取微信access_token
		String access_token = redisService.get(ConstantIF.WX_ACCESS_TOKEN);
		if(access_token == null) {
			access_token = HttpClientHelper.sendGet(PropertyUtil.getProperty("access_token_url"), null , "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			Map token = mapper.readValue(access_token, Map.class);
			access_token = (String) token.get("access_token");
			//缓存微信access_token
			redisService.set(ConstantIF.WX_ACCESS_TOKEN, access_token, ConstantIF.WX_CACHE_TIME);
		}
		return access_token;
	}

	/**
	 * @Title: getWxJsapiTicketToken
	 * @Description: 获取jsapi_ticket
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws:
	 * @time: 2018年3月29日 下午3:45:53
	 */
	public String getWxJsapiTicketToken() throws JsonParseException, JsonMappingException, IOException {
		String jsapi_ticket = redisService.get(ConstantIF.WX_JSAPI_TICKET);
		if(jsapi_ticket == null) {
			//获取jsapi_ticket
			String access_token = getWxAccessToken();
			jsapi_ticket = HttpClientHelper.sendGet(PropertyUtil.getProperty("jsapi_ticket_url") + access_token, null , "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			Map token = mapper.readValue(jsapi_ticket, Map.class);
			jsapi_ticket = (String) token.get("ticket");
			//缓存微信jsapi_ticket
			redisService.set(ConstantIF.WX_JSAPI_TICKET, jsapi_ticket, ConstantIF.WX_CACHE_TIME);
		}
		return jsapi_ticket;
	}
}
