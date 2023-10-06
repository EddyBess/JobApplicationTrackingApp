package com.example.job_application_tracking_api;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class JobApplicationController {
    private final JobApplicationRepository repository;
    JobApplicationController(JobApplicationRepository repo){
        this.repository = repo;
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/applications")
    List<JobApplication> getAll(){
        return this.repository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/status")
    List<JobApplicationStatus> getAllStatus(){
        return Arrays.stream(JobApplicationStatus.values()).toList();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/applications")
    JobApplication add(@RequestBody JobApplication newJobApplication){
        return this.repository.save(newJobApplication);
    }
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
    @DeleteMapping("/applications/{id}")
    void deleteApplication(@PathVariable Long id){
        repository.deleteById(id);
    }


}
