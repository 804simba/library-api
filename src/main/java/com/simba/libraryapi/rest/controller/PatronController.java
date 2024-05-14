package com.simba.libraryapi.rest.controller;

import com.simba.libraryapi.commons.ApiRoute;
import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.domain.dto.patron.SaveOrUpdatePatronRequest;
import com.simba.libraryapi.rest.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ApiRoute.PATRONS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PatronController {
    private final PatronService patronService;

    @PostMapping(value = "/save")
    public BaseResponse saveOrUpdate(@RequestBody SaveOrUpdatePatronRequest saveOrUpdatePatronRequest) {
        return patronService.saveOrUpdatePatron(saveOrUpdatePatronRequest);
    }

    @PutMapping(value = "/update")
    public BaseResponse update(@RequestBody SaveOrUpdatePatronRequest saveOrUpdatePatronRequest) {
        return patronService.saveOrUpdatePatron(saveOrUpdatePatronRequest);
    }

    @GetMapping(value = "/{patronId}")
    public BaseResponse getPatron(@PathVariable Long patronId) {
        return patronService.getPatronById(patronId);
    }

    @GetMapping
    public BaseResponse getPatrons(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                 @RequestParam(name = "pageSize", defaultValue = "30") int pageSize) {
        return patronService.getAllPatrons(pageSize, pageNumber);
    }

    @DeleteMapping(value = "/delete/{patronId}")
    public BaseResponse delete(@PathVariable Long patronId) {
        return patronService.deletepatron(patronId);
    }
}
