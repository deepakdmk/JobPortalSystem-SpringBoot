package com.deeps.jobportal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.RecruiterProfile;
import com.deeps.jobportal.repository.RecruiterProfileRepository;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepo;

    public RecruiterProfileService(RecruiterProfileRepository springRecruiterProfileRepository) {
        this.recruiterProfileRepo = springRecruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getOne(Integer id) {
        return recruiterProfileRepo.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepo.save(recruiterProfile);
    }

}
