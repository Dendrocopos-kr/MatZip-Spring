package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.koreait.matzip.user.model.UserParam;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI();
		String[] uriArr = uri.split("/");

		if (uriArr[1].equals("resources")) {
			return super.preHandle(request, response, handler);
		} else if (uriArr.length < 3) {
			return false;
		}


		boolean isLogout = SecurityUtils.isLogout(request);

		switch (uriArr[1]) {
		case ViewRef.URI_USER:
			switch (uriArr[2]) {
			case "login":
			case "join":
				if (!isLogout) {
					response.sendRedirect("/rest/map");
					return false;
				}
			}
		case ViewRef.URI_REST:
			switch (uriArr[2]) {
			case "reg":
				if (isLogout) {
					response.sendRedirect("/user/login");
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
}
