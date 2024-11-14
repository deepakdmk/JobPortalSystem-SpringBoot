package com.deeps.jobportal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.IRecruiterJobs;
import com.deeps.jobportal.entity.JobCompany;
import com.deeps.jobportal.entity.JobLocation;
import com.deeps.jobportal.entity.JobPostActivity;
import com.deeps.jobportal.entity.RecruiterJobsDto;
import com.deeps.jobportal.repository.JobPostActivityRepository;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepo;

    public JobPostActivityService(JobPostActivityRepository spingJobPostActivityRepository) {
        this.jobPostActivityRepo = spingJobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepo.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter) {
        System.out.println("whats the int?");
        System.out.println(recruiter);
        List<IRecruiterJobs> recruiterJobsDtos = jobPostActivityRepo.getRecruiterJobs(recruiter);
        // this code here is proof that the activity exist, its the query thats not
        // working, we'll get back to this later!
        List<Integer> jobPostIds = jobPostActivityRepo.getJobPostIdsByRecruiter(recruiter);
        System.out.println(jobPostIds);
        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
        for (IRecruiterJobs rec : recruiterJobsDtos) {
            JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
            System.out.println(rec.toString());
            JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
            System.out.println(comp.toString());
            recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJobPostId(),
                    rec.getJobTitle(), loc, comp));

        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(int id) {
        return jobPostActivityRepo.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
    }
}
