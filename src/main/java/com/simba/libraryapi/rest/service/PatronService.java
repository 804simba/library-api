package com.simba.libraryapi.rest.service;

import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.domain.dto.patron.SaveOrUpdatePatronRequest;
import com.simba.libraryapi.domain.entity.Patron;
import org.springframework.security.core.userdetails.UserDetails;

public interface PatronService {
    BaseResponse saveOrUpdatePatron(SaveOrUpdatePatronRequest saveOrUpdatePatronRequest);

    BaseResponse getPatronById(Long patronId);

    BaseResponse getAllPatrons(int pageSize, int pageNumber);

    BaseResponse deletePatron(Long patronId);

    UserDetails loadUserByUsername(String emailAddress);

    BaseResponse findPatronByEmail(String emailAddress);
}
