package com.ms.luvook.common.error;

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

        Result result = new Result()
                        	.fail();

        if(errorMsg == null){
            result.setMessage(Result.SERVER_ERROR_MESSAGE);
        }else{
            result.setMessage(errorMsg);
        }

        if(log.isInfoEnabled()){
            e.printStackTrace();
        }else{
            log.error("Error ::: {}", e.getMessage());
        }

        return result;
    }
}
