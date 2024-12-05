package com.deeps.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.jobportal.entity.JobPostActivity;
import com.deeps.jobportal.entity.JobSeekerProfile;
import com.deeps.jobportal.entity.JobSeekerSave;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

	List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

	List<JobSeekerSave> findByJob(JobPostActivity job);
}
