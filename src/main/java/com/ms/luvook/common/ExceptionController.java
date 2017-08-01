package com.ms.luvook.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ms.luvook.common.domain.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by vivie on 2017-06-16.
 */

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        final String errorMsg = e.getMessage();

        Result result = Result
                        .newInstance()
                        .fail();

        if(errorMsg == null){
            result.setMessage(Result.SERVER_ERROR_MESSAGE);
        }else{
            result.setMessage(errorMsg);
        }
        //log.error("Error ::: {}", e.getMessage());
        e.printStackTrace(); //테스트 용도로 사용
        return result;
    }
}
