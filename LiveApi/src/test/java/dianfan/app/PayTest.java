package dianfan.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.entities.CourseOrder;
import dianfan.service.PayNotifyService;

/**
 * @ClassName AppIndexTest
 * @Description 支付测试
 * @author cjy
 * @date 2018年1月23日 下午3:09:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-redis.xml" })
public class PayTest {
	@Autowired
	private PayNotifyService payNotifyService;

	// 测试：最新动态
	@Test
	public void pay() throws Exception {
		CourseOrder order = payNotifyService.updateCourseOrder("eeab130d49fb11e8aeb500163e0028c4");
		payNotifyService.updateUserVipLevelInfo(order);
	}

}
