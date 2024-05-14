package com.simba.libraryapi.rest.service.impl;

import com.simba.libraryapi.commons.utils.PaginationPayloadUtility;
import com.simba.libraryapi.commons.ResponseCode;
import com.simba.libraryapi.commons.utils.ResponsePayloadUtility;
import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.domain.dto.pagination.PagedResponse;
import com.simba.libraryapi.domain.dto.patron.GetPatronResponse;
import com.simba.libraryapi.domain.dto.patron.SaveOrUpdatePatronRequest;
import com.simba.libraryapi.domain.entity.Patron;
import com.simba.libraryapi.domain.entity.Permission;
import com.simba.libraryapi.domain.entity.Role;
import com.simba.libraryapi.domain.repository.PatronRepository;
import com.simba.libraryapi.domain.repository.RoleRepository;
import com.simba.libraryapi.rest.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;

    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public BaseResponse saveOrUpdatePatron(SaveOrUpdatePatronRequest saveOrUpdatePatronRequest) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Patron patron;
            if (ObjectUtils.isEmpty(saveOrUpdatePatronRequest.getPatronId())) {
                patron = new Patron();
                createOrUpdatePatronInformation(patron, saveOrUpdatePatronRequest);
                patronRepository.save(patron);
                baseResponse.setCode(ResponseCode.CREATED);
                baseResponse.setMessage("Operation successful");
            } else {
                patron = patronRepository.findPatronById(saveOrUpdatePatronRequest.getPatronId());
                if (!ObjectUtils.isEmpty(patron)) {
                    createOrUpdatePatronInformation(patron, saveOrUpdatePatronRequest);
                } else  {
                    baseResponse.setCode(ResponseCode.NOT_FOUND);
                    baseResponse.setMessage("Book not found");
                }
            }
        } catch (Exception e) {
            baseResponse.setCode(ResponseCode.BAD_REQUEST);
            baseResponse.setMessage("Something went wrong, please try again");
        }
        return baseResponse;
    }

    private void createOrUpdatePatronInformation(Patron patron, SaveOrUpdatePatronRequest saveOrUpdatePatronRequest) {
        patron.setName(saveOrUpdatePatronRequest.getName());
        patron.setEmailAddress(saveOrUpdatePatronRequest.getEmailAddress());
        patron.setPhoneNumber(saveOrUpdatePatronRequest.getPhoneNumber());
        patron.setRoles(createRolesAndPermissions());
        patron.setPassword(passwordEncoder.encode(saveOrUpdatePatronRequest.getPassword()));
    }

    Collection<Role> createRolesAndPermissions() {
        Role role = new Role();
        role.setName("patron");
        role.setDescription("patron");
        Role sr = roleRepository.save(role);
        return List.of(sr);
    }

    @Override
    public BaseResponse getPatronById(Long patronId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Patron patron = patronRepository.findPatronById(patronId);
            if (ObjectUtils.isEmpty(patron)) {
                baseResponse.setCode(ResponseCode.NOT_FOUND);
                baseResponse.setMessage("Patron not found for id: " + patronId);
            } else {
                GetPatronResponse getPatronResponse = new GetPatronResponse();
                getPatronResponse.setName(patron.getName());
                getPatronResponse.setEmail(patron.getEmailAddress());
                baseResponse.setCode(ResponseCode.OK);
                baseResponse.setMessage("Operation successful");
                baseResponse.setPayload(getPatronResponse);
            }
        } catch (Exception e) {
            baseResponse.setCode(ResponseCode.BAD_REQUEST);
            baseResponse.setMessage("Something went wrong, please try again");
        }
        return baseResponse;
    }

    @Override
    public BaseResponse getAllPatrons(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Patron> patronPage = patronRepository.findAll(pageable);
        if (patronPage.isEmpty()) {
            return new BaseResponse(ResponseCode.NOT_FOUND, "No available patron were found");
        }
        Page<GetPatronResponse> pagedAvailablePatron = patronPage.map(eachPatron -> {
            GetPatronResponse patron = new GetPatronResponse();
            patron.setName(eachPatron.getName());
            patron.setEmail(eachPatron.getEmailAddress());
            return patron;
        });
        PagedResponse<GetPatronResponse> pagedResponse = PaginationPayloadUtility.resolvePaginationMetaData(pagedAvailablePatron, pageNumber, pageSize, "Successful", ResponseCode.OK, "Fetched available books");
        return ResponsePayloadUtility.createSuccessResponse(pagedResponse,"Operation completed successfully");
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        final Optional<Patron> optionalUser = patronRepository.findPatronByEmailAddress(emailAddress);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("user does not exist");
        }
        var foundUser = optionalUser.get();
        return org.springframework.security.core.userdetails.User.withUsername(foundUser.getEmailAddress())
                .password(foundUser.getPassword()).accountExpired(false).accountLocked(false).credentialsExpired(false)
                .disabled(false).authorities(getAuthorities(foundUser.getRoles())).build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPermissions());
        }
        for (Permission permission : collection) {
            privileges.add(permission.getName());
        }
        return privileges;
    }

    @Override
    public BaseResponse findPatronByEmail(String emailAddress) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Optional<Patron> patronOptional = patronRepository.findPatronByEmailAddress(emailAddress);
            if (patronOptional.isPresent()) {
                Patron patron = patronOptional.get();
                GetPatronResponse getPatronResponse = new GetPatronResponse();
                getPatronResponse.setName(patron.getName());
                getPatronResponse.setEmail(patron.getEmailAddress());
                baseResponse.setCode(ResponseCode.OK);
                baseResponse.setMessage("Operation successful");
                baseResponse.setPayload(getPatronResponse);
            } else {
                baseResponse = new BaseResponse(ResponseCode.NOT_FOUND, "Patron not found with email address: " + emailAddress);
            }
        } catch (Exception e) {
            baseResponse = new BaseResponse(ResponseCode.BAD_REQUEST, "Something went wrong, please try again");
        }
        return baseResponse;
    }

    @Override
    public BaseResponse deletePatron(Long patronId) {
        patronRepository.deleteById(patronId);
        return new BaseResponse(ResponseCode.OK, "Patron deleted successfully");
    }
}
