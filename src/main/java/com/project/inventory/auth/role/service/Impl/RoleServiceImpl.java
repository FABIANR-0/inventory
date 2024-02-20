package com.project.inventory.auth.role.service.Impl;

import com.project.inventory.auth.role.dto.RoleCreate;
import com.project.inventory.auth.role.entity.Role;
import com.project.inventory.auth.role.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public void create(RoleCreate request) {
        System.out.println("wdwd");
    }
}
