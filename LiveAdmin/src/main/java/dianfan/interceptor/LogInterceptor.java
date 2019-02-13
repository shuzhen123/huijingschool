package dianfan.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.entities.UserInfo;
import dianfan.service.LogInfoService;

/**
 * @ClassName LogInterceptor
 * @Description 接口访问日志拦截器
 * @author cjy
 * @date 2017年12月20日 下午4:29:12
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
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
				// 获取注解数据
				LogOp rl = method.getAnnotation(LogOp.class);

				if (ConstantIF.LOG_TYPE_2.equals(rl.logtype())) {
					// PC端
					Object attr = request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);
					if (attr != null && attr instanceof UserInfo) {
						UserInfo user = (UserInfo) attr;
						logwrite.writeLog(Long.parseLong(rl.logtype()), rl.description(), rl.method(), user.getUserid(), request.getRemoteHost());
					} else {
						logwrite.writeLog(Long.parseLong(rl.logtype()), rl.description(), rl.method(), rl.userid(), request.getRemoteHost());
					}
				}
			}
		}
	}
}
