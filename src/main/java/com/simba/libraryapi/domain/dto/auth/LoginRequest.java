package com.simba.libraryapi.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simba.libraryapi.domain.dto.base.BaseRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest extends BaseRequest {

    @JsonProperty(value = "username")
    @NotEmpty(message = "username must not be empty")
    private String username;

    @JsonProperty(value = "password")
    @NotEmpty(message = "password must not be empty")
    private String password;
}
