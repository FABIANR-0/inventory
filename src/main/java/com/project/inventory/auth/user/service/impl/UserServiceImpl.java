package com.project.inventory.auth.user.service.impl;


import com.project.inventory.auth.user.repository.UserRepository;
import com.project.inventory.auth.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
