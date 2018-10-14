package com.jobfinder.myjobfinder.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jobfinder.myjobfinder.pojo.Credential;


public class CredentialValidator implements Validator {

	@Override
	public boolean supports(Class cl) {
		// TODO Auto-generated method stub
		return cl.equals(Credential.class);
	}

	@Override
	public void validate(Object o, Errors errors) {
		// TODO Auto-generated method stub

		Credential c = (Credential) o;
		//c.setUsername(c.getUsername().replaceAll("[^a-zA-Z0-9]+", ""));
		boolean b = c.getUsername().matches("[a-zA-Z0-9]+");
		
		if(!b || (c.getUsername().length() < 6)){
			errors.rejectValue("username", "empty.username", "Username should contain only A-Z, a-z, 0-9 and should be at least 6 characters");
		}
		
		//c.setPassword(c.getPassword().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		//c.setUserType(c.getUserType().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		
		boolean n = c.getPassword().matches("[a-zA-Z0-9_!#$%]+");
		
		if((!n) || (c.getPassword().length() < 6)){
			errors.rejectValue("password", "errors.password", "Password should contain A-Z, a-z, 0-9, _!#$% only and be atleast 6 digits");
		}	
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty.username", "username cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty.password", "password cannot be empty");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "empty.phone", "phone cannot be empty");		
		
	}

}
