package com.jobfinder.myjobfinder.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DAO {
	
	private static final Logger log = Logger.getAnonymousLogger();
	private static final ThreadLocal sessionThread = new ThreadLocal();
	private static final SessionFactory sessionFactory = 
			new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	public static Session getSession(){
		Session session = (Session) DAO.sessionThread.get();
		if(session == null){
			session = sessionFactory.openSession();
			sessionThread.set(session);
		}
		return session;
	}
	
	protected void begin(){
		Transaction tx = DAO.getSession().beginTransaction();
	}
	
	protected void commit(){
		Transaction tx = DAO.getSession().getTransaction();
		tx.commit();
	}
	
	protected void rollback(){
		try{
			DAO.getSession().getTransaction().rollback();
		}
		catch(HibernateException e){
			log.log(Level.WARNING, "Cannot rollback", e);
		}
		try{
			DAO.getSession().close();
		}
		catch(HibernateException e){
			log.log(Level.WARNING, "Cannot close", e);
		}
		finally{
			DAO.sessionThread.set(null);
		}
	}
		
		protected void close(){
			DAO.getSession().close();
			DAO.sessionThread.set(null);
		}
	}


