package dianfan.mp.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import antlr.collections.List;
import dianfan.service.RedisService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring-context.xml", 
//		"classpath:spring-mvc-test.xml", 
		"classpath:spring-redis.xml"
		})
public class RedisTest {
	@Autowired
	private RedisService redisService;
	
	@Test
	public void hset() {
		redisService.hSet("a", "b", "c");
	}
	@Test
	public void hget() {
		System.out.println(redisService.getExpire("18861528827reg"));
	}
	@Test
	public void hdel() {
		System.out.println(redisService.hDel("a", "b"));
	}
	
	
}
