package com.ms.luvook.member.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.luvook.common.domain.Result;
import com.ms.luvook.common.service.JwtService;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberService;

/**
 * Created by vivie on 2017-06-08.
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public Result signup(@RequestBody MemberMaster memberMaster){
        Result result = Result.successInstance();
        MemberMaster createdMember = memberService.signup(memberMaster);
        result.setData(createdMember);
        return result;
    }

    @GetMapping("/validate")
    public Result validate(String email){
        boolean alreadyExist = memberService.isExist(email);
        Result result = Result.successInstance();
        if(alreadyExist == true){
            result.fail().setMessage("E-mail이 이미 존재합니다.");
        }
        return result;
    }

    @PostMapping(value="/signin")
    public Result signin(String email, String password, HttpServletResponse response){
    	Result result = Result.successInstance();
        MemberMaster loginMember = memberService.signin(email, password);
        String token = jwtService.createMember(loginMember);
        response.setHeader("Authorization", token);
        result.setData(loginMember);
        return result;
    }
    
    @PostMapping(value="/signin/jwt")
    public Result signin(){
    	Result result = Result.successInstance();
    	MemberMaster loginMember = memberService.signinJwt();
    	result.setData(loginMember);
    	return result;
    }
    
    @PutMapping(value="/info")
    public Result updateInfo(String nickname, String password){
    	Result result = Result.successInstance();
    	memberService.updateInfo(nickname, password);
    	
    	return result;
    	
    }
    
    @PostMapping(value="/info/img")
    public Result uploadProfileImg(String encodeImg){
    	Result result = Result.successInstance();
    	memberService.uploadProfileImg(encodeImg);
    	return result;
    }
}