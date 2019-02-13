package dianfan.mp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml",
		// "classpath:spring-mvc-test.xml",
		// "classpath:spring-hexunpay.xml",
		"classpath:spring-redis.xml" })
public class NewsTest {

	// 测试：根据条件获取课程信息
	@Test
	public void findNewsList() throws Exception {
	}
	
}















