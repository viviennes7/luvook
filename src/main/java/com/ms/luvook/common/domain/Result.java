package com.ms.luvook.common.domain;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by vivie on 2017-06-16.
 */

@Data
public class Result {
    public static final String SUCCESS_MESSAGE = "성공했습니다.";
    public static final String SERVER_ERROR_MESSAGE = "서버에 문제가 발생했습니다.";

    private HttpStatus statusCode;
    private String message;
    private String json;
    private int totalCount;

    private Result(){}

    public static Result newInstance(){
        return new Result();
    }

    public Result success(){
        statusCode = HttpStatus.OK;
        message = SUCCESS_MESSAGE;
        return this;
    }

    public Result fail(){
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        message = SERVER_ERROR_MESSAGE;
        return this;
    }

    public int getStatusCode() {
        return statusCode.value();
    }
}
