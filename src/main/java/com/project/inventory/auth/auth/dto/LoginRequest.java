package com.project.inventory.auth.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank
    @JsonProperty(value = "username")
    private String username;

    @NotBlank
    @JsonProperty(value = "password")
    private String password;
}
