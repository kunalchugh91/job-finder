package com.jobfinder.myjobfinder.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jobfinder.myjobfinder.pojo.Recruiter;

public class RecruiterValidator implements Validator{

	@Override
	public boolean supports(Class cl) {
		// TODO Auto-generated method stub
		return cl.equals(Recruiter.class);
	}

	@Override
	public void validate(Object o, Errors errors) {
		// TODO Auto-generated method stub
		Recruiter r = (Recruiter) o;
		
		r.setName(r.getName().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		//r.setEmail(r.getEmail().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		r.setJobTitle(r.getJobTitle().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		r.setPhone(r.getPhone().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		r.setCompany(r.getCompany().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		
		if(r.getPhone().length() != 10){
			errors.rejectValue("phone", "errors.phone", "Phone should have 10 digits");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty.name", "name cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty.email", "email cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "empty.phone", "phone cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobTitle", "empty.jobTitle", "jobTitle cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company", "empty.company", "company cannot be empty");		
		
		if(!(r.getName().matches("[a-zA-Z\\s]+"))){
			errors.rejectValue("name", "empty.name", "Name can only contain a-z, A-Z and whitespaces");
		}
		
		if(!(r.getEmail().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9]+.[A-Za-z]{2,}"))){
			errors.rejectValue("email", "empty.email", "email should be in proper format");
		}
		
	}

}
