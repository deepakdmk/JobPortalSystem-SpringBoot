package com.deeps.jobportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deeps.jobportal.entity.UsersType;
import com.deeps.jobportal.repository.UsersTypeRepository;

@Service
public class UsersTypeService {

    private final UsersTypeRepository userTypeRepo;

    public UsersTypeService(UsersTypeRepository springUsersTypeRepository) {
        userTypeRepo = springUsersTypeRepository;
    }

    public List<UsersType> getAll() {
        return userTypeRepo.findAll();
    }
}
