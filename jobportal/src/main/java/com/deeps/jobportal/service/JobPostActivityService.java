package com.deeps.jobportal.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		System.out.println("whats the recruiter job id ? - " + recruiter);
		List<IRecruiterJobs> recruiterJobsDtos = jobPostActivityRepo.getRecruiterJobs(recruiter);
		// this code here is proof that the activity exist, its the query thats not
		// working, we'll get back to this later!
		List<Integer> jobPostIds = jobPostActivityRepo.getJobPostIdsByRecruiter(recruiter);

		System.out.println("This needs to not be empty to work" + recruiterJobsDtos);
		System.out.println("Query to check how many job posts under this recruiter, answer = " + jobPostIds);
		List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
		for (IRecruiterJobs rec : recruiterJobsDtos) {
			JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
//			System.out.println(rec.toString());
			JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
//			System.out.println(comp.toString());
			recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(),
					rec.getJob_title(), loc, comp));

		}
		return recruiterJobsDtoList;
	}

	public JobPostActivity getOne(int id) {
		return jobPostActivityRepo.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
	}

	public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote,
			LocalDate searchDate) {
		return Objects.isNull(searchDate) ? jobPostActivityRepo.searchWithoutDate(job, location, remote, type)
				: jobPostActivityRepo.search(job, location, remote, type, searchDate);
	}

	public List<JobPostActivity> getAll() {
		return jobPostActivityRepo.findAll();
	}

}
