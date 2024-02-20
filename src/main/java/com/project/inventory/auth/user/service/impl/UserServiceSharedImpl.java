package com.project.inventory.auth.user.service.impl;

import com.project.inventory.auth.permission.dto.PermissionResponse;
import com.project.inventory.auth.user.entity.User;
import com.project.inventory.auth.user.repository.UserRepository;
import com.project.inventory.auth.user.service.UserServiceShared;
import com.project.inventory.common.exception.service.AuthenticationFailedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceSharedImpl implements UserServiceShared {

    private final UserRepository userRepository;

    public UserServiceSharedImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(()-> new AuthenticationFailedException("credenciales incorrectas"));
    }

    @Override
    public List<PermissionResponse> getAllPermission(String userName) {
        return userRepository.getPermissionByUserName(userName);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
