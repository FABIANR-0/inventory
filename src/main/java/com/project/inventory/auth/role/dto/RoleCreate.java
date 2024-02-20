package com.project.inventory.auth.role.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleCreate {

    @JsonProperty("role_name")
    @NotBlank
    private String roleName;

}
