package dianfan.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.exception.SessionTimeoutException;
/**
 * @ClassName SessionTimeoutInterceptor
 * @Description 后台管理拦截器
 * @author cjy
 * @date 2018年1月2日 下午1:34:21
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1、如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		Method method = ((HandlerMethod) handler).getMethod();
		if (method.getAnnotation(UnCheckedFilter.class) != null) {
			//允许访问的方法
			return true;
		}
		//当前session
		Object token = request.getSession().getAttribute(ConstantIF.PC_SESSION_KEY);

		if (token != null) {
			//token认证通过，检测操作权限
			String uri = request.getRequestURI();
			if(uri.indexOf("/admin/") != -1) {
				//后台管理员登录
				//缓存的权限
				List<String> rolelist = (List<String>) request.getSession().getAttribute(ConstantIF.PC_ROLE);
				if(rolelist.contains("all")) {
					//admin账号登录
					return true;
				}
				//当前操作方法名
				String methodName = uri.substring(uri.lastIndexOf("/") + 1).toLowerCase();
				if(rolelist.contains(methodName)) {
					//当前操作有权限
					return true;
				}else {
					//检测是否在所有权限中
					List<String> allroles = (List<String>) request.getSession().getAttribute(ConstantIF.PC_ROLE_ALL);
					if(allroles.contains(methodName)) {
						//当前操作无权限
						response.sendError(403, "暂无访问权限");
						return false;
					}else {
						return true;
					}
				}
			}else if(uri.indexOf("/agent/") != -1) {
				//代理商登录
				String queryString = request.getQueryString();  
				List<String> rolelist = (List<String>) request.getSession().getAttribute(ConstantIF.PC_AGENT_ROLE);
				if(rolelist.contains("all")) {
					//代理商账号登录
					return true;
				}
				//当前操作方法名
				if(queryString != null && false == queryString.trim().isEmpty())
				{
					uri = uri + "?" + queryString;
				}
				String methodName = uri.substring(uri.lastIndexOf("/") + 1).toLowerCase();
				if(rolelist.contains(methodName)) {
					//当前操作有权限
					return true;
				}else {
					//检测是否在所有权限中
					List<String> allroles = (List<String>) request.getSession().getAttribute(ConstantIF.PC_AGENT_ROLE_ALL);
					if(allroles.contains(methodName)) {
						//当前操作无权限
						response.sendError(403, "暂无访问权限");
						return false;
					}else {
						return true;
					}
				}
			}else if(uri.indexOf("/saler/") != -1) {
				//代理商登录
				return true;
			}else if(uri.indexOf("/tea/") != -1) {
				//老师登录
				return true;
			}else if(uri.indexOf("/hx/") != -1) {
				//和讯人员登录
				return true;
			}else {
				//未知角色登录
				response.sendError(403, "暂无访问权限");
				return false;
			}
		} else {
			//登录已过期或未登录
		    throw new SessionTimeoutException();// 返回到配置文件中定义的路径
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
