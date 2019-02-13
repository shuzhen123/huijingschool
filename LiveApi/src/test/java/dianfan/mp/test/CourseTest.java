package dianfan.mp.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.entities.CourseCondition;
import dianfan.entities.IndexCourseBean;
import dianfan.service.app.AppCourseService;
import dianfan.service.mp.MpIndexService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml",
		// "classpath:spring-mvc-test.xml",
		// "classpath:spring-hexunpay.xml",
		"classpath:spring-redis.xml" })
public class CourseTest {

	@Autowired
	private AppCourseService courseService;
	@Autowired
	private MpIndexService mpIndexService;

	// 测试：根据条件获取课程信息
	@Test
	public void getCourseList() throws Exception {
		CourseCondition param = new CourseCondition();
		param.setCoursetype("3");
		param.setPage(1);
		
		Map<String, Object> list = courseService.getCourseList(param);
		System.out.println(list);
	}
	
	@Test
	public void getCountByParam() throws Exception {
		CourseCondition courseList_param = new CourseCondition();
		courseList_param.setCoursetype("3");
		courseList_param.setPage(1);
		courseList_param.setOffset(10);
		Map<String, Object> list = courseService.getCourseList(courseList_param);
		System.out.println(list);
	}
	
	// 测试：首页课程分类推荐列表
	@Test
	public void findCourseListByType() throws Exception {
		List<IndexCourseBean> list = mpIndexService.findCourseListByType();
		System.out.println(list);
	}
}















