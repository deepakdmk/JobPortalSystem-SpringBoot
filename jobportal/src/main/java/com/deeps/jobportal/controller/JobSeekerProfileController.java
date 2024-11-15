package com.deeps.jobportal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deeps.jobportal.entity.Skills;
import com.deeps.jobportal.entity.Users;
import com.deeps.jobportal.repository.UsersRepository;
import com.deeps.jobportal.service.JobSeekerProfileService;
import com.deeps.jobportal.entity.JobSeekerProfile;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

    private JobSeekerProfileService jobSeekerProfileService;
    private UsersRepository usersRepo;

    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UsersRepository usersRepo) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersRepo = usersRepo;
    }

    @GetMapping("/")
    public String JobSeekerProfile(Model model) {
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepo.findByEmail(
                    authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            if (seekerProfile.isPresent()) {
                jobSeekerProfile = seekerProfile.get();
                if (jobSeekerProfile.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }
            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }
        return "job-seeker-profile";
    }

}
