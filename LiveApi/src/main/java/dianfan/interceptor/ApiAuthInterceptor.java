package dianfan.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.utility.StringUtility;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.ResultBean;
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
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "application/json");
		response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错 
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE"); 
		response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间 
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization"); 
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1. 
		response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. 
		
		// 1、如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// swagger接口放行
		if (request.getServletPath().contains("/api-docs")) {
			return true;
		}

		// 2、检测是否为放行api
		if (method.getAnnotation(UnCheckedFilter.class) != null) {
			return true;
		}

		// 获取accesstoken
		String accesstoken = request.getParameter(ConstantIF.ACCESSTOKEN);
		if (StringUtility.isNull(accesstoken)) {
			// 未登录
			ResultBean rb = new ResultBean("010", ResultMsg.C_010);
			ObjectMapper mapper = new ObjectMapper();
			String res = mapper.writeValueAsString(rb);
			response.flushBuffer();
			response.getWriter().write(res);
			response.getWriter().close();
			return false;
		}

		// 验证token
		TokenModel model = tokenService.getToken(accesstoken);
		// 验证通过
		if (tokenService.checkToken(model)) {
			return true;
		} else {
			// 验证失败
			ResultBean rb = new ResultBean("010", ResultMsg.C_010);
			ObjectMapper mapper = new ObjectMapper();
			String res = mapper.writeValueAsString(rb);
			response.flushBuffer();
			response.getWriter().write(res);
			response.getWriter().close();
		}
		return false;
	}

}
