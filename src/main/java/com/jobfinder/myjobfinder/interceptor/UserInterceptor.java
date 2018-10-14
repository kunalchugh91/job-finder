package com.jobfinder.myjobfinder.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jobfinder.myjobfinder.pojo.Recruiter;
import com.jobfinder.myjobfinder.pojo.UserProfile;

public class UserInterceptor extends HandlerInterceptorAdapter {
	
	private String loginPage;
	
	public void setLoginPage(String p){
		loginPage = p;
	}
	
	public String getLoginPage(){
		return loginPage;
	}
	
	/**
	 * This implementation always returns <code>true</code>.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
		
		System.out.println("Inside UserInterceptor");		
		HttpSession session = request.getSession();
		if(session != null){
			
		
		
		Object o = session.getAttribute("user");
		if(o != null){
			
		try{
			UserProfile userProfile = (UserProfile) o;
			return true;
		}
		catch(Exception e){

			System.out.println("Exception in UserInterceptor");
			e.printStackTrace();
			
		}}else{
			response.sendRedirect(loginPage);
			return false;
		}
		
		}
		response.sendRedirect(loginPage);
		return false;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
