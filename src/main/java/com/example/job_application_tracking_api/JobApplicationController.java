package com.example.job_application_tracking_api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobApplicationController {
    private final JobApplicationRepository repository;
    JobApplicationController(JobApplicationRepository repo){
        this.repository = repo;
    }

    @GetMapping("/applications")
    List<JobApplication> getAll(){
        return this.repository.findAll();
    }
    @PostMapping("/applications")
    JobApplication add(@RequestBody JobApplication newJobApplication){
        return this.repository.save(newJobApplication);
    }

    @PutMapping("/applications/{id}")
    JobApplication update(@RequestBody JobApplication newJobApplication,@PathVariable Long id){
        return this.repository.findById(id).map(
                application->{
                    application.setCompanyName(newJobApplication.getCompanyName());
                    application.setCompanyEmail(newJobApplication.getCompanyEmail());
                    application.setStatus(newJobApplication.getApplicationStatus());
                    return repository.save(application);
                }
        ).orElseGet(() -> {
            newJobApplication.setId(id);
            return repository.save(newJobApplication);
        });


    }
    @DeleteMapping("/applications/{id}")
    void deleteApplication(@PathVariable Long id){
        repository.deleteById(id);
    }


}
