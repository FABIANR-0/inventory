package com.project.inventory.auth.role.controller;

import com.project.inventory.auth.role.dto.RoleCreate;
import com.project.inventory.auth.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, description =" Create role" )
    @ApiResponse(responseCode = "201", description = "created")
    @PreAuthorize(value = "hasPermission('HttpStatus','CREATE_ROLE')")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid RoleCreate request){
        roleService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
