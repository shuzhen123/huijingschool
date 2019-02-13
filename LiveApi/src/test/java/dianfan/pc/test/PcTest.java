package dianfan.pc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.entities.UserInfo;
import dianfan.service.pc.PcUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class PcTest {
	@Autowired
	private PcUserService pcUserService;
	
	//测试：pc用户登录验证
	@Test
	public void userLogin() throws Exception {
		UserInfo userInfo = pcUserService.userLogin("agent", "123456");
		System.out.println(userInfo);
	}
	
	//测试：更改房间密码
	@Test
	public void changeLiveRoomPassword() throws Exception {
		pcUserService.changeLiveRoomPassword("e63d9327f1f211e782df52540054a904", "", "");
	}
	
	//测试：录播设置
	@Test
	public void changeTranscribeStatus() throws Exception {
		pcUserService.changeTranscribeStatus(null, "y");
	}
	
}
