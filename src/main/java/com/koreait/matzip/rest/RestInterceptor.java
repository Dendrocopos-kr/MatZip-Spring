package com.koreait.matzip.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestParam;

public class RestInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private RestMapper mapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String[] uriArr = uri.split("/");
		
		String[] checkKeywords = { "del", "upd","Del","Upd"};
		for(String keyword : checkKeywords) {
			if( uriArr[2].contains(keyword) ) {
				int i_rest = CommonUtils.getIntParameter("i_rest", request);
				if(i_rest == 0 ) {
					return false;
				}
				
				int i_user = SecurityUtils.getLoginUserPK(request);
				return _authSuccess(i_rest, i_user);
			}
		}
		return super.preHandle(request, response, handler);
	}
	
	private boolean _authSuccess(int i_rest, int i_user) {
		return i_user == mapper.selRestChkUser(i_rest);
	}
}
