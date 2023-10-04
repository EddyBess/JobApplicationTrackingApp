package com.example.job_application_tracking_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class JobApplication {

    private @Id @GeneratedValue Long id;
    private String companyName;
    private String companyEmail;
    private JobApplicationStatus status = JobApplicationStatus.PENDING;

    public JobApplication() {

    }



    public Long getId(){
        return this.id;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public String getCompanyEmail(){
        return this.companyEmail;
    }
    public JobApplicationStatus getApplicationStatus(){
        return this.status;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public void setStatus(JobApplicationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JobApplication{" + "id=" + this.id + ", CompanyName='" + this.companyName + '\'' + ", CompanyEmail='" + this.companyEmail + '\'' +"Status="+ this.status +'}';
    }
}
