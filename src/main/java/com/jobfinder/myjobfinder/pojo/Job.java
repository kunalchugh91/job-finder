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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;

@Entity
@FilterDefs({
@FilterDef(name="filter1", 
           parameters = @ParamDef(name="catg", type="string"),
           defaultCondition="category like :catg"),
@FilterDef(name="filter2", 
           parameters = @ParamDef(name="keyw", type="string"))
})
@Filter(name="filter2", condition="jobDescription like :keyw")
@Table(name="job")
public class Job {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="jobID")
	public int jobID;
	
	@Column(name="jobTitle")
	public String jobTitle;
	
	@Column(name="location")
	public String location;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="jobCategoryID")
	@Filter(name="filter1")
	public JobCategory jobCategory;
	
	@Transient
	public String jc;
	
	public String employmentType; //Full-time, Part-time, Contractor,Temporary. 
	
	
	@Column(name="jobdescription")
	public String jobDescription;
	
	@Column(name="jobstatus")
	public boolean jobStatus;            // true -> open, false -> closed
	
	@Column(name="rangeamt")
	public int rangeAmt;
	
	@Column(name="currency")
	public String currency;
	
	@Column(name="term")
	public String term; //yearly, monthly etc
	
	@Column(name="plusCommission")
	public boolean plusCommission;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="recruiterID")
    public Recruiter recruiter;
	
	@OneToMany(mappedBy="job", fetch=FetchType.EAGER)
	public Set<JobApplication> jobApplications = new HashSet<JobApplication>();

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public JobCategory getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(JobCategory jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public boolean isJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(boolean jobStatus) {
		this.jobStatus = jobStatus;
	}

	public int getRangeAmt() {
		return rangeAmt;
	}

	public void setRangeAmt(int rangeAmt) {
		this.rangeAmt = rangeAmt;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public boolean isPlusCommission() {
		return plusCommission;
	}

	public void setPlusCommission(boolean plusCommission) {
		this.plusCommission = plusCommission;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public Set<JobApplication> getJobApplications() {
		return jobApplications;
	}

	public void setJobApplications(Set<JobApplication> jobApplications) {
		this.jobApplications = jobApplications;
	}
	
	

}
