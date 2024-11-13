package com.deeps.jobportal.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.Users;
import com.deeps.jobportal.repository.UsersRepository;
import com.deeps.jobportal.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepo;

    public CustomUserDetailsService(UsersRepository springUsersRepo) {
        this.usersRepo = springUsersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find users"));
       return new CustomUserDetails(user);
    }

}
