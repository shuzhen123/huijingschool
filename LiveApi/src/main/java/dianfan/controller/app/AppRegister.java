package dianfan.controller.app;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
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
import dianfan.exception.SQLExecutorException;
import dianfan.service.app.AppUserService;
import dianfan.util.RegexUtils;

/**
 * @ClassName UserControllerRegister
 * @Description 用户注册
 * @author cjy
 * @date 2017年12月20日 上午11:21:45
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppRegister {
	@Autowired
	private AppUserService userService;
	
	/**
	 * @Title: userMpRegister
	 * @Description: 注册用户
	 * @param telno 手机号码
	 * @param smscode 短信验证码
	 * @param password 密码
	 * @param invite_code 邀请码 
	 * @return
	 * @time: 2017年12月20日 上午11:26:24
	 */
	@LogOp(method = "userMpRegister", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "用户注册")
	@ApiOperation(value = "用户注册", httpMethod = "POST", notes = "用户注册", response = ResultBean.class)
	@RequestMapping(value = "register",method=RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean userMpRegister(
			@ApiParam("手机号码") @RequestParam(value = "telno") String telno,
			@ApiParam("短信验证码") @RequestParam(value = "smscode") String smscode,
			@ApiParam("密码") @RequestParam(value = "password") String password,
			@ApiParam("邀请码 ") @RequestParam(value = "invite_code", required = false) String invite_code,
			@ApiParam("业务员id") @RequestParam(value = "salerid", required = false) String salerid
			) {
		//输入验证
		if(!RegexUtils.phoneRegex(telno)) {
			return new ResultBean("001", ResultMsg.C_001);
		}else if(StringUtils.isEmpty(smscode.trim())) {
			return new ResultBean("004", ResultMsg.C_004);
		}else if(StringUtils.isEmpty(password.trim())) {
			return new ResultBean("034", ResultMsg.C_034);
		}
		
		//注册用户
		try {
			return userService.register(telno, smscode, password, invite_code, salerid);
		} catch (SQLExecutorException | IOException e) {
			return new ResultBean("005", ResultMsg.C_005);
		}
	}
	
	/**
	 * @Title: resetPassword
	 * @Description: 重置密码
	 * @param telno 手机号码
	 * @param smscode 短信验证码
	 * @param password 新密码
	 * @param repassword 再次输入
	 * @return
	 * @throws:
	 * @time: 2018年1月22日 上午11:14:22
	 */
	@LogOp(method = "resetPassword", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "重置密码")
	@ApiOperation(value = "重置密码", httpMethod = "POST", notes = "重置密码", response = ResultBean.class)
	@RequestMapping(value = "resetpwd",method=RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean resetPassword(
			@ApiParam("手机号码") @RequestParam(value = "telno") String telno,
			@ApiParam("短信验证码") @RequestParam(value = "smscode") String smscode,
			@ApiParam("新密码") @RequestParam(value = "password") String password,
			@ApiParam("再次输入密码") @RequestParam(value = "repassword") String repassword
			) {
		
		//输入验证
		if(!RegexUtils.phoneRegex(telno)) {
			return new ResultBean("001", ResultMsg.C_001);
		}else if("".equals(smscode.trim())) {
			return new ResultBean("004", ResultMsg.C_004);
		}else if(!password.equals(repassword)) {
			return new ResultBean("018", ResultMsg.C_018);
		}
		//重置密码
		try {
			return userService.resetPassword(telno, smscode, password);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
}