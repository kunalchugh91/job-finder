package com.jobfinder.myjobfinder;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.jobfinder.myjobfinder.dao.JobCategoryDAO;
import com.jobfinder.myjobfinder.dao.JobDAO;
import com.jobfinder.myjobfinder.dao.RecruiterDAO;
import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.JobCategory;
import com.jobfinder.myjobfinder.pojo.Recruiter;
import com.jobfinder.myjobfinder.validator.CreateJobValidator;

@Controller
public class CreateJobController {
	
	@Autowired
	@Qualifier("jobDAO")
	public JobDAO jobDAO;

	@Autowired
	@Qualifier("jobCategoryDAO")
	JobCategoryDAO jobCategoryDAO;
	
	@Autowired
	@Qualifier("recruiterDAO")
	RecruiterDAO recruiterDAO;		
	
	@Autowired
	@Qualifier("createJobValidator")
	CreateJobValidator validator;	
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}	
	
	@RequestMapping(value = "/createjob.htm", method = RequestMethod.GET)
	public String showForm(ModelMap model, HttpSession session) {
		Job job = new Job(); // should be AutoWired

		// command object
		List<JobCategory> categories = jobCategoryDAO.list();
		//model.addAttribute("categories", categories);	
		session.setAttribute("categories", categories);
		
		model.addAttribute("job", job);

		// return form view
		return "createjob";

	}
	
	@RequestMapping(value="/createjob.htm", method=RequestMethod.POST)
	public ModelAndView saveProfile(@ModelAttribute("job") Job job, 
			                        BindingResult errors,
			                        HttpServletRequest request,
			                        HttpServletResponse response,
			                        HttpSession session) throws IOException{
		
		validator.validate(job, errors);
		if(errors.hasErrors()){
			return new ModelAndView("createjob", "job", job);
		}
		
	try {
		Recruiter recruiter = (Recruiter) session.getAttribute("user");
		Job j = new Job();
		j.setJobStatus(true);
		j.setRecruiter(recruiter);
		j.setJobCategory(jobCategoryDAO.getCategoryByName(job.getJc()));
		j.setJobTitle(job.getJobTitle());
		j.setLocation(job.getLocation());
		j.setEmploymentType(job.getEmploymentType());
		j.setJobDescription(job.getJobDescription());
		j.setRangeAmt(job.getRangeAmt());
		j.setCurrency(job.getCurrency());
		j.setTerm(job.getTerm());
		j.setPlusCommission(job.isPlusCommission());
		recruiter.getJobs().add(j);
	    jobDAO.save(j); 
		
		}

	catch (IllegalStateException e) {
		System.out.println("*** IllegalStateException: " + e.getMessage());
	} 
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		response.sendRedirect("rechome.htm");
		return new ModelAndView("recruiterhome");
	}	
	
}
