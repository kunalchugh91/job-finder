package com.jobfinder.myjobfinder.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.jobfinder.myjobfinder.pojo.Job;
import com.jobfinder.myjobfinder.pojo.JobApplication;
import com.jobfinder.myjobfinder.pojo.UserProfile;

public class JobApplicationDAO extends DAO{

	public boolean save(JobApplication ja){
		try{
		Transaction tx = getSession().beginTransaction();
		
		if(ja.getApplicationID() == 0)
		getSession().saveOrUpdate(ja);
		else
			getSession().merge(ja);
			
		tx.commit();
		return true;
		}
		catch(HibernateException e){
			rollback();
			System.out.println("HibernateException on JobApplicationDAO.save()");
			return false;
		}	
	}
	
	public boolean hasApplied(UserProfile u, Job j){
		Criteria c = getSession().createCriteria(JobApplication.class);
		Criteria c1 = c.createCriteria("job");
		Criteria c2 = c.createCriteria("userProfile");
		c1.add(Restrictions.eq("jobID", j.getJobID()));
		c2.add(Restrictions.eq("userid", u.getUserid()));
		try{
			JobApplication ja = (JobApplication) c.uniqueResult();
			if(ja == null) return false;
			else return true;
		}catch(HibernateException e){
			return true;
		}
	}
	
	public JobApplication getByUIDJID(int userid, int jobID){
		Criteria c = getSession().createCriteria(JobApplication.class);
		Criteria c1 = c.createCriteria("userProfile");
		Criteria c2 = c.createCriteria("job");
		c1.add(Restrictions.eq("userid", userid));
		c2.add(Restrictions.eq("jobID", jobID));
		try{
		JobApplication ja = (JobApplication) c.uniqueResult();
		return ja;
		}
		catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}
	
}
