package dianfan.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.vcloud.core.VCloudManage;

/**
 * @ClassName ChannelTest
 * @Description 频道测试
 * @author cjy
 * @date 2018年1月23日 下午3:09:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-redis.xml" })
public class ChannelTest {

	// 测试：最新动态
	@Test
	public void pay() throws Exception {
		
		Map<String, String> param = new HashMap<>();
		
		param.put("accids", "[\"bd40e1e5863845128f7897d675f04d72\"]");
		
		Map<String, Object> im = VCloudManage.im("vcloud.userinfo.get", param );
		System.out.println(im);
	}

}
