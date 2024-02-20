package com.project.inventory.auth.user.service;

import com.project.inventory.auth.permission.dto.PermissionResponse;
import com.project.inventory.auth.user.entity.User;

import java.util.List;

public interface UserServiceShared {
    User getUserByUserName(String userName);

    List<PermissionResponse> getAllPermission(String userName);

    void saveUser(User user);
}
