package com.deeps.jobportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.JobPostActivity;
import com.deeps.jobportal.entity.JobSeekerApply;
import com.deeps.jobportal.entity.JobSeekerProfile;
import com.deeps.jobportal.repository.JobSeekerApplyRepository;

@Service
public class JobSeekerApplyService {

	private final JobSeekerApplyRepository jobSeekerApplyRepo;

	public JobSeekerApplyService(JobSeekerApplyRepository springJobSeekerApplyRepo) {
		this.jobSeekerApplyRepo = springJobSeekerApplyRepo;
	}

	public List<JobSeekerApply> getCandidateJobs(JobSeekerProfile userAccountId) {
		return jobSeekerApplyRepo.findByUserId(userAccountId);
	}

	public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
		return jobSeekerApplyRepo.findByJob(job);
	}

	public void addNew(JobSeekerApply jobSeekerApply) {
		jobSeekerApplyRepo.save(jobSeekerApply);
	}
}
