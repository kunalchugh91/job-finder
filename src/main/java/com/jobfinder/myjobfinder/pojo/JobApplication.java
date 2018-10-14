package com.jobfinder.myjobfinder.pojo;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="jobapplication")
public class JobApplication {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="applicationID")
	public int applicationID;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="jobID")
	public Job job;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid")
	public UserProfile userProfile;
	
	public Date applicationDate;
	
	public boolean decision; // true - accept, false - reject
	
	public boolean decisionMade; //true - decision has been made, false - not yet
	
	public Date decisionDate;
	
	@Transient
	private String jobTitle;
	
	@Transient
	private String company;
	
	@Transient
	private String decisionString;
	

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public int getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public boolean isDecision() {
		return decision;
	}

	public void setDecision(boolean decision) {
		this.decision = decision;
	}

	public boolean isDecisionMade() {
		return decisionMade;
	}

	public void setDecisionMade(boolean decisionMade) {
		this.decisionMade = decisionMade;
	}

	public Date getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setDecisionString(String decisionString) {
		this.decisionString = decisionString;
	}
	
	public String getDecisionString(){
		return decisionString;
	}
	
	

}
