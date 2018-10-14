package com.jobfinder.myjobfinder.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jobfinder.myjobfinder.pojo.Recruiter;

public class RecruiterInterceptor extends HandlerInterceptorAdapter {

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
		
		System.out.println("Inside RecruiterInterceptor");
		HttpSession session = request.getSession();
		if(session != null){
			
		
		
		Object o = session.getAttribute("user");
		if(o != null){
			
		try{
			Recruiter recruiter = (Recruiter) o;
			return true;
		}
		catch(Exception e){
			System.out.println("Exception in Recruiterinterceptor");
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
