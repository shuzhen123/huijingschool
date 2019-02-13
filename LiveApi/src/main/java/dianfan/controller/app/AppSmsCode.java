package dianfan.controller.app;

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
import dianfan.exception.SQLExecutorException;
import dianfan.service.app.AppSmsService;
import dianfan.util.RegexUtils;
/**
 * @ClassName UserControllerSmsCode
 * @Description 短信发送
 * @author cjy
 * @date 2017年12月20日 上午9:34:17
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppSmsCode {
	@Autowired
	private AppUserMapper userService;
	@Autowired
	private AppSmsService smsService;
	
	/**
	 * @Title: sendRegisterSmsCode
	 * @Description: 发送注册短信验证码
	 * @param telephone 用户手机号码
	 * @return
	 * @throws:
	 * @time: 2017年12月20日 上午10:05:40
	 */
	@LogOp(method = "sendRegisterSmsCode", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "发送注册短信验证码")
	@ApiOperation(value = "发送注册短信验证码", httpMethod = "GET", notes = "发送注册短信验证码", response = ResultBean.class)
	@RequestMapping(value = "/getregcode", method = {RequestMethod.GET})
	@UnCheckedFilter
	public @ResponseBody ResultBean sendRegisterSmsCode(@ApiParam("手机号码") @RequestParam(value = "telephone") String telephone){
		//1、验证手机号码格式
		if (!RegexUtils.phoneRegex(telephone)) {
			return new ResultBean("001", ResultMsg.C_001);
		}
		//2、检测号码是否被注册
		try {
			int count = userService.getUserCountByPhone(telephone);
			if(count > 0) {
				return new ResultBean("002", ResultMsg.C_002);
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		
		//3、发送验证码
		ResultBean resultBean = smsService.getCode(telephone, "reg");
		//4、发送成功
		return resultBean;
	}
	
	/**
	 * @Title: sendLoginSmsCode
	 * @Description: 发送登录短信验证码
	 * @param telephone 用户手机号码
	 * @return
	 * @throws:
	 * @time: 2017年12月20日 下午1:31:09
	 */
	@LogOp(method = "sendLoginSmsCode", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "发送登录短信验证码")
	@ApiOperation(value = "发送登录短信验证码", httpMethod = "GET", notes = "发送登录短信验证码", response = ResultBean.class)
	@RequestMapping(value = "/getlogincode")
	@UnCheckedFilter
	public @ResponseBody ResultBean sendLoginSmsCode(@ApiParam("手机号码") @RequestParam(value = "telephone") String telephone){
		//1、验证手机号码格式
		if (!RegexUtils.phoneRegex(telephone)) {
			return new ResultBean("001", ResultMsg.C_001);
		}
		//2、检测号码是否已注册
		try {
			int count = userService.getUserCountByPhone(telephone);
			if(count == 0) {
				return new ResultBean("007", ResultMsg.C_007);
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		
		//3、发送验证码
		ResultBean resultBean = smsService.getCode(telephone, "login");
		//4、发送成功
		return resultBean;
	}
	
	/**
	 * @Title: sendResetPwdSmsCode
	 * @Description: 发送重置密码短信验证码
	 * @param telephone 手机号码
	 * @return
	 * @throws:
	 * @time: 2018年1月22日 上午11:19:30
	 */
	@LogOp(method = "sendResetPwdSmsCode", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "发送重置密码短信验证码")
	@ApiOperation(value = "发送重置密码短信验证码", httpMethod = "GET", notes = "发送重置密码短信验证码", response = ResultBean.class)
	@RequestMapping(value = "/getrepwdcode")
	@UnCheckedFilter
	public @ResponseBody ResultBean sendResetPwdSmsCode(@ApiParam("手机号码") @RequestParam(value = "telephone") String telephone){
		//1、验证手机号码格式
		if (!RegexUtils.phoneRegex(telephone)) {
			return new ResultBean("001", ResultMsg.C_001);
		}
		//2、检测号码是否已注册
		try {
			int count = userService.getUserCountByPhone(telephone);
			if(count == 0) {
				return new ResultBean("007", ResultMsg.C_007);
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		
		//3、发送验证码
		ResultBean resultBean = smsService.getCode(telephone, "resetpwd");
		//4、发送成功
		return resultBean;
	}
	
	
}