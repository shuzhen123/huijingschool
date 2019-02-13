package dianfan.app;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.entities.CourseCondition;
import dianfan.service.VCloudNotifyService;
import dianfan.service.app.AppCourseService;
/**
 * @ClassName AppIndexTest
 * @Description 支付测试
 * @author cjy
 * @date 2018年1月23日 下午3:09:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class CourseTest {
	@Autowired
	private AppCourseService appCourseService;
	@Autowired
	private VCloudNotifyService vCloudNotifyService;
	
	//测试：最新动态
	@Test
	public void pay() throws Exception {
		CourseCondition param = new CourseCondition();
		param.setCoursetype("3");
		param.setPage(1);
		param.setOffset(10);
		param.setPeopleunm("desc");
		param.setEvaluate("asc");
		appCourseService.getCourseList(param);
	}
	
	@Test
	public void updateFromVideoUpload() throws Exception {
		/*
		 * type=upload, 
			vid=1576564818, 
			name=test.mp4, 
			origAddr=http://vodcvzretw1.vod.126.net/vodcvzretw1/844a07e5-9018-4641-8a14-b912af83c073.flv
		 */
		Map<String, Object> map = new HashMap<>();
		map.put("vid", 1577602595);
		map.put("origAddr", "http://vodcvzretw1.vod.126.net/vodcvzretw1/9c33534b-70f7-41dd-8268-0269af318d54.flv");
		vCloudNotifyService.updateFromVideoUpload(map);
	}
	
	@Test
	public void updateFromVideoTranscode() throws Exception {
		/*
		 * vid=1572912353, 
			type=transcode, 
			shdMp4Addr=http://vodcvzretw1.vod.126.net/vodcvzretw1/JOtqwjtJ_1572912353_shd.mp4, 
			shdFlvAddr=http://vodcvzretw1.vod.126.net/vodcvzretw1/ZTxZNoNB_1572912353_shd.flv, 
			shdHlsAddr=http://vodcvzretw1.vod.126.net/vodcvzretw1/uR3grrMk_1572912353_shd.m3u8, 
			name=娉㈡氮鐞嗚.flv
		 */
		Map<String, Object> map = new HashMap<>();
		map.put("vid", "1576564818");
		map.put("shdMp4Addr", "http://vodcvzretw1.vod.126.net/vodcvzretw1/JOtqwjtJ_1572912353_shd.mp4");
		vCloudNotifyService.updateFromVideoTranscode(map);
	}
	
}
