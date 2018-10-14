package com.jobfinder.myjobfinder.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.jobfinder.myjobfinder.pojo.JobCategory;
import com.jobfinder.myjobfinder.pojo.UserProfile;

public class JobCategoryDAO extends DAO{

	
	
	public JobCategoryDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<JobCategory> list(){
		try{
			begin();
			Query q = getSession().createQuery("from JobCategory");
			List<JobCategory> categories =  q.list();
			commit();
			return categories;
		}
		catch(HibernateException e){
			rollback();
			System.out.println("Hibernate exception occured on categoryDAO.list, Rolled back");
		}
		return null;
	}
	
	public JobCategory getCategoryByName(String cat){
		
		try{
			
			Query q = getSession().createQuery("from JobCategory where category = :cat");
			q.setString("cat", cat);
			JobCategory c = (JobCategory) q.uniqueResult();
			
			return c;
			}
			catch(HibernateException e){
				System.out.println("HibernateException on getCategoryByName()");
				return null;
			}		
		
	}
	
}
