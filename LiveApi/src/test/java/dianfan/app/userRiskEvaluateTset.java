package dianfan.app;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import dianfan.service.app.AppRiskEvaluateService;
/**
 * @ClassName AppIndexTest
 * @Description 用户风险测评
 * @author sz
 * @date 2018年1月23日 下午3:09:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class userRiskEvaluateTset {
	@Autowired
	private AppRiskEvaluateService appRiskEvaluateService;
	
	//测试：最新动态
	@Test
	public void pay() throws Exception {
		List<String> answerid = new ArrayList<>();
		answerid.add("d0413240504b11e8aeb500163e0028c4");
		answerid.add("d04bf16c504b11e8aeb500163e0028c4");
		answerid.add("d0583792504b11e8aeb500163e0028c4");
		answerid.add("d062c8b3504b11e8aeb500163e0028c4");
		answerid.add("d06dd70e504b11e8aeb500163e0028c4");
		answerid.add("d0770698504b11e8aeb500163e0028c4");
		answerid.add("d0806f0f504b11e8aeb500163e0028c4");
		answerid.add("d0898968504b11e8aeb500163e0028c4");
		answerid.add("d093a068504b11e8aeb500163e0028c4");
		answerid.add("d09dbf09504b11e8aeb500163e0028c4");
		answerid.add("d0a67af4504b11e8aeb500163e0028c4");
		answerid.add("d0af3b6a504b11e8aeb500163e0028c4");
		answerid.add("d0b7afe4504b11e8aeb500163e0028c4");
		answerid.add("d0c044c2504b11e8aeb500163e0028c4");
		answerid.add("d0c8f357504b11e8aeb500163e0028c4");
		answerid.add("d0d1a0da504b11e8aeb500163e0028c4");
		answerid.add("d0da969a504b11e8aeb500163e0028c4");
		answerid.add("d0e3792f504b11e8aeb500163e0028c4");
		answerid.add("d0ec63e8504b11e8aeb500163e0028c4");
		answerid.add("d0fe1645504b11e8aeb500163e0028c4");
		String userid = "01e8abb43fc64b46a4d7ad5e6c765d4d";
		appRiskEvaluateService.userRiskEvaluate(answerid, userid);

	}
	
}
