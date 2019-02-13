package dianfan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class BaseRedisService<K, V> {

	@Autowired
	private RedisTemplate<K, V> redisTemplate;

	/**
	 * @Title: getRedisSerializer
	 * @Description: 获取 RedisSerializer
	 * @return RedisSerializer<String>
	 * @throws:
	 * @time: 2017年12月21日 上午9:10:37
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * @Title: add
	 * @Description: 新增 (不设置过期)
	 * @param Key
	 * @param value
	 * @return boolean
	 * @throws:
	 * @time: 2017年12月21日 上午9:11:10
	 */
	public boolean add(final String Key, final String value) {
		boolean result = false;
		if (Key != null && !"".equals(Key)) {
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(Key);
					byte[] name = serializer.serialize(value);
					return connection.setNX(key, name);
				}
			});
		}
		return result;
	}

	/**
	 * @Title: add
	 * @Description: 批量新增(不设置过期)
	 * @param list
	 *            键值对集合
	 * @return boolean
	 * @throws:
	 * @time: 2017年12月21日 上午9:11:32
	 */
	public boolean add(final List<Map<String, String>> list) {
		boolean result = false;
		if (list != null && list.size() > 0) {
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					for (Map<String, String> map : list) {
						for (Entry<String, String> m : map.entrySet()) {
							byte[] key = serializer.serialize(m.getKey());
							byte[] name = serializer.serialize(m.getValue());
							connection.setNX(key, name);
						}
					}
					return true;
				}
			}, false, true);
		}
		return result;
	}

	/**
	 * @Title: add
	 * @Description: 新增 (设置过期)
	 * @param Key
	 * @param value
	 * @param milliseconds
	 *            秒
	 * @return boolean
	 * @throws:
	 * @time: 2017年12月21日 上午9:12:24
	 */
	public boolean add(final String Key, final String value, final long milliseconds) {
		boolean result = false;
		if (Key != null && !"".equals(Key)) {
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(Key);
					byte[] name = serializer.serialize(value);
					connection.pSetEx(key, milliseconds, name);
					return true;
				}
			});
		}
		return result;
	}

	/**
	 * @Title: add
	 * @Description: 批量新增 -设置过期
	 * @param list
	 * @param milliseconds
	 * @return boolean
	 * @throws:
	 * @time: 2017年12月21日 上午9:13:06
	 */
	public boolean add(final List<Map<String, String>> list, final long milliseconds) {
		boolean result = false;
		if (list != null && list.size() > 0) {
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					for (Map<String, String> map : list) {
						for (Entry<String, String> m : map.entrySet()) {
							byte[] key = serializer.serialize(m.getKey());
							byte[] name = serializer.serialize(m.getValue());
							connection.pSetEx(key, milliseconds, name);
							return true;
						}
					}
					return true;
				}
			}, false, true);
		}
		return result;
	}

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @param key
	 * @throws:
	 * @time: 2017年12月21日 上午9:13:48
	 */
	public void delete(final String key) {
		if (key != null && !"".equals(key)) {
			List<String> list = new ArrayList<String>();
			list.add(key);
			delete(list);
		}
	}

	/**
	 * @Title: delete
	 * @Description: 删除多个
	 * @param keys
	 * @throws:
	 * @time: 2017年12月21日 上午9:13:59
	 */
	public void delete(final List<String> keys) {
		if (keys != null && keys.size() > 0) {
			redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					for (String key : keys) {
						byte[] keyByte = serializer.serialize(key);
						connection.del(keyByte);
					}
					return true;
				}
			}, false, true);
		}
	}

	/**
	 * @Title: update
	 * @Description: 修改
	 * @param Key
	 * @param value
	 * @return
	 * @throws:
	 * @time: 2017年12月21日 上午9:14:17
	 */
	public boolean update(final String Key, final String value) {
		if (get(Key) == null) {
			throw new NullPointerException("数据行不存在, key = " + Key);
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(Key);
				byte[] name = serializer.serialize(value);
				connection.set(key, name);
				return true;
			}
		});
		return result;
	}

	/**
	 * @Title: get
	 * @Description: 通过key获取
	 * @param keyId
	 * @return
	 * @throws:
	 * @time: 2017年12月21日 上午9:14:31
	 */
	public Map<String, String> get(final String keyId) {
		Map<String, String> result = new HashMap<String, String>();
		if (keyId != null && !"".equals(keyId)) {
			result = redisTemplate.execute(new RedisCallback<Map<String, String>>() {
				@Override
				public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					byte[] value = connection.get(key);
					if (value == null) {
						return null;
					}
					String valueS = serializer.deserialize(value);
					Map<String, String> map = new HashMap<String, String>();
					map.put(keyId, valueS);
					return map;
				}
			});
		}
		return result;
	}

	/**
	 * @Title: addByKey
	 * @Description: 一个key设置多个值(不存在新增)
	 * @param Key
	 * @param value
	 * @return
	 * @throws:
	 * @time: 2017年12月21日 上午9:14:43
	 */
	public boolean addByKey(final String Key, final String value) {
		boolean result = false;
		if (Key != null && !"".equals(Key)) {
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(Key);
					byte[] name = serializer.serialize(value);
					boolean flag = false;
					if (connection.exists(key)) {
						connection.lPushX(key, name);
						flag = true;
					} else {
						connection.setNX(key, name);
						flag = true;
					}
					return flag;
				}
			});
		}
		return result;
	}

	/**
	 * @Title: getExpire
	 * @Description: 获取key的剩余时间（秒）
	 * @param keyId
	 * @return
	 * @throws:
	 * @time: 2017年12月21日 上午9:14:55
	 */
	public Long getExpire(final String keyId) {
		Long result = 0L;
		if (keyId != null && !"".equals(keyId)) {
			result = redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					Long ttl = connection.ttl(key);
					return ttl;
				}
			});
		}
		return result;
	}
}