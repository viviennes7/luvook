package com.ms.luvook.common.error;

import com.ms.luvook.common.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/{errorCode}")
    public Result error(@PathVariable int errorCode){
        Result result = new Result();
        HttpStatus errorStatus = HttpStatus.valueOf(errorCode);
        result.setStatusCode(errorStatus);
        result.setMessage(errorStatus.getReasonPhrase());

        return result;
    }
}
