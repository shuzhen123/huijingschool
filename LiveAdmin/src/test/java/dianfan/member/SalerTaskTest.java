package dianfan.member;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentPersonnelService;
import dianfan.service.agent.AgentStatisticsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class SalerTaskTest {

	@Autowired
	private AgentPersonnelService task;
	@Autowired
	private AgentStatisticsService agentStatisticsService;
	
	@Test
	public void salerTaskDistribute() throws SQLExecutorException {
		Map<String, List> map = task.findSalerTaskList("e63d9327f1f211e782df52540054a904", 2018, 2);
		System.out.println(map);
	}
	
	@Test
	public void statistics() throws SQLExecutorException {
//		Map<String, Object> map = agentStatisticsService.wageStatistics("e63d9327f1f211e782df52540054a904", "2018-01-01", "2018-02-31", "129162450baa11e8aab300163e0028c4", "2bbec6900b1911e8aab300163e0028c4");
//		System.out.println(map);
	}
	
}
