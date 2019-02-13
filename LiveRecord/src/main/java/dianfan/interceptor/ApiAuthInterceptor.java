package dianfan.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.utility.StringUtility;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.entities.TokenModel;
import dianfan.service.RedisTokenService;

/**
 * @ClassName ApiAuthInterceptor
 * @Description 请求状态拦截器
 * @author cjy
 * @date 2017年12月20日 下午4:50:29
 */
public class ApiAuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisTokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1、如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		//swagger接口放行
		if (request.getServletPath().contains("/api-docs")) {
			return true;
		}
		
		// 2、检测是否为放行api
		if (method.getAnnotation(UnCheckedFilter.class) != null) {
			return true;
		}
		
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "application/json");
		
		//获取accesstoken
		String accesstoken = request.getParameter(ConstantIF.ACCESSTOKEN);
		if (StringUtility.isNull(accesstoken)) {
			// 未登录
			response.getWriter().write("{\"code\":\"010\",\"msg\":\"登录超时或未登录，请重新登录\"}");
			response.getWriter().close();
			return false;
		}
		
		TokenModel model = tokenService.getToken(accesstoken);
		//验证通过
		if (tokenService.checkToken(model)) {
			return true;
		}else {
			// 验证失败
			response.getWriter().write("{\"code\":\"011\",\"msg\":\"登录超时或未登录，请重新登录\"}");
		}
		response.getWriter().close();
		return false;
	}
	
}
