package com.simba.libraryapi.domain.dto.patron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveOrUpdatePatronRequest {

    @JsonProperty("patron_id")
    private Long patronId;

    @JsonProperty("patron_name")
    @NotEmpty(message = "patron_name cannot be empty")
    private String name;

    @JsonProperty("patron_email_address")
    @NotEmpty(message = "patron_email_address cannot be empty")
    private String emailAddress;

    @JsonProperty("patron_phone_number")
    @NotEmpty(message = "patron_phone_number cannot be empty")
    private String phoneNumber;

    @JsonProperty("patron_password")
    @NotEmpty(message = "patron_password cannot be empty")
    private String password;
}
