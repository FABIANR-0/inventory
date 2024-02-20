package com.project.inventory.auth.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    @JsonProperty(value = "token")
    private String jwt;

    public LoginResponse(String jwt){
        this.jwt = jwt;
    }
}
