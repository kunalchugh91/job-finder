package com.jobfinder.myjobfinder;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jobfinder.myjobfinder.dao.DAO;
import com.jobfinder.myjobfinder.dao.JobApplicationDAO;
import com.jobfinder.myjobfinder.dao.JobDAO;
import com.jobfinder.myjobfinder.dao.UserProfileDAO;
import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.JobApplication;
import com.jobfinder.myjobfinder.pojo.JobSearch;
import com.jobfinder.myjobfinder.pojo.UserProfile;

@Controller
public class JobApplicationController {
	
	@Autowired
	@Qualifier("jobDAO")
	public JobDAO jobDAO;
	
	@Autowired
	@Qualifier("userProfileDAO")
	public UserProfileDAO userProfileDAO;	
	
	@Autowired
	@Qualifier("jobApplicationDAO")
	public JobApplicationDAO jobApplicationDAO;	
	
	/*
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		//binder.setValidator(validator);
	}
	*/		
	
	@RequestMapping(value="/apply.htm", method=RequestMethod.GET)
	public ModelAndView applyJob(HttpSession session, 
			                     //@ModelAttribute("jobsearch") JobSearch js,
			                     //BindingResult result
									@RequestParam("jobID") int jobID,
									HttpServletResponse response
			                     ) throws IOException{
		System.out.println("in method");
		UserProfile u = (UserProfile) session.getAttribute("user");
		
		Job j = jobDAO.getJobById(jobID);
		JobApplication ja = new JobApplication();
		j.getJobApplications().add(ja);
		u.getJobApplications().add(ja);
		ja.setApplicationDate(new Date(System.currentTimeMillis()));
		ja.setUserProfile(u);
		ja.setJob(j);
		jobDAO.save(j);
		userProfileDAO.save(u);
		jobApplicationDAO.save(ja);
		
	    /*
	     * 
	     * 
	     */
		response.sendRedirect("searchjobs.htm");
		return new ModelAndView("userhome");
	}
	
}

