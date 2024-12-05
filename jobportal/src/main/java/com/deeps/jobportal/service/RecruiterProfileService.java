package com.deeps.jobportal.service;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.RecruiterProfile;
import com.deeps.jobportal.entity.Users;
import com.deeps.jobportal.repository.RecruiterProfileRepository;
import com.deeps.jobportal.repository.UsersRepository;

@Service
public class RecruiterProfileService {

	private final RecruiterProfileRepository recruiterProfileRepo;
	private final UsersRepository usersRepository;

	public RecruiterProfileService(RecruiterProfileRepository springRecruiterProfileRepository,
			UsersRepository springUserRepository) {
		this.recruiterProfileRepo = springRecruiterProfileRepository;
		this.usersRepository = springUserRepository;
	}

	public Optional<RecruiterProfile> getOne(Integer id) {
		return recruiterProfileRepo.findById(id);
	}

	public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
		return recruiterProfileRepo.save(recruiterProfile);
	}

	public RecruiterProfile getCurrentRecruiterProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users users = usersRepository.findByEmail(currentUsername)
					.orElseThrow(() -> new UsernameNotFoundException("user not found"));
			Optional<RecruiterProfile> recruiterProfile = getOne(users.getUserId());
			return recruiterProfile.orElse(null);

		} else
			return null;
	}

}
