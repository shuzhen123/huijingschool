package dianfan.mp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.exception.SQLExecutorException;
import dianfan.service.VCloudNotifyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml",
		// "classpath:spring-mvc-test.xml",
		// "classpath:spring-hexunpay.xml",
		"classpath:spring-redis.xml" })
public class NotifyTest {

	@Autowired
	private VCloudNotifyService vCloudNotifyService;
	
	@Test
	public void updateLiveCourse() throws SQLExecutorException {
		vCloudNotifyService.updateLiveCourse("2c9781cf45634edcbc384395fe3db1c7", "http://vodcvzretw1.vod.126.net/vodcvzretw1/2c9781cf45634edcbc384395fe3db1c7_1524905223336_1524905242314_333611259-00000.mp4");
	}
}
