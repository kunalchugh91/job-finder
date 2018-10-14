package com.jobfinder.myjobfinder.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.UserProfile;


public class JobDAO extends DAO{

	public boolean save(Job j){
		try{
		Transaction tx = getSession().beginTransaction();
		
		if(j.getJobID() == 0)
		getSession().saveOrUpdate(j);
		else
			getSession().merge(j);
		
		tx.commit();
		return true;
		}
		catch(HibernateException e){
			rollback();
			System.out.println("HibernateException on JobDAO.save()");
			return false;
		}	
	}
	
	public List<Job> list(int i, String category){
		//Query q = getSession().createQuery("from Job");
		Criteria q = getSession().createCriteria(Job.class);
		Criteria q2 = q.createCriteria("jobCategory");
		q2.add(Restrictions.eq("category", category));
		q.add(Restrictions.eq("jobStatus", true));
		q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		q.setFirstResult(i);
		q.setMaxResults(10);
		List<Job> jobs = q.list();
		return jobs;
	}
	
	public Job getJobById(int id){
		try{
		
		Query q = getSession().createQuery("from Job where jobID = :id");
		q.setInteger("id", id);
		Job j = (Job) q.uniqueResult();
		
		return j;
		}
		catch(HibernateException e){
			System.out.println("HibernateException on getJobById()");
			return null;
		}
	}	
	
}
