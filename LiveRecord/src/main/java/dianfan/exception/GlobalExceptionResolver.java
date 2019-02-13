package dianfan.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		String viewName = determineViewName(ex, request);
		response.setCharacterEncoding("UTF-8");
		if (viewName != null) {// JSP格式返回
			if (!(request.getHeader("accept").contains("application/json")
					|| (request.getHeader("X-Requested-With") != null
							&& request.getHeader("X-Requested-With").contains("XMLHttpRequest")))) {
				// 如果不是json请求
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {// JSON格式返回
				try {
					PrintWriter writer = response.getWriter();
					writer.write(ex.getMessage());
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		} else {
			if (request.getHeader("accept").contains("application/json")
					|| (request.getHeader("X-Requested-With") != null
							&& request.getHeader("X-Requested-With").contains("XMLHttpRequest"))) {
				try {
					PrintWriter writer = response.getWriter();
					writer.write("{\"code\":\"500\",\"msg\":\"接口异常\", \"data\": null}");
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}
