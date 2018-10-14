package com.jobfinder.myjobfinder.pojo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="credential")
public class Credential {
	
	@Id
	private String username;
	
	private String password;
	
	private String userType;
	

	public Credential(String username, String password, String userType) {
		// TODO Auto-generated constructor stub
		this.username = username;
		this.password = password;
		this.userType = userType;
	}
	
	public Credential(){
		
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/*
	@OneToOne(fetch=FetchType.LAZY, mappedBy="credential")
	private UserProfile userProfile;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="credential")
	private Recruiter recruiter;
	*/

	
	
}
