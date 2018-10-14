package com.jobfinder.myjobfinder;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.ModelAndView;

import com.jobfinder.myjobfinder.dao.DAO;
import com.jobfinder.myjobfinder.dao.RecruiterDAO;
import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.Recruiter;
import com.jobfinder.myjobfinder.validator.RecruiterValidator;


@Controller
public class RecruiterController {
	
	@Autowired
	@Qualifier("recruiterDAO")
	private RecruiterDAO recruiterDAO;
	
	@Autowired
	@Qualifier("recruiterValidator")
	RecruiterValidator validator;		
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@RequestMapping(value="/rechome.htm", method=RequestMethod.GET)
	public String showHomePage(ModelMap model, HttpSession session){
		
		Recruiter recruiter = (Recruiter) session.getAttribute("user");
		
		
		List<Job> lj = recruiterDAO.getJobList(recruiter);
		
		model.addAttribute("joblist", lj);
		
		return "recruiterhome";
	}

	@RequestMapping(value = "/recprofile.htm", method = RequestMethod.GET)
	public String showForm(ModelMap model, HttpSession session) {
		Recruiter recruiter = (Recruiter) session.getAttribute("user");

		model.addAttribute("recruiter", recruiter);

		// return form view
		return "recruiterprofile";

	}
	
	@RequestMapping(value="/recprofile.htm", method=RequestMethod.POST)
	public ModelAndView saveProfile(@ModelAttribute("recruiter") Recruiter recruiter,
			                        HttpServletRequest request,
			                        ModelMap model,
			                        HttpSession session, BindingResult errors){
		
		validator.validate(recruiter, errors);
		if(errors.hasErrors()){
			return new ModelAndView("recruiterprofile","recruiter", recruiter);
		}
		Recruiter r = null;
	try {
		r = (Recruiter) session.getAttribute("user");
		r.setName(recruiter.getName());
		r.setJobTitle(recruiter.getJobTitle());
		r.setPhone(recruiter.getPhone());
		r.setEmail(recruiter.getEmail());
		r.setCompany(recruiter.getCompany());
		recruiterDAO.save(r);
		
		}

	catch (IllegalStateException e) {
		System.out.println("*** IllegalStateException: " + e.getMessage());
	} 
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	    List<Job> lj = recruiterDAO.getJobList(r);
	
	    model.addAttribute("joblist", lj);		
		return new ModelAndView("recruiterhome");
	}	
	
}
