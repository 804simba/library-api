package com.simba.libraryapi.domain.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse implements Serializable {

    @JsonProperty("response_code")
    private int code;

    @JsonProperty("response_message")
    private String message;

    @JsonProperty("response_payload")
    private Object payload;

    @JsonProperty("response_time")
    private LocalDateTime time = LocalDateTime.now();

    public BaseResponse() {
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(int code, String message, Object payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
        this.time = LocalDateTime.now();
    }
}
