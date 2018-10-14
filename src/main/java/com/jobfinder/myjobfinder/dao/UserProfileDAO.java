package com.jobfinder.myjobfinder.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.jobfinder.myjobfinder.pojo.UserProfile;

public class UserProfileDAO extends DAO{

	
	
public UserProfileDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

public UserProfile getUserByEmail(String email){
	try{
	
	Query q = getSession().createQuery("from UserProfile where email = :email");
	q.setString("email", email);
	UserProfile u = (UserProfile) q.uniqueResult();
	
	return u;
	}
	catch(HibernateException e){
		System.out.println("HibernateException on getUserByEmail()");
		return null;
	}
}

public boolean save(UserProfile u){
	try{
	Transaction tx = getSession().beginTransaction();
	
	if(u.getUserid() != 0)
	getSession().merge(u);
	else
	getSession().saveOrUpdate(u);
	
	tx.commit();
	//getSession().evict(u);
	return true;
	}
	catch(HibernateException e){
		rollback();
		System.out.println("HibernateException on UserProfileDAO.save()");
		return false;
	}	
}

public UserProfile getByUsername(String u){
	Criteria crit = getSession().createCriteria(UserProfile.class);
	Criteria c2 = crit.createCriteria("credential");
	c2.add(Restrictions.eq("username", u));
	try{
	UserProfile up = (UserProfile) crit.uniqueResult();
	return up;
	}
	catch(HibernateException e){
		System.out.println("HibernateException at UserProfile.getByUsername");
		return null;
	}
	catch(Exception e){
		System.out.println("Exception at UserProfile.getByUsername");
		return null;
	}	
	
}
	
}
