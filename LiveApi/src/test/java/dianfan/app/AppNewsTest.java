package dianfan.app;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.dao.mapper.app.AppNewsMapper;
import dianfan.service.app.AppNewsService;
/**
 * @ClassName AppNewsTest
 * @Description app news
 * @author cjy
 * @date 2018年1月23日 下午3:09:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class AppNewsTest {
	@Autowired
	private AppNewsMapper appNewsMapper;
	@Autowired
	private AppNewsService appNewsService;
	
	//测试：最新动态
	@Test
	public void findNewsList() throws Exception {
		Map<String, Object> findNewsComment = appNewsService.findNewsComment("b5caac0c975243e7b5280ff2e2f503e7", 1);
		System.out.println(findNewsComment);
		
	}
	
	//测试：咨讯评论
	@Test
	public void findNewsComment() throws Exception {
		Map<String, Object> map = appNewsService.findNewsComment("a509345b58734461bb0eb3d8ef0e884a", 1);
		
		System.out.println(map);
	}
	
	
}
