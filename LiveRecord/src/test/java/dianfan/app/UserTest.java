package dianfan.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.service.app.UserService;
/**
 * @ClassName AppIndexTest
 * @Description app首页测试
 * @author cjy
 * @date 2018年1月23日 下午3:09:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class UserTest {
	@Autowired
	private UserService userService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	//测试：最新动态
	@Test
	public void userLogin() throws Exception {
		UserInfo info = new UserInfo();
		info.setUsername("saler");
		info.setPassword("123456");
		ResultBean login = userService.userLogin(info);
		System.out.println(login);
	}
	
	//测试：多线程
	@Test
	public void threadTest() throws Exception {
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i<10000; i++) {
					System.out.println("thread : "+i);
				}
			}
		});
		
		
	}
	
	
}
