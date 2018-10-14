package com.jobfinder.myjobfinder;

import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobfinder.myjobfinder.dao.DAO;
import com.jobfinder.myjobfinder.dao.JobApplicationDAO;
import com.jobfinder.myjobfinder.dao.JobDAO;
import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.JobApplication;
import com.jobfinder.myjobfinder.pojo.Recruiter;
import com.jobfinder.myjobfinder.pojo.UserProfile;

@Controller
public class CloseJobController {
	
	@Autowired
	@Qualifier("jobDAO")
	private JobDAO jobDAO;
	
	@Autowired
	@Qualifier("jobApplicationDAO")
	private JobApplicationDAO jobApplicationDAO;
	

	@RequestMapping(value="closejob.htm", method=RequestMethod.POST)
	public void closeJob(@RequestParam("jobID") int jobID,
			             HttpSession session,
			             HttpServletResponse response) throws IOException{
		
		Recruiter r = (Recruiter) session.getAttribute("user");
		Job j = jobDAO.getJobById(jobID);
		Iterator i = j.getJobApplications().iterator();
		Job job = DAO.getSession().get(Job.class, jobID);		
		while(i.hasNext()){
			JobApplication ja = (JobApplication) i.next();
			if(!ja.isDecisionMade()){
				ja.setDecision(false);
				ja.setDecisionDate(new Date(System.currentTimeMillis()));
				ja.setDecisionMade(true);
				jobApplicationDAO.save(ja);
				//UserProfile u = DAO.getSession().get(UserProfile.class, ja.getUserProfile());				
				try {
					UpdateCandidatesController.sendMail(2, ja.getUserProfile().getName(), ja.getUserProfile().getEmail(), job.getJobID(), job.getJobTitle(), job.getRecruiter().getName());
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					System.out.println("Could not send mail");
					e.printStackTrace();
				}				
			}
		}
		j.setJobStatus(false);
		jobDAO.save(j);
		
		response.sendRedirect("rechome.htm");
	}
}
