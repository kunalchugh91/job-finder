package com.jobfinder.myjobfinder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jobfinder.myjobfinder.dao.CredentialDAO;
import com.jobfinder.myjobfinder.dao.LoginDAO;
import com.jobfinder.myjobfinder.dao.RecruiterDAO;
import com.jobfinder.myjobfinder.dao.UserProfileDAO;
import com.jobfinder.myjobfinder.pojo.Credential;
import com.jobfinder.myjobfinder.pojo.Recruiter;
import com.jobfinder.myjobfinder.pojo.UserProfile;
import com.jobfinder.myjobfinder.validator.CredentialValidator;

@Controller
public class LoginController {
	
	@Autowired
	@Qualifier("loginDAO")
	private LoginDAO loginDAO;
	
	@Autowired
	@Qualifier("credentialValidator")
	private CredentialValidator validator;
	
	@Autowired
	@Qualifier("userProfileDAO")
	private UserProfileDAO userProfileDAO;
	
	@Autowired
	@Qualifier("recruiterDAO")
	private RecruiterDAO recruiterDAO;	
	
	@Autowired
	@Qualifier("credentialDAO")
	private CredentialDAO credentialDAO;	
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}	
	

	@RequestMapping(value="/login.htm", method=RequestMethod.GET)
	public ModelAndView showLoginPage(){
		
		return new ModelAndView("login", "credential", new Credential()) ;
	}
	
	@RequestMapping(value="/login.htm", method=RequestMethod.POST)
	public ModelAndView Authorize(@RequestParam("username") String username,
			                      @RequestParam("password") String password,
			                      HttpServletRequest request,
			                      HttpServletResponse response,
			                      @ModelAttribute("credential") Credential credential,
			                      BindingResult errors){
		
		// Validate (check password is atleast 6 digits)
		validator.validate(credential, errors);
		if(errors.hasErrors()){
			return new ModelAndView("login", "credential", credential);
		}	
		
		
		password = credential.getPassword();
		username = credential.getUsername();		
	
	    credential = loginDAO.authenticate(username, password);
	    if(credential != null){
		
		Object o = null;
		
		try{
		
		if(credential.getUserType().equals("JOBSEEKER")){
			o = userProfileDAO.getByUsername(credential.getUsername());
			
		}
		if(credential.getUserType().equals("RECRUITER")){
			o = recruiterDAO.getByUsername(credential.getUsername());
			
		}
		
		if(o != null){
			HttpSession session = request.getSession(true);
			if(o instanceof UserProfile){
				UserProfile u = (UserProfile) o;
				session.setAttribute("user", u);
				response.sendRedirect("userhome.htm");
			}
			if(o instanceof Recruiter){
				Recruiter r = (Recruiter) o;
				session.setAttribute("user", r);
				response.sendRedirect("rechome.htm");;
			}			

		}		
		
		   }
		catch(HibernateException e){
			System.out.println("HibernateException in LoginDAO.authenticate");
		}
		catch(Exception e){
			System.out.println("Exception in LoginDAO.authenticate");
		}
		
	    }
		
		request.setAttribute("message", "Username or Password did not match");
		credential = new Credential();
		return new ModelAndView("login", "credential", credential);
}
	
	@RequestMapping(value="/logup.htm", method=RequestMethod.POST)
	public ModelAndView SignUp(@RequestParam("username") String username,
			                      @RequestParam("password") String password,
			                      HttpServletRequest request,
			                      HttpServletResponse response,
			                      @ModelAttribute("credential") Credential credential,
			                      BindingResult errors) throws IOException{	
	
		// Validate (check password is atleast 6 digits)
		validator.validate(credential, errors);
		if(errors.hasErrors()){
			return new ModelAndView("login", "credential", credential);
		}			
		
		String userType = credential.getUserType();
		password = credential.getPassword();
		username = credential.getUsername();
		
		Credential c = loginDAO.signup(username, password, userType);
		if(c != null){
		Object o = null;
		
		if(userType.equals("JOBSEEKER")){
			credentialDAO.save(c);
			UserProfile u = new UserProfile();
			u.setCredential(c);
			userProfileDAO.save(u);
			o = u;
		}else{
			credentialDAO.save(c);
			Recruiter r = new Recruiter();
			r.setCredential(c);
			recruiterDAO.save(r);
			o = r;			
		}		
		
		if(o != null){
			HttpSession session = request.getSession(true);
			if(o instanceof UserProfile){
				UserProfile u = (UserProfile) o;
				session.setAttribute("user", u);
				response.sendRedirect("userhome.htm");
			}
			if(o instanceof Recruiter){
				Recruiter r = (Recruiter) o;
				session.setAttribute("user", r);
				response.sendRedirect("rechome.htm");
			}			

		}
		}
		
		request.setAttribute("message2", "Please choose a different username");
		credential = new Credential();
		return new ModelAndView("login", "credential", credential);		
}	
	
	@RequestMapping(value="/logout.htm", method=RequestMethod.GET)
	public void logout(HttpServletResponse response, HttpSession session) throws IOException{
		session.invalidate();
		response.sendRedirect("login.htm");
	}
	
}	
