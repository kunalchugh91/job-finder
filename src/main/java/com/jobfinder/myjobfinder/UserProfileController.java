package com.jobfinder.myjobfinder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.jobfinder.myjobfinder.dao.JobCategoryDAO;

import com.jobfinder.myjobfinder.dao.UserProfileDAO;
import com.jobfinder.myjobfinder.pojo.Credential;
import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.JobApplication;
import com.jobfinder.myjobfinder.pojo.JobCategory;
import com.jobfinder.myjobfinder.pojo.Recruiter;
import com.jobfinder.myjobfinder.pojo.UserProfile;
import com.jobfinder.myjobfinder.validator.UserProfileValidator;

@Controller
public class UserProfileController {
	
	@Autowired
	@Qualifier("jobCategoryDAO")
	JobCategoryDAO jobCategoryDAO;
	
	@Autowired
	@Qualifier("userProfileValidator")
	UserProfileValidator validator;	
	
	@Autowired
	@Qualifier("userProfileDAO")	
	UserProfileDAO userProfileDAO;
	
	@Autowired
	ServletContext servletContext;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@RequestMapping(value="/userhome.htm", method=RequestMethod.GET)
	public ModelAndView showHomePage(HttpSession session, ModelMap model){
		
		Object o = session.getAttribute("user");
		UserProfile u = (UserProfile) o;
		
		Set<JobApplication> sa = u.getJobApplications();
		Iterator i = sa.iterator();
		JobApplication ja = null;
		while(i.hasNext()){
			ja = (JobApplication) i.next();
			Job j = ja.getJob();
			ja.setJobTitle(j.getJobTitle());
			ja.setCompany(j.getRecruiter().getCompany());
			if(ja.isDecisionMade()){
				if(ja.isDecision())
					ja.setDecisionString("ACCEPTED");
					else
						ja.setDecisionString("REJECTED");
				
			}else{
				ja.setDecisionString("PENDING");
			}
		}
		
		
		model.addAttribute("jobapplications", sa);
	    return new ModelAndView("userhome");
			
	}	
	
	@RequestMapping(value = "/profile.htm", method = RequestMethod.GET)
	public String showForm(ModelMap model, HttpSession session) {
		
		UserProfile userProfile = (UserProfile) session.getAttribute("user");

		// command object
		List<JobCategory> categories = jobCategoryDAO.list();
		
		//model.addAttribute("categories", categories);	
		session.setAttribute("categories", categories);
		
		model.addAttribute("userProfile", userProfile);

		// return form view
		return "myprofile";

	}
	
	@RequestMapping(value="/profile.htm", method=RequestMethod.POST, params="save")
	public ModelAndView saveProfile(@ModelAttribute("userProfile") UserProfile userProfile, 
			                        BindingResult errors,
			                        HttpServletRequest request,
			                        HttpServletResponse response,
			                        HttpSession session) throws IOException{
		
		validator.validate(userProfile, errors);
		if(errors.hasErrors()){
			return new ModelAndView("myprofile", "userProfile", userProfile);
		}		
		
	try {
		File directory = null;
		String path = null;
		String seperator = File.separator;
		if(seperator.equalsIgnoreCase("\\")){
			path = servletContext.getRealPath("").replace("build\\", "");
			
		}
		if(seperator.equalsIgnoreCase("/")){
			path = servletContext.getRealPath("").replace("build/", "");
			path += "/";
			
		}
		directory = new File(path + "\\" + userProfile.getName());
		boolean b = directory.exists();
		if(!b){
			b = directory.mkdir();
		}
		if(b){
			MultipartFile photo = userProfile.getPhoto();
			File localFile = new File(directory, photo.getOriginalFilename());
			photo.transferTo(localFile);
			userProfile.setFile(localFile.getPath());
			
			// get category from DB where name of category = userProfile.ind
			JobCategory j = jobCategoryDAO.getCategoryByName(userProfile.getInd());
			UserProfile u = (UserProfile)session.getAttribute("user");
			u.setFile(userProfile.getFile());
			u.setName(userProfile.getName());
			u.setPhone(userProfile.getPhone());
			u.setEmail(userProfile.getEmail());
			u.setHeadline(userProfile.getHeadline());
			u.setCountry(userProfile.getCountry());
			u.setPostalCode(userProfile.getPostalCode());
			u.setLinkedInURL(userProfile.getLinkedInURL());
			u.setFacebookURL(userProfile.getFacebookURL());
			u.setTwitterURL(userProfile.getTwitterURL());
			u.setIndustry(j);
			userProfileDAO.save(u);
			
		}
		
		}

	catch (IllegalStateException e) {
		System.out.println("*** IllegalStateException: " + e.getMessage());
	} 
	catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("*** IOException: " + e.getMessage());
	} 
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		response.sendRedirect("userhome.htm");
		return new ModelAndView("userhome");
	}
	

	
}
