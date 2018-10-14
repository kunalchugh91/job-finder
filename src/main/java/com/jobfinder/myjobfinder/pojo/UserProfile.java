package com.jobfinder.myjobfinder.pojo;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Entity
@Table(name="userprofile")
public class UserProfile {
	
	public UserProfile(){
		
	}

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="userid")
private int userid;

@Transient
private CommonsMultipartFile photo;

@Column(name="file", nullable=true)
private String file;

@Column(name="name", nullable=true)
private String name;

@Column(name="email", unique=true, nullable=true)
private String email;

@Column(name="phone", nullable=true)
private String phone;

@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="jobCategoryID", nullable=true)
private JobCategory industry;

@Transient
private String ind;

@Column(name="headline", nullable=true)
private String headline;


@Column(name="country", nullable=true)
private String country;

@Column(name="postalcode", nullable=true)
private String postalCode;

@Column(name="linkedinurl", nullable=true)
private String linkedInURL;

@Column(name="facebookurl", nullable=true)
private String facebookURL;

@Column(name="twitterurl", nullable=true)
private String twitterURL;

@OneToMany(mappedBy="userProfile", fetch=FetchType.LAZY)
Set<JobApplication> jobApplications = new HashSet<JobApplication>();

@OneToOne(fetch=FetchType.EAGER)
private Credential credential;

@Transient
private String decision;


public Credential getCredential() {
	return credential;
}

public void setCredential(Credential credential) {
	this.credential = credential;
}

public int getUserid() {
	return userid;
}

public void setUserid(int userid) {
	this.userid = userid;
}



public CommonsMultipartFile getPhoto() {
	return photo;
}

public void setPhoto(CommonsMultipartFile photo) {
	this.photo = photo;
}

public String getFile() {
	return file;
}

public void setFile(String file) {
	this.file = file;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public JobCategory getIndustry() {
	return industry;
}

public void setIndustry(JobCategory industry) {
	this.industry = industry;
}

public String getHeadline() {
	return headline;
}

public void setHeadline(String headline) {
	this.headline = headline;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getPostalCode() {
	return postalCode;
}

public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
}

public String getLinkedInURL() {
	return linkedInURL;
}

public void setLinkedInURL(String linkedInURL) {
	this.linkedInURL = linkedInURL;
}

public String getFacebookURL() {
	return facebookURL;
}

public void setFacebookURL(String facebookURL) {
	this.facebookURL = facebookURL;
}

public String getTwitterURL() {
	return twitterURL;
}

public void setTwitterURL(String twitterURL) {
	this.twitterURL = twitterURL;
}

public String getInd() {
	return ind;
}

public void setInd(String ind) {
	this.ind = ind;
}

public Set<JobApplication> getJobApplications() {
	return jobApplications;
}

public void setJobApplications(Set<JobApplication> jobApplications) {
	this.jobApplications = jobApplications;
}

public String getDecision() {
	return decision;
}

public void setDecision(String decision) {
	this.decision = decision;
}



}
