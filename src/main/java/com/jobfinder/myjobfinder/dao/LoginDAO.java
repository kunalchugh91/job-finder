package com.jobfinder.myjobfinder.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jobfinder.myjobfinder.pojo.Credential;
import com.jobfinder.myjobfinder.pojo.Recruiter;
import com.jobfinder.myjobfinder.pojo.UserProfile;

public class LoginDAO extends DAO{
	

	public Credential authenticate(String username, String password){
		UserProfile userProfile = null;
		Recruiter recruiter = null;
		
		Query q = getSession().createQuery("from Credential where username =:u and password =:p");
		q.setString("u", username);
		q.setString("p", password);
		try{
			
		Credential credential = (Credential) q.uniqueResult();
		return credential;		
		
		   }
		catch(HibernateException e){
			System.out.println("HibernateException in LoginDAO.authenticate");
			return null;
		}
		catch(Exception e){
			System.out.println("Exception in LoginDAO.authenticate");
			return null;
		}		
		
	}

	public Credential signup(String username, 
			             String password, 
			             String userType) {
		// TODO Auto-generated method stub
		
		// check if username exists
		Session session = getSession();
		Credential credential = session.get(Credential.class, username);
		if(credential != null){
			return null;
		}
		Credential c = new Credential(username, password, userType);
		return c;
		
		
	}
	
}
