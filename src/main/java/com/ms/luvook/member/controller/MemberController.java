package com.ms.luvook.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.luvook.common.domain.Result;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by vivie on 2017-06-08.
 */
@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public Result signup(MemberMaster memberMaster, HttpSession session){
        Result result = Result.successInstance();
        MemberMaster createdMember = memberService.signup(memberMaster);
        session.setAttribute("member",createdMember);

        return result;
    }

    @GetMapping("/validate")
    public Result validate(String email){
        boolean alreadyExist = memberService.isExist(email);
        Result result = Result.successInstance();

        if(alreadyExist == true){
            result.fail()
                   .setMessage("E-mail이 이미 존재합니다.");
        }
        return result;
    }

    @PostMapping("/signin")
    public Result signin(String email, String password, HttpSession session){
        final MemberMaster loginMember = memberService.signin(email, password);
        session.setAttribute("member",loginMember);
        return Result.successInstance();
    }
}