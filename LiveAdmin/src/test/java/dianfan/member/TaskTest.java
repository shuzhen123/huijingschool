package dianfan.member;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.entities.BashMap;
import dianfan.exception.SQLExecutorException;
import dianfan.service.SalerTaskTimerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class TaskTest {

	@Autowired
	private SalerTaskTimerService taskTimer;
	
	@Test
	public void salerTaskDistribute() throws SQLExecutorException {
		taskTimer.salerTaskDistribute();
	}
	
}
