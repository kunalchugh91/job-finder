package com.jobfinder.myjobfinder.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jobfinder.myjobfinder.pojo.Job;

public class CreateJobValidator implements Validator{

	@Override
	public boolean supports(Class cl) {
		// TODO Auto-generated method stub
		return cl.equals(Job.class);
		
	}

	@Override
	public void validate(Object o, Errors errors) {
		// TODO Auto-generated method stub
		Job j = (Job) o;
		
		j.setJobTitle(j.getJobTitle().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		j.setLocation(j.getLocation().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		j.setEmploymentType(j.getEmploymentType().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		j.setJobDescription(j.getJobDescription().replaceAll("[^a-zA-Z0-9:_.\\s]+", ""));
		j.setCurrency(j.getCurrency().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		j.setTerm(j.getTerm().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobTitle", "empty.jobTitle", "Job Title cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "empty.location", "Location cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employmentType", "empty.employmentType", "Please enter either Full-time or Part-Time");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobDescription", "empty.jobDescription", "Job Description cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rangeAmt", "empty.rangeAmt", "Please enter Amount in digits");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currency", "empty.currency", "Please enter currency in words (Dollar, Rupee etc)");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "term", "empty.term", "Please enter Yearly, Monthly, Weekly or Daily");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "plusCommission", "empty.plusCommission", "Plus Commission cannot be empty");
		
		if(!
		       (j.getTerm().equalsIgnoreCase("yearly") || 
				j.getTerm().equalsIgnoreCase("monthly") || 
				j.getTerm().equalsIgnoreCase("weekly") || 
				j.getTerm().equalsIgnoreCase("daily")
				)){
			errors.rejectValue("term", "empty.term", "Please enter Yearly, Monthly, Weekly or Daily");
		}
		
		
	}

}
