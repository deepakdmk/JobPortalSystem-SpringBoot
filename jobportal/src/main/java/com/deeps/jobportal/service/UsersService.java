package com.deeps.jobportal.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.JobSeekerProfile;
import com.deeps.jobportal.entity.RecruiterProfile;
import com.deeps.jobportal.entity.Users;
import com.deeps.jobportal.repository.JobSeekerProfileRepository;
import com.deeps.jobportal.repository.RecruiterProfileRepository;
import com.deeps.jobportal.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepo;
    private final JobSeekerProfileRepository jobSeekerProfileRepo;
    private final RecruiterProfileRepository recruiterProfileRepo;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository springUsersRepository, JobSeekerProfileRepository springJobSeekerProfileRepo,
            RecruiterProfileRepository springRecruiterProfileRepository, PasswordEncoder springPasswordEncoder) {
        this.usersRepo = springUsersRepository;
        this.jobSeekerProfileRepo = springJobSeekerProfileRepo;
        this.recruiterProfileRepo = springRecruiterProfileRepository;
        this.passwordEncoder = springPasswordEncoder;

    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUsers = usersRepo.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            recruiterProfileRepo.save(new RecruiterProfile(savedUsers));
        } else {
            jobSeekerProfileRepo.save(new JobSeekerProfile(savedUsers));
        }
        return savedUsers;
    }

    public Optional<Users> getUsersByEmail(String email) {
        return usersRepo.findByEmail(email);
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
            int userId = users.getUserId();

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile recruiterProfile = recruiterProfileRepo.findById(userId)
                        .orElse(new RecruiterProfile());
                return recruiterProfile;
            } else {
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepo.findById(userId)
                        .orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
            return user;
        }
        return null;
    }

}