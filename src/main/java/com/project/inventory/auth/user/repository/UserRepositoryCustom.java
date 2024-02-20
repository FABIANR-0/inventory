package com.project.inventory.auth.user.repository;

import com.project.inventory.auth.permission.dto.PermissionResponse;

import java.util.List;

public interface UserRepositoryCustom {
    List<PermissionResponse> getPermissionByUserName(String userName);
}
