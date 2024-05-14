package com.simba.libraryapi.rest.service;

import com.simba.libraryapi.domain.dto.base.BaseResponse;

public interface AuthService {
    BaseResponse authenticate(String emailAddress, String password);
}
