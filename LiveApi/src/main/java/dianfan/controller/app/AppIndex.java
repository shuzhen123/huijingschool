package dianfan.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.DynamicNews;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.app.AppBannerService;

/**
 * @ClassName AppIndex
 * @Description app首页
 * @author cjy
 * @date 2018年1月23日 下午1:52:06
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppIndex {
	@Autowired
	private AppBannerService bannerService;
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: banner
	 * @Description: app banner 展示图
	 * @return
	 * @throws:
	 * @time: 2018年1月23日 下午2:18:11
	 */
	@LogOp(method = "userLogin", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "banner展示图")
	@ApiOperation(value = "banner展示图", httpMethod = "GET", notes = "banner展示图", response = ResultBean.class)
	@RequestMapping(value = "banner", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean banner() {
		return null;
	}

	/**
	 * @Title: dynamicNews
	 * @Description: 最新动态
	 * @return
	 * @throws:
	 * @time: 2018年1月23日 下午2:27:05
	 */
	@LogOp(method = "dynamicNews", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "最新动态")
	@ApiOperation(value = "最新动态", httpMethod = "GET", notes = "最新动态", response = ResultBean.class)
	@RequestMapping(value = "dynamicnews", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean dynamicNews() {
		try {
			// 最新动态
			List<DynamicNews> news = bannerService.getDynamicNews();
			return new ResultBean(news);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: indexLiveLogo
	 * @Description: 精彩推荐logo
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 下午2:18:56
	 */
	@LogOp(method = "indexLiveLogo", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "精彩推荐logo")
	@ApiOperation(value = "精彩推荐logo", httpMethod = "GET", notes = "精彩推荐logo", response = ResultBean.class)
	@RequestMapping(value = "indexlivelogo", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean indexLiveLogo() {
		Map<String, String> logo = new HashMap<>();
		logo.put("left", ConstantIF.PROJECT + redisService.get("livelogol"));
		logo.put("right", ConstantIF.PROJECT + redisService.get("livelogor"));
		return new ResultBean(logo);
	}

}
