package dianfan.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.agent.AgentQRService;
import dianfan.util.HttpClientHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class WxTest {
	@Autowired
	private RedisService redisService;
	@Autowired
	private AgentQRService agentQRService;
	
	@Test
	public void createWxQRCode() throws SQLExecutorException, IOException {
		String string = agentQRService.createWxQRCode("04e18e6a9e9e4a15bf53a06a332bb77b");
		System.out.println(string);
	}
}
