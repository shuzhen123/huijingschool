package dianfan.controller.app;

import java.io.IOException;

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
import dianfan.dao.mapper.app.AppUserMapper;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppUserService;
import dianfan.util.RegexUtils;

/**
 * @ClassName AppLogin
 * @Description 用户登录
 * @author cjy
 * @date 2018年3月7日 下午12:13:54
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppLogin {
	/**
	 * 注入：RedisService
	 */
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisService redisService;
	/**
	 * 注入：RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * 注入：AppUserService
	 */
	@Autowired
	private AppUserService userService;
	/**
	 * 注入：AppUserMapper
	 */
	@Autowired
	private AppUserMapper appUserMapper;

	/**
	 * @Title: userLogin
	 * @Description: 用户登录
	 * @param telno
	 *            手机号码
	 * @param password
	 *            密码（或短信验证码）
	 * @return ResultBean ResultBean
	 * @throws:
	 * @time: 2017年12月20日 下午2:53:09
	 */
	@LogOp(method = "userLogin", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "用户登录")
	@ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录", response = ResultBean.class)
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean userLogin(@ApiParam("手机号码") @RequestParam(value = "telno") String telno,
			@ApiParam("密码（或短信验证码）") @RequestParam(value = "password") String password) {
		ResultBean result = null;
		// 输入验证
		if (!RegexUtils.phoneRegex(telno)) {
			result = new ResultBean("001", ResultMsg.C_001);
		} else if (password.isEmpty()) {
			result = new ResultBean("008", ResultMsg.C_008);
		} else {
			// 验证登录
			try {
				result = userService.userLogin(telno, password);
				// 删除缓存的验证码
				redisService.del(telno + "login");
			} catch (SQLExecutorException e) {
				result = new ResultBean("500", ResultMsg.C_500);
			}
		}
		return result;
	}
	
	/**
	 * @Title: autoLogin
	 * @Description: 用户自动登录
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年5月5日 上午10:49:29
	 */
	@LogOp(method = "autoLogin", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "用户自动登录")
	@ApiOperation(value = "用户自动登录", httpMethod = "POST", notes = "用户自动登录", response = ResultBean.class)
	@RequestMapping(value = "autologin", method = RequestMethod.POST)
	public @ResponseBody ResultBean autoLogin(@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			return userService.userAutoLogin(token.getUserId());
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: getOpenid
	 * @Description: 获取用户openid
	 * @param accesstoken
	 *            accesstoken
	 * @param code
	 *            code
	 * @throws:
	 * @time: 2018年4月17日 下午5:20:48
	 */
	@LogOp(method = "getOpenid", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "获取用户openid")
	@ApiOperation(value = "获取用户openid", httpMethod = "POST", notes = "获取用户openid", response = ResultBean.class)
	@RequestMapping(value = "wxopenid", method = RequestMethod.POST)
	public @ResponseBody ResultBean getOpenid(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("code") @RequestParam(value = "code") String code) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			userService.getUserOpenid(token.getUserId(), code);
		} catch (IOException | SQLExecutorException e) {
		}
		return new ResultBean();
	}

	/**
	 * @Title: addCustomer
	 * @Description: 记录意向客户
	 * @param telno
	 *            手机号码
	 * @param realname
	 *            姓名
	 * @return ResultBean ResultBean
	 * @throws:
	 * @time: 2017年12月21日 下午12:48:14
	 */
	@LogOp(method = "addCustomer", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "记录意向客户")
	@ApiOperation(value = "记录意向客户", httpMethod = "GET", notes = "记录意向客户", response = ResultBean.class)
	@RequestMapping(value = "addcustomer", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean addCustomer(@ApiParam("手机号码") @RequestParam(value = "telno") String telno,
			@ApiParam("姓名") @RequestParam(value = "realname") String realname) {
		ResultBean result = new ResultBean();
		// 输入验证
		if (!RegexUtils.phoneRegex(telno)) {
			result = new ResultBean("001", ResultMsg.C_001);
		} else if ("".equals(realname.trim())) {
			result = new ResultBean("009", ResultMsg.C_009);
		} else {
			// 检测用户是否已存在
			try {
				int count = appUserMapper.getCustomerUserCountByPhone(telno);
				if (count > 0) {
					// 此用户已存在
					result = new ResultBean("011", ResultMsg.C_011);
				} else {
					// 保存用户信息
					userService.addCustomerUser(telno, realname);
				}
			} catch (SQLExecutorException e) {
				result = new ResultBean("500", ResultMsg.C_500);
			}
		}
		return result;
	}
}
