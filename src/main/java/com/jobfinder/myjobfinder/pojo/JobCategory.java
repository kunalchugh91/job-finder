package com.jobfinder.myjobfinder.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name="jobcategory")
@Filter(name="filter1")
public class JobCategory {

	@Id
	@GeneratedValue
	private int jobCategoryID;
	
	private String category;
	
	@OneToMany(mappedBy="industry")
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>();
	

	public int getJobCategoryID() {
		return jobCategoryID;
	}

	public void setJobCategoryID(int jobCategoryID) {
		this.jobCategoryID = jobCategoryID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	
	
	
	
}
