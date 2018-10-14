package com.jobfinder.myjobfinder.dao;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.jobfinder.myjobfinder.pojo.Credential;


public class CredentialDAO extends DAO{

	public boolean save(Credential c){
		try{
		Transaction tx = getSession().beginTransaction();
		getSession().save(c);
		tx.commit();
		return true;
		}
		catch(HibernateException e){
			rollback();
			System.out.println("HibernateException on CredentialDAO.save()");
			return false;
		}	
	}	
	
}
