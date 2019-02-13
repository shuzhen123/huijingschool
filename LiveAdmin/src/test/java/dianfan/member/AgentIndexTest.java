package dianfan.member;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.dao.mapper.agent.AgentCustomerMapper;
import dianfan.dao.mapper.agent.AgentQRMapper;
import dianfan.entities.ChartsBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentIndexService;
import dianfan.service.agent.AgentTeacherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class AgentIndexTest {

	@Autowired
	private AgentIndexService agentIndexService;
	@Autowired
	private AgentCustomerMapper agentCustomerMapper;
	@Autowired
	private AgentQRMapper agentQRMapper;
	@Autowired
	private AgentTeacherService agentTeacherService;
	
	@Test
	public void getEarningsData() throws SQLExecutorException, ParseException {
		LinkedList<ChartsBean> earningsData = agentIndexService.getEarningsData(2018, 1, "");
		System.out.println(earningsData);
	}
	
	@Test
	public void checkAgentCustomer() throws SQLExecutorException {
		List<String> ids = new ArrayList<>();
		ids.add("b24f623b0ae711e8aab300163e0028c4");
		
		
		ids.add("b2e28b800ae711e8aab300163e0028c4");
		ids.add("b38427fa0ae711e8aab300163e0028c4");
		ids.add("357616390ae511e8aab300163e0028c4");
		
		ids.add("b0fb13d10ae711e8aab300163e0028c4");
		List<String> list = agentCustomerMapper.checkAgentCustomer(ids );
		System.out.println(list);
	}
}
