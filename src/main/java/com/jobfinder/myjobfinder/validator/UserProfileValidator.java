package com.jobfinder.myjobfinder.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jobfinder.myjobfinder.pojo.UserProfile;

public class UserProfileValidator implements Validator{

	@Override
	public boolean supports(Class cl) {
		// TODO Auto-generated method stub
		return cl.equals(UserProfile.class);
	}

	@Override
	public void validate(Object o, Errors errors) {
		// TODO Auto-generated method stub
		UserProfile u = (UserProfile) o;
		
		u.setName(u.getName().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		//u.setEmail(u.getEmail().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		u.setPhone(u.getPhone().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		u.setHeadline(u.getHeadline().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		u.setCountry(u.getCountry().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		u.setPostalCode(u.getPostalCode().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		u.setLinkedInURL(u.getLinkedInURL().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		u.setTwitterURL(u.getTwitterURL().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		u.setFacebookURL(u.getFacebookURL().replaceAll("[^a-zA-Z0-9_.\\s]+", ""));
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty.name", "name cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty.email", "email cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "empty.phone", "phone cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "headline", "empty.headline", "headline cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "empty.country", "country cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "empty.postalCode", "postalCode cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "linkedInURL", "empty.linkedInURL", "linkedInURL cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "facebookURL", "empty.facebookURL", "facebookURL cannot be empty");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "twitterURL", "empty.twitterURL", "twitterURL cannot be empty");
		
		if(!(u.getEmail().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9]+.[A-Za-z]{2,}"))){
			errors.rejectValue("email", "empty.email", "email should be in proper format");
		}	
		
		if(!(u.getName().matches("[a-zA-Z\\s]+"))){
			errors.rejectValue("name", "empty.name", "Name can only contain a-z, A-Z and whitespaces");
		}	
		
		if(!(u.getCountry().matches("[A-Za-z]+"))){
			errors.rejectValue("country", "empty.country", "country can contain only a-z, A-Z");
		}

		
		if(u.getPhone().length() != 10){
			errors.rejectValue("phone", "empty.phone", "phone should be 10 digits");
		}
		
		try{
			long n = Long.parseLong(u.getPhone());
		}catch(Exception e){
			errors.rejectValue("phone", "empty.phone", "phone should contain only numbers");
		}		
		
	}

}
