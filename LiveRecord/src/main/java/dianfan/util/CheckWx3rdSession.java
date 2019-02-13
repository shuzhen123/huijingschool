package dianfan.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import dianfan.service.BaseRedisService;

@Scope("prototype")
@Component
public class CheckWx3rdSession {
	@Autowired
	private static BaseRedisService RedisService;

	/**
	 * 检查3rdSession存在与否
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public static boolean check3rdSession(String uid) throws Exception {
		Map<String, String> map = null;
		if (uid != null) {
			map = RedisService.get(uid);
		}
		if (map != null && map.size() > 0) {
			return true;
		}
		return false;
	}
}
