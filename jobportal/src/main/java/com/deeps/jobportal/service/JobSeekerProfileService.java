package com.deeps.jobportal.service;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.JobSeekerProfile;
import com.deeps.jobportal.entity.Users;
import com.deeps.jobportal.repository.JobSeekerProfileRepository;
import com.deeps.jobportal.repository.UsersRepository;

@Service
public class JobSeekerProfileService {

	private final JobSeekerProfileRepository jobSeekerProfileRepo;
	private final UsersRepository usersRepo;

	public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepo,
			UsersRepository springUsersRepository) {
		this.jobSeekerProfileRepo = jobSeekerProfileRepo;
		this.usersRepo = springUsersRepository;
	}

	public Optional<JobSeekerProfile> getOne(Integer id) {
		return jobSeekerProfileRepo.findById(id);
	}

	public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
		return jobSeekerProfileRepo.save(jobSeekerProfile);
	}

	public JobSeekerProfile getCurrentSeekerProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users users = usersRepo.findByEmail(currentUsername)
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			Optional<JobSeekerProfile> seekerProfile = getOne(users.getUserId());
			return seekerProfile.orElse(null);
		} else
			return null;
	}

}
