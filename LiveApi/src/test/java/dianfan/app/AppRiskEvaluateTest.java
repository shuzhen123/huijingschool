package dianfan.app;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.dao.mapper.app.AppRiskEvaluateMapper;
import dianfan.entities.EvaluateClass;
/**
 * 
 * @ClassName: AppRiskEvaluateTest
 * @Description: 风险评测测试类
 * @author sz
 * @time 2018年5月7日 上午10:48:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class AppRiskEvaluateTest {
	@Autowired
	private AppRiskEvaluateMapper appRiskEvaluateMapper;
	
	//测试：最新动态
	@Test
	public void getCode() throws Exception {
		List<EvaluateClass> list = appRiskEvaluateMapper.getRiskEvaluateQuestion();
		System.out.println(list);
	}
	
	
}
