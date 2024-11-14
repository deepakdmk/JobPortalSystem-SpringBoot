package com.deeps.jobportal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.JobSeekerProfile;
import com.deeps.jobportal.repository.JobSeekerProfileRepository;

@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepo;

    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepo) {
        this.jobSeekerProfileRepo = jobSeekerProfileRepo;
    }

    public Optional<JobSeekerProfile> getOne(Integer id) {
        return jobSeekerProfileRepo.findById(id);
    }

}
