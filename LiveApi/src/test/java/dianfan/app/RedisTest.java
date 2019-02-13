package dianfan.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.service.RedisService;
import dianfan.service.RedisTokenService;
import dianfan.util.HttpClientHelper;

/**
 * @ClassName AppIndexTest
 * @Description app首页测试
 * @author cjy
 * @date 2018年1月23日 下午3:09:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-redis.xml" })
public class RedisTest {
	@Autowired
	private RedisService<?, ?> redisService;
	@Autowired
	private RedisTokenService redisTokenService;

	@Test
	public void sAddOne() throws Exception {
		Long sadd = redisService.Sadd("sadd", "abc");
		System.out.println(sadd);
	}

	@Test
	public void sAddMore() throws Exception {
		List<String> values = new ArrayList<>();
		values.add("a");
		values.add("b");
		values.add("c");
		redisService.del("sadd");
		Long sadd = redisService.Sadd("sadd", values);
		System.out.println(sadd);
	}

	@Test
	public void smembers() throws Exception {
		List list = redisService.Smembers("sadda");
		System.out.println(list);
	}

	@Test
	public void sismember() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("这里有独家的大盘解析，大数据量化精选，A股全交易时段名师直播解盘，专家在线诊股。价值投资股市易经系列，集合竞价抓涨停板绝技。").append("\n\n");
		sb.append("新用户注册大礼包免费领取！").append("\n\n");
		sb.append("↓").append("\n\n");
		sb.append("点击后跳转到<a href='http://mp.huijingschool.com/#/zhuce'>注册会员</a>页面");
		//普通关注
		
		String message = makeTextCustomMessage("oRxPxwkEVNENe5sbEgBlho6FNFp0", sb.toString());
		
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
		String token = redisTokenService.getWxAccessToken(); 
		
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
	}

	public String makeTextCustomMessage(String openId, String content) {
		// 对消息内容中的双引号进行转义
		content = content.replace("\"", "\\\"");
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
		return String.format(jsonMsg, openId, content);
	}
	
}
