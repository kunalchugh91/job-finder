package com.jobfinder.myjobfinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;

import com.jobfinder.myjobfinder.dao.DAO;
import com.jobfinder.myjobfinder.dao.JobApplicationDAO;
import com.jobfinder.myjobfinder.dao.JobCategoryDAO;
import com.jobfinder.myjobfinder.dao.JobDAO;
import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.JobCategory;
import com.jobfinder.myjobfinder.pojo.JobSearch;
import com.jobfinder.myjobfinder.pojo.UserProfile;

@Controller
public class SearchJobController {
	
	@Autowired
	@Qualifier("jobCategoryDAO")
	private JobCategoryDAO jobCategoryDAO;	
	
	@Autowired
	@Qualifier("jobDAO")
	private JobDAO jobDAO;

	@Autowired
	@Qualifier("jobApplicationDAO")
	private JobApplicationDAO jobApplicationDAO;		
		

	@RequestMapping(value={"/searchjob.htm","/searchjobs.htm"}, method = RequestMethod.GET)
	public String showForm(ModelMap model, 
			               HttpSession s,
			               HttpServletRequest request){
		UserProfile u = (UserProfile) s.getAttribute("user");
		String s1 = request.getRequestURI();
		
		if(s1.contains("searchjobs.htm")){
			
			int index = (Integer) s.getAttribute("index");
			String keyword = String.valueOf(s.getAttribute("searchkey"));
			String category = String.valueOf(s.getAttribute("searchcat"));
			
			Session session = JobDAO.getSession();		
			Filter filter2 = null;
			if(!(keyword.equalsIgnoreCase(""))){
				filter2 = session.enableFilter("filter2");
				keyword = "%"+keyword+"%";
				filter2.setParameter("keyw", keyword);
			}	
			
			List<Job> jobs = jobDAO.list(index, category);
			if(filter2 != null)
			session.disableFilter("filter2");
			Iterator<Job> it = jobs.iterator();
			ArrayList<JobSearch> jobsearchlist = new ArrayList<JobSearch>();
			while(it.hasNext()){
				Job j = (Job)it.next();
				JobSearch js = new JobSearch();
				js.setCompany(j.getRecruiter().getCompany());
				js.setCurrency(j.getCurrency());
				js.setEmail(j.getRecruiter().getEmail());
				js.setEmploymentType(j.getEmploymentType());
				js.setJobCategory(j.getJobCategory().getCategory());
				js.setJobDescription(j.getJobDescription());
				js.setJobID(j.getJobID());
				js.setJobTitle(j.getJobTitle());
				js.setLocation(j.getLocation());
				js.setName(j.getRecruiter().getName());
				js.setPhone(j.getRecruiter().getPhone());
				js.setPlusCommission(j.isPlusCommission());
				js.setRangeAmt(j.getRangeAmt());
				js.setTerm(j.getTerm());
				if(jobApplicationDAO.hasApplied(u, j))
					js.setHasApplied("Applied already");
					else
						js.setHasApplied(" ");
				jobsearchlist.add(js);
				
			}
			
			model.addAttribute("results", jobsearchlist.size());
			model.addAttribute("jobsearchlist", jobsearchlist);
			
			return "searchjob";				
			
		}else{
		List<JobCategory> categories = jobCategoryDAO.list();
		//model.addAttribute("categories", categories);
		s.setAttribute("categories", categories);
		model.addAttribute("results", 0);
		//model.addAttribute("jobsearch", new JobSearch());
		return "searchjob";
		}
		
	}
	
	@RequestMapping(value="/searchjob.htm",  method = RequestMethod.POST, params="search")
	public ModelAndView fillForm(ModelMap model, HttpServletRequest request, HttpSession s){
		
		String keyword = request.getParameter("keyword");
		String category = request.getParameter("category");
		s.setAttribute("searchkey", keyword);
		s.setAttribute("searchcat", category);
		int index = 0;
		s.setAttribute("index", index);
		UserProfile u = (UserProfile) s.getAttribute("user");		
		System.out.println(category);
		Session session = JobDAO.getSession();
		
		
		/*
		Filter filter = session.enableFilter("filter1");
		category = "%"+category+"%";
		System.out.println(category);		
		filter.setParameter("catg", category);
		System.out.println(filter.toString());
		*/
		
		Filter filter2 = null;
		if(!(keyword.equalsIgnoreCase(""))){
			filter2 = session.enableFilter("filter2");
			keyword = "%"+keyword+"%";
			filter2.setParameter("keyw", keyword);
		}
		
		List<Job> jobs = jobDAO.list(index, category);
		Iterator<Job> it = jobs.iterator();
		ArrayList<JobSearch> jobsearchlist = new ArrayList<JobSearch>();
		while(it.hasNext()){
			Job j = (Job)it.next();
			JobSearch js = new JobSearch();
			js.setCompany(j.getRecruiter().getCompany());
			js.setCurrency(j.getCurrency());
			js.setEmail(j.getRecruiter().getEmail());
			js.setEmploymentType(j.getEmploymentType());
			js.setJobCategory(j.getJobCategory().getCategory());
			js.setJobDescription(j.getJobDescription());
			js.setJobID(j.getJobID());
			js.setJobTitle(j.getJobTitle());
			js.setLocation(j.getLocation());
			js.setName(j.getRecruiter().getName());
			js.setPhone(j.getRecruiter().getPhone());
			js.setPlusCommission(j.isPlusCommission());
			js.setRangeAmt(j.getRangeAmt());
			js.setTerm(j.getTerm());
			if(jobApplicationDAO.hasApplied(u, j))
				js.setHasApplied("Applied already");
				else
					js.setHasApplied(" ");			
			jobsearchlist.add(js);
			
		}
			
		
		/*
		if(filter != null)
		session.disableFilter("filter1");
		*/

		if(filter2 != null)
			session.disableFilter("filter2");
		
		model.addAttribute("results", jobsearchlist.size());
		
		return new ModelAndView("searchjob", "jobsearchlist", jobsearchlist);
		
	}	
	
	@RequestMapping(value="/searchjob.htm",  method = RequestMethod.POST, params="next")
	public ModelAndView nextResults(ModelMap model, HttpSession s){
		
		int index = (Integer) s.getAttribute("index");
		String keyword = String.valueOf(s.getAttribute("searchkey"));
		String category = String.valueOf(s.getAttribute("searchcat"));
		UserProfile u = (UserProfile) s.getAttribute("user");		
		
		Session session = JobDAO.getSession();		
		Filter filter2 = null;
		if(!(keyword.equalsIgnoreCase(""))){
			filter2 = session.enableFilter("filter2");
			keyword = "%"+keyword+"%";
			filter2.setParameter("keyw", keyword);
		}	
		
		index = index + 10;
		s.setAttribute("index", index);
		List<Job> jobs = jobDAO.list(index, category);
		if(filter2 != null)
		session.disableFilter("filter2");
		Iterator<Job> it = jobs.iterator();
		ArrayList<JobSearch> jobsearchlist = new ArrayList<JobSearch>();
		while(it.hasNext()){
			Job j = (Job)it.next();
			JobSearch js = new JobSearch();
			js.setCompany(j.getRecruiter().getCompany());
			js.setCurrency(j.getCurrency());
			js.setEmail(j.getRecruiter().getEmail());
			js.setEmploymentType(j.getEmploymentType());
			js.setJobCategory(j.getJobCategory().getCategory());
			js.setJobDescription(j.getJobDescription());
			js.setJobID(j.getJobID());
			js.setJobTitle(j.getJobTitle());
			js.setLocation(j.getLocation());
			js.setName(j.getRecruiter().getName());
			js.setPhone(j.getRecruiter().getPhone());
			js.setPlusCommission(j.isPlusCommission());
			js.setRangeAmt(j.getRangeAmt());
			js.setTerm(j.getTerm());
			if(jobApplicationDAO.hasApplied(u, j))
				js.setHasApplied("Applied already");
				else
					js.setHasApplied(" ");				
			jobsearchlist.add(js);
			
		}
		
		model.addAttribute("results", jobsearchlist.size());
		
		return new ModelAndView("searchjob", "jobsearchlist", jobsearchlist);		
		
		
	}	
	
	@RequestMapping(value="/searchjob.htm",  method = RequestMethod.POST, params="prev")
	public ModelAndView prevResults(ModelMap model, HttpSession s){
		
		int index = (Integer) s.getAttribute("index");
		String keyword = String.valueOf(s.getAttribute("searchkey"));
		String category = String.valueOf(s.getAttribute("searchcat"));
		UserProfile u = (UserProfile) s.getAttribute("user");		
		
		Session session = JobDAO.getSession();		
		Filter filter2 = null;
		if(!(keyword.equalsIgnoreCase(""))){
			filter2 = session.enableFilter("filter2");
			keyword = "%"+keyword+"%";
			filter2.setParameter("keyw", keyword);
		}	
		
		index = index - 10;
		s.setAttribute("index", index);
		List<Job> jobs = jobDAO.list(index, category);
		if(filter2 != null)
		session.disableFilter("filter2");
		Iterator<Job> it = jobs.iterator();
		ArrayList<JobSearch> jobsearchlist = new ArrayList<JobSearch>();
		while(it.hasNext()){
			Job j = (Job)it.next();
			JobSearch js = new JobSearch();
			js.setCompany(j.getRecruiter().getCompany());
			js.setCurrency(j.getCurrency());
			js.setEmail(j.getRecruiter().getEmail());
			js.setEmploymentType(j.getEmploymentType());
			js.setJobCategory(j.getJobCategory().getCategory());
			js.setJobDescription(j.getJobDescription());
			js.setJobID(j.getJobID());
			js.setJobTitle(j.getJobTitle());
			js.setLocation(j.getLocation());
			js.setName(j.getRecruiter().getName());
			js.setPhone(j.getRecruiter().getPhone());
			js.setPlusCommission(j.isPlusCommission());
			js.setRangeAmt(j.getRangeAmt());
			js.setTerm(j.getTerm());
			if(jobApplicationDAO.hasApplied(u, j))
				js.setHasApplied("Applied already");
				else
					js.setHasApplied(" ");				
			jobsearchlist.add(js);
			
		}
		
		model.addAttribute("results", jobsearchlist.size());
		
		return new ModelAndView("searchjob", "jobsearchlist", jobsearchlist);		
		
		
	}	
	
}
