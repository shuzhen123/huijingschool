package dianfan.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.app.UserService;
/**
 * @ClassName AppLogin
 * @Description 业务员登录
 * @author cjy
 * @date 2018年3月9日 下午4:07:37
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/app")
public class AppLogin {
	@Autowired
	private UserService userService;
	
	/**
	 * @Title: getVerifyCode
	 * @Description: 获取图片验证码
	 * @param request
	 * @param response
	 * @throws:
	 * @time: 2018年3月14日 上午10:06:16
	 
	@LogOp(method = "getVerifyCode",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取图片验证码")
	@ApiOperation(value = "获取图片验证码", httpMethod = "GET", notes = "获取图片验证码", response = ResultBean.class)
	@RequestMapping(value = "vcode", method=RequestMethod.GET)
	@UnCheckedFilter
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
           
        //生成随机字串 
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        //存入会话session 
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute(ConstantIF.VERFY_CODE_KEY);
        //保存现在的
        session.setAttribute(ConstantIF.VERFY_CODE_KEY, verifyCode.toLowerCase()); 
        //生成图片 
        int w = 100, h = 30; 
        try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/**
	 * @Title: userLogin
	 * @Description: 用户登录
	 * @param telno 手机号码
	 * @param password 密码
	 * @return
	 * @throws:
	 * @time: 2018年3月9日 下午4:17:16
	 */
	@LogOp(method = "userLogin", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "业务员登录")
	@ApiOperation(value = "业务员登录", httpMethod = "POST", notes = "业务员登录", response = ResultBean.class)
	@RequestMapping(value = "login", method=RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean userLogin(
			@ApiParam("用户账号") @RequestParam(value = "username", required = true) String username, 
			@ApiParam("密码") @RequestParam(value = "password", required = true) String password) {
		UserInfo info = new UserInfo();
		info.setUsername(username);
		info.setPassword(password);
		//验证登录
		try {
			return userService.userLogin(info);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
/*	@LogOp(method = "userLogin", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "业务员登录")
	@ApiOperation(value = "业务员登录", httpMethod = "POST", notes = "业务员登录", response = ResultBean.class)
	@RequestMapping(value = "login", method=RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean userLogin(
			@ApiParam("用户账号") @RequestParam(value = "username", required = true) String username, 
			@ApiParam("密码") @RequestParam(value = "password", required = true) String password) {
		//@ApiParam("图片验证码") @RequestParam(value = "vcode", required = true) String vcode,
		//HttpSession session
		//获取session中的验证码
		//String save_code = (String) session.getAttribute(ConstantIF.VERFY_CODE_KEY);
		//获取用户验证码
		//if(save_code == null || !save_code.toLowerCase().equals(vcode.toLowerCase())) {
		//验证码不正确
		//return new ResultBean("003", ResultMsg.C_003);
		//}
		
		UserInfo info = new UserInfo();
		info.setUsername(username);
		info.setPassword(password);
		//验证登录
		try {
			return userService.userLogin(info);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
*/	/**
	 * @Title: userLogout
	 * @Description: 业务员登出
	 * @param username
	 * @param password
	 * @param vcode
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午4:12:32
	 */
	@LogOp(method = "userLogout", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "业务员登出")
	@ApiOperation(value = "业务员登出", httpMethod = "POST", notes = "业务员登出", response = Void.class)
	@RequestMapping(value = "logout", method=RequestMethod.POST)
	public void userLogout(@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken) {
		userService.userLogout(accesstoken);
	}
	
}
