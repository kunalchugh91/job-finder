package com.jobfinder.myjobfinder.pojo;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="recruiter")
public class Recruiter {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="recruiterID")
	public int recruiterID;
	
	@Column(name="name", nullable=true)
	public String name;
	
	@Column(name="jobtitle", nullable=true)
	public String jobTitle;
	
	@Column(name="phone", nullable=true)
	public String phone;
	
	@Column(name="email", nullable=true, unique=true)
	public String email;
	
	@Column(name="company", nullable=true)
	public String company;	
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="recruiter")
	public Set<Job> jobs = new HashSet<Job>();
	
	@OneToOne(fetch=FetchType.EAGER)
	private Credential credential;	


	public int getRecruiterID() {
		return recruiterID;
	}

	public void setRecruiterID(int recruiterID) {
		this.recruiterID = recruiterID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}
	
	
	
}
