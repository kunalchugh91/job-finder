package com.jobfinder.myjobfinder;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import com.jobfinder.myjobfinder.dao.DAO;
import com.jobfinder.myjobfinder.dao.JobApplicationDAO;
import com.jobfinder.myjobfinder.dao.JobDAO;
import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.JobApplication;
import com.jobfinder.myjobfinder.pojo.UserProfile;

@Controller
public class UpdateCandidatesController {
	
	@Autowired
	@Qualifier("jobDAO")
	public JobDAO jobDAO;
	
	@Autowired
	@Qualifier("jobApplicationDAO")
	public JobApplicationDAO jobApplicationDAO;	
	
	@RequestMapping(value="/viewcandidates.htm", method=RequestMethod.GET)
	public ModelAndView showForm(ModelMap model, @RequestParam(value="jobID") int id){
		
		Job j = jobDAO.getJobById(id);
		Iterator<JobApplication> i = j.getJobApplications().iterator();
		ArrayList<UserProfile> upl = new ArrayList<UserProfile>();
		while(i.hasNext()){
			JobApplication ja = i.next();
			UserProfile u = ja.getUserProfile();
			if(ja.isDecisionMade()){
				if(ja.isDecision()) u.setDecision("ACCEPTED");
				else u.setDecision("REJECTED");
			}else{
				u.setDecision("PENDING");
			}
			upl.add(u);
		}
		model.addAttribute("jobID", j.getJobID());
		model.addAttribute("jobTitle", j.getJobTitle());
		model.addAttribute("userapps", upl);
		System.out.println("viewcand");
		return new ModelAndView("viewcandidates");
	}
	
	@RequestMapping(value="/updatecandidates.htm", method=RequestMethod.POST)
	public void updateCandidates(@RequestParam(value="dec") int decision, 
			                             @RequestParam(value="user") int userid,
			                             @RequestParam(value="jobID") int jobID,
			                             HttpServletResponse response) throws IOException{
		// 1 - accept, 2 - reject
		System.out.println(userid + " " + decision);
		
		JobApplication ja = jobApplicationDAO.getByUIDJID(userid, jobID);
		UserProfile u = DAO.getSession().get(UserProfile.class, userid);
		Job j = DAO.getSession().get(Job.class, jobID);
		if(decision == 1){
			ja.setDecision(true);
		}else{
			ja.setDecision(false);			
		}
		ja.setDecisionDate(new Date(System.currentTimeMillis()));
		ja.setDecisionMade(true);
		jobApplicationDAO.save(ja);
		
			try {
				sendMail(decision, u.getName(), u.getEmail(), j.getJobID(), j.getJobTitle(), j.getRecruiter().getName());
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				System.out.println("Could not send mail");
				e.printStackTrace();
			}
		
		PrintWriter out = response.getWriter();
		out.println("<td>Done!</td>");
	}

	public static void sendMail(int decision, String name, String emailid, int jobID, String jobTitle, String recName) throws EmailException {
		// TODO Auto-generated method stub
		
		String s1 = "Dear "+name+",\n";
		String s2 = "This is regarding your job application for the position of "+jobTitle+" (Job ID "+
		             jobID+" ).";
		String s3 = null;
		if(decision == 1){
			s3 = "Congratulations, we have found that your skillset matches our requirement.";
		}else{
			s3 = "We have viewed your application. Unfortunately, your skills do not match our requirements. We have decided to pursue other candidates";
		}
		String s4 = "\n"+"Thank you,\n"+recName;
		s1 = s1+s2+s3+s4;
		
		Email email= new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthentication("chugh43@gmail.com", "q1+w2=//");
        email.setSSLOnConnect(true);
        email.setFrom("chugh43@gmail.com");
        email.setSubject("Regarding your Job Application "+jobID);
        email.setMsg(s1);
        emailid = "kunalchugh91@gmail.com";        
        email.addTo(emailid);
		System.out.println(email.send());
	}

}
