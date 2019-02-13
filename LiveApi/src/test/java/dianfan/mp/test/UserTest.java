package dianfan.mp.test;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.dao.mapper.app.AppUserMapper;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.nimsdk.YunpianSms;
import dianfan.service.app.AppUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml",
		// "classpath:spring-mvc-test.xml",
		"classpath:spring-redis.xml" })
public class UserTest {

	@Autowired
	private AppUserService userService;
	@Autowired
	private AppUserMapper userMapper;

	// 测试：根据手机号码获取人数
	@Test
	public void userServiceTest() throws Exception {
		int count = userMapper.getUserCountByPhone("18861528829");
		System.out.println(count);
	}

	// 测试：注册短信发送
	@Test
	public void sendSmsCodeTest() throws Exception {
		String[] data = { "112233" };
		Map map = YunpianSms.getInstance().sendSignleSms("18861528826", data);
		System.out.println(map);
	}

	// 测试：微信用户注册
	@Test
	public void registerTest() throws SQLExecutorException {
	}

	// 测试：微信用户密码登录
	@Test
	public void loginByPasswdTest() throws SQLExecutorException {
		ResultBean result = userService.userLogin("18861528826", "123456");
		System.out.println(result);
	}

	// 测试：微信用户密码登录--获取登录验证码
	@Test
	public void getLoginCodeTest() {
	}

	// 测试：微信用户密码登录
	@Test
	public void loginBySmsCodeTest() throws SQLExecutorException {
		ResultBean result = userService.userLogin("agent", "123456");
		System.out.println(result);
	}
}
