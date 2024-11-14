package com.deeps.jobportal.controller;

import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.deeps.jobportal.entity.JobPostActivity;
import com.deeps.jobportal.entity.RecruiterProfile;
import com.deeps.jobportal.entity.Users;
import com.deeps.jobportal.service.JobPostActivityService;
import com.deeps.jobportal.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.deeps.jobportal.entity.RecruiterJobsDto;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    public JobPostActivityController(UsersService springUsersService,
            JobPostActivityService springJobPostActivityService) {
        this.usersService = springUsersService;
        this.jobPostActivityService = springJobPostActivityService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model) {

        Object currentUserProfile = usersService.getCurrentUserProfile();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {

                List<RecruiterJobsDto> recruiterJobs = jobPostActivityService
                        .getRecruiterJobs(((RecruiterProfile) currentUserProfile).getUserAccountId());
                System.out.println(recruiterJobs.toString());
                model.addAttribute("jobPost", recruiterJobs);
            }

        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model) {
        System.out.println("here");
        Users user = usersService.getCurrentUser();
        if (user != null) {
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard/";
    }

    @PostMapping("dashboard/edit/{id}")
    public String editJob(@PathVariable("id") int id, Model model) {
        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }

}
