package com.simba.libraryapi.commons.utils;

import com.simba.libraryapi.commons.ResponseCode;
import com.simba.libraryapi.domain.dto.base.BaseResponse;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

public class ResponsePayloadUtility {
    public static BaseResponse createSuccessResponse(Object data, String... responseMessage) {
        return new BaseResponse(ResponseCode.OK, (!ObjectUtils.isEmpty(responseMessage) ? responseMessage[0] : "Operation Successful"), data,
                LocalDateTime.now());
    }

    public static BaseResponse createFailedResponse(String... responseMessage) {
        return new BaseResponse(ResponseCode.BAD_REQUEST, (!ObjectUtils.isEmpty(responseMessage) ? responseMessage[0] : "Operation Failed"));
    }
}
