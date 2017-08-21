package com.ms.luvook.member.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.luvook.common.domain.Result;
import com.ms.luvook.common.service.JwtService;
import com.ms.luvook.member.domain.LoginVo;
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
    public Result signup(MemberMaster memberMaster){
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
    public Result signin(@RequestBody LoginVo loginVo, HttpServletResponse response){
        MemberMaster loginMember = memberService.signin(loginVo);
        String token = jwtService.createMember(loginMember);
        response.setHeader("Authorization", token);
        return Result.successInstance();
    }
}