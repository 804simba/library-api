package com.simba.libraryapi.rest.service.impl;

import com.simba.libraryapi.commons.ResponseCode;
import com.simba.libraryapi.core.security.jwt.DefaultJwtService;
import com.simba.libraryapi.domain.dto.auth.AuthenticationToken;
import com.simba.libraryapi.domain.dto.auth.LoginResponse;
import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.domain.entity.Patron;
import com.simba.libraryapi.domain.repository.PatronRepository;
import com.simba.libraryapi.rest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PatronRepository patronRepository;

    private final PasswordEncoder passwordEncoder;

    private final DefaultJwtService defaultJwtService;

    @Override
    public BaseResponse authenticate(String emailAddress, String password) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            var patronOptional = patronRepository.findPatronByEmailAddress(emailAddress);
            if (patronOptional.isPresent()) {
                Patron patron = patronOptional.get();
                boolean isValidPassword = passwordEncoder.matches(password, patron.getPassword());
                if (!isValidPassword) {
                    loginResponse.setCode(ResponseCode.BAD_REQUEST);
                    loginResponse.setMessage("Incorrect password, please enter a valid password");
                }
                AuthenticationToken authenticationToken = defaultJwtService.generateToken(patron);
                loginResponse.setAuthenticationToken(authenticationToken);
                loginResponse.setCode(ResponseCode.OK);
                loginResponse.setMessage("Authentication successful");
            } else {
                loginResponse.setCode(ResponseCode.BAD_REQUEST);
                loginResponse.setMessage("User not found, please register");
            }

        } catch (Exception e) {
            loginResponse.setCode(ResponseCode.INTERNAL_ERROR);
            loginResponse.setMessage("Something went wrong, please try again later.");
        }
        return loginResponse;
    }
}
