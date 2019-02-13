package dianfan.member;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dianfan.dao.mapper.agent.AgentQRMapper;
import dianfan.entities.Popu;
import dianfan.service.agent.AgentQRService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-redis.xml" })
public class UserTest {

	@Autowired
	private AgentQRService AgentQRService;
	@Autowired
	private AgentQRMapper agentQRMapper;
	
	
//	@Test
//	public void icr() throws JsonParseException, JsonMappingException, IOException {
//		List<Popu> list = agentQRMapper.findWxQr("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=null");
//		
//		for(Popu p : list) {
//			String ticket = AgentQRService.createWxQRCode(p.getUserid());
//			p.setQrurl("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket);
//			agentQRMapper.updateQr(p);
//		}
//		
//		
//		System.out.println(list);
//	}
}
