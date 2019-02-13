package dianfan.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.utility.StringUtility;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.entities.TokenModel;
import dianfan.service.LogInfoService;
import dianfan.service.RedisTokenService;
/**
 * @ClassName LogInterceptor
 * @Description 接口访问日志拦截器
 * @author cjy
 * @date 2017年12月20日 下午4:29:12
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private RedisTokenService tokenService;
	@Autowired
	private LogInfoService logwrite;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	   return true;
	}
	
	/*
	 * (non-Javadoc)
	 * <p>Title: postHandle</p>
	 * <p>Description: 接口访问监听器</p>
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * link: @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			if (method.isAnnotationPresent(LogOp.class)) {
				//获取注解数据
				LogOp rl = method.getAnnotation(LogOp.class);
				
				if (ConstantIF.LOG_TYPE_1.equals(rl.logtype())) {
					// 已登录操作
					String accesstoken = request.getParameter(ConstantIF.ACCESSTOKEN);
					if (!StringUtility.isNull(accesstoken)) {
						// 验证token
						TokenModel model = tokenService.getToken(accesstoken);
						logwrite.writeLog(Long.parseLong(rl.logtype()), rl.description(), rl.method(),
								model.getUserId(), request.getRemoteHost());
					} else {
						//未登录操作
						logwrite.writeLog(Long.parseLong(rl.logtype()), rl.description(), rl.method(), rl.userid(), request.getRemoteHost());
					}
				} else {
					// // PC端
					// UserInfo user = (UserInfo)
					// request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
					// if (user != null) {
					// logwrite.writeLog(Long.parseLong(rl.logtype()), rl.description(),
					// rl.method(), user.getUserid(),
					// rl.params(), request.getRemoteHost());
					// } else {
					// logwrite.writeLog(Long.parseLong(rl.logtype()), rl.description(),
					// rl.method(), rl.userid(),
					// rl.params(), request.getRemoteHost());
					// }
				}
			}
		}
	}
}
