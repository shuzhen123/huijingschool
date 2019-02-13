package dianfan.app;

import java.util.List;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.dao.mapper.app.AppCourseMapper;
import dianfan.dao.mapper.app.AppNewsMapper;
import dianfan.dao.mapper.app.AppTeacherMapper;
import dianfan.entities.AppRecommendCourse;
import dianfan.entities.DynamicNews;
import dianfan.entities.NewsList;
import dianfan.entities.RecommendTeacherInfo;
import dianfan.service.app.AppBannerService;
import dianfan.service.app.AppSmsService;
/**
 * @ClassName AppIndexTest
 * @Description app首页测试
 * @author cjy
 * @date 2018年1月23日 下午3:09:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class AppIndexTest {
	@Autowired
	private AppBannerService bannerService;
	@Autowired
	private AppTeacherMapper appAgentMapper;
	@Autowired
	private AppCourseMapper appCourseMapper;
	@Autowired
	private AppNewsMapper appNewsMapper;
	@Autowired
	private AppSmsService appSmsService;
	
	//测试：最新动态
	@Test
	public void getCode() throws Exception {
		appSmsService.getCode("18861528826", "login");
	}
	
	//测试：最新动态
	@Test
	public void getDynamicNews() throws Exception {
		List<DynamicNews> news = bannerService.getDynamicNews();
		System.out.println(news);
	}
	
	//测试：获取首页名师推荐列表
	@Test
	public void findIndexRecommendTeacher() throws Exception {
		List<RecommendTeacherInfo> teacher = appAgentMapper.findIndexRecommendTeacher();
		System.out.println(teacher);
	}
	
	//测试：获取名师推荐列表
	@Test
	public void findRecommendTeacher() throws Exception {
		TreeSet<RecommendTeacherInfo> teacher = appAgentMapper.findRecommendTeacher();
		System.out.println(teacher);
	}
	
	//测试：获取首页课程列表
	@Test
	public void findCoursesByType() throws Exception {
		List<AppRecommendCourse> list = appCourseMapper.findCoursesByType(1);
		System.out.println(list);
	}
	
	//测试：获取首页课程列表
	@Test
	public void findNewsLimit() throws Exception {
		List<NewsList> list = appNewsMapper.findNewsLimit();
		System.out.println(list);
	}
	
}
