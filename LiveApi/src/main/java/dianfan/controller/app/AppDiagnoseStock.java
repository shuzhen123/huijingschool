package dianfan.controller.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppDiagnoseStockService;

/**
 * @ClassName AppDiagnoseStock
 * @Description 专家诊股
 * @author cjy
 * @date 2018年3月8日 下午3:16:31
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppDiagnoseStock {
	@Autowired
	private AppDiagnoseStockService diagnoseStockService;
	@Autowired
	private RedisTokenService redisTokenService;

	/**
	 * @Title: stockInfoList
	 * @Description: 按类型获取诊股列表
	 * @param accesstoken
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年4月2日 上午9:22:51
	 */
	@LogOp(method = "stockInfoList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "按类型获取诊股列表")
	@ApiOperation(value = "按类型获取诊股列表", httpMethod = "GET", notes = "按类型获取诊股列表", response = ResultBean.class)
	@RequestMapping(value = "/stockinfolist", method = {RequestMethod.GET})
	@UnCheckedFilter
	public @ResponseBody ResultBean stockInfoList(
			/*@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,*/
			@ApiParam("诊股类型（-1全部，0未回复，1已回复）") @RequestParam(value = "type") int type,
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		try {
			Map<String, Object> list = diagnoseStockService.findStockListByType(type, page);
			return new ResultBean(list);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: sendStockQuestion
	 * @Description: 诊股提问
	 * @param type
	 * @param page
	 * @return
	 * @throws:
	 * @time: 2018年4月2日 上午9:57:47
	 */
	@LogOp(method = "sendStockQuestion", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "诊股提问")
	@ApiOperation(value = "诊股提问", httpMethod = "POST", notes = "诊股提问", response = ResultBean.class)
	@RequestMapping(value = "/stockquestion", method = {RequestMethod.POST})
	public @ResponseBody ResultBean sendStockQuestion(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("诊股标题") @RequestParam(value = "title") String title,
			@ApiParam("诊股问题内容") @RequestParam(value = "des") String des) {
		if(title == null || title.trim().isEmpty() || des == null || des.trim().isEmpty()) {
			return new ResultBean("026", ResultMsg.C_026);
		}
		try {
			TokenModel token = redisTokenService.getToken(accesstoken);
			diagnoseStockService.sendStockQuestion(token.getUserId(), title, des);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
}
