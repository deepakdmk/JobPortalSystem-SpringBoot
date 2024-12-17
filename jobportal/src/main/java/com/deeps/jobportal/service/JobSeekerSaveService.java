package com.deeps.jobportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.JobPostActivity;
import com.deeps.jobportal.entity.JobSeekerProfile;
import com.deeps.jobportal.entity.JobSeekerSave;
import com.deeps.jobportal.repository.JobSeekerSaveRepository;

@Service
public class JobSeekerSaveService {

	private final JobSeekerSaveRepository jobSeekerSaveRepo;

	public JobSeekerSaveService(JobSeekerSaveRepository springJobSeekerSaveRepo) {
		this.jobSeekerSaveRepo = springJobSeekerSaveRepo;
	}

	public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
		return jobSeekerSaveRepo.findByUserId(userAccountId);
	}

	public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
		return jobSeekerSaveRepo.findByJob(job);
	}

	public void addNew(JobSeekerSave jobSeekerSave) {
		jobSeekerSaveRepo.save(jobSeekerSave);
	}

}
