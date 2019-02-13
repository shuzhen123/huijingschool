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
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppShopCartMapper;
import dianfan.entities.CartCourse;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppShopCartService;

/**
 * @ClassName AppShopCart
 * @Description 我的购物车
 * @author cjy
 * @date 2018年4月9日 下午12:15:03
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppShopCart {
	@Autowired
	private AppShopCartMapper appShopCartMapper;
	@Autowired
	private AppShopCartService appShopCartService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: addMyShopCart
	 * @Description: 课程添加到我的购物车
	 * @param accesstoken
	 * @param courseid
	 * @return
	 * @throws:
	 * @time: 2018年4月9日 下午1:20:57
	 */
	@LogOp(method = "addMyShopCart", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "课程添加到我的购物车")
	@ApiOperation(value = "课程添加到我的购物车", httpMethod = "POST", notes = "课程添加到我的购物车", response = ResultBean.class)
	@RequestMapping(value = "addshopcart", method=RequestMethod.POST)
	public @ResponseBody ResultBean addMyShopCart(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("课程id") @RequestParam(value = "courseid", required = true) String courseid) {
		try {
			TokenModel token = redisTokenService.getToken(accesstoken);
			//检测是否已添加到购物车
			CartCourse cc = new CartCourse();
			cc.setUserid(token.getUserId());
			cc.setCourseid(courseid);
			Boolean bool = appShopCartMapper.checkAddCart(cc);
			if(!bool) {
				//添加到购物车
				appShopCartService.addMyCart(cc);
			}
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: myShopCat
	 * @Description: 我的购物车列表
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年4月9日 下午12:19:59
	 */
	@LogOp(method = "myShopCat", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "我的购物车列表")
	@ApiOperation(value = "我的购物车列表", httpMethod = "POST", notes = "我的购物车列表", response = ResultBean.class)
	@RequestMapping(value = "myshopcat", method=RequestMethod.POST)
	public @ResponseBody ResultBean myShopCat(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("请求页数") @RequestParam(value = "page", defaultValue="1", required = false) Integer page) {
		if(page == null || page < 1) {
			page = 1;
		}
		try {
			TokenModel token = redisTokenService.getToken(accesstoken);
			Map<String, Object> cart = appShopCartService.findMyCart(token.getUserId(), page);
			return new ResultBean(cart);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: delShopCatCourse
	 * @Description: 删除购物车中的课程
	 * @param accesstoken
	 * @param courseid
	 * @return
	 * @throws:
	 * @time: 2018年4月9日 下午2:42:54
	 */
	@LogOp(method = "delShopCatCourse", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "删除购物车中的课程")
	@ApiOperation(value = "删除购物车中的课程", httpMethod = "POST", notes = "删除购物车中的课程", response = ResultBean.class)
	@RequestMapping(value = "delshopcatcourse", method=RequestMethod.POST)
	public @ResponseBody ResultBean delShopCatCourse(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("课程id") @RequestParam(value = "courseid", required = true) String courseid) {
		try {
			TokenModel token = redisTokenService.getToken(accesstoken);
			appShopCartService.delShopCatCourse(token.getUserId(), courseid);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
}
