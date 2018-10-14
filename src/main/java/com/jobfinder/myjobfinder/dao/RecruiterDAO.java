package com.jobfinder.myjobfinder.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.Recruiter;
import com.jobfinder.myjobfinder.pojo.UserProfile;

public class RecruiterDAO extends DAO{
	
	public boolean save(Recruiter r){

	try{
	Transaction tx = getSession().beginTransaction();
	
	if(r.getRecruiterID() == 0)
	getSession().saveOrUpdate(r);
	else
		getSession().merge(r);
	
	tx.commit();
	return true;
	}
	
	catch(HibernateException e){
		rollback();
		System.out.println("HibernateException on RecruiterDAO.save()");
		return false;
	}	
	
}
	
	public Recruiter getRecruiterByID(String id){
		try{
		
			int i = Integer.parseInt(id);
		Recruiter r = getSession().get(Recruiter.class, i);	
		/*
		Query q = getSession().createQuery("from Recruiter where recruiterID = :id");
		q.setString("id", id);
		Recruiter r = (Recruiter) q.uniqueResult();
		*/
		
		return r;
		}
		catch(HibernateException e){
			System.out.println("HibernateException on getRecruiterByID()");
			return null;
		}
	}
	
	public List<Job> getJobList(Recruiter r){
		Query q = getSession().createQuery("from Job where recruiterID = :id");
		q.setInteger("id", r.getRecruiterID());
		return q.list();
	}
	
	public Recruiter getByUsername(String u){
		Criteria crit = getSession().createCriteria(Recruiter.class);
		Criteria c2 = crit.createCriteria("credential");
		c2.add(Restrictions.eq("username", u));
		try{
			Recruiter r = (Recruiter) crit.uniqueResult();
		return r;
		}
		catch(HibernateException e){
			System.out.println("HibernateException at Recruiter.getByUsername");
			return null;
		}
		catch(Exception e){
			System.out.println("Exception at Recruiter.getByUsername");
			return null;
		}	
		
	}	
	
}
