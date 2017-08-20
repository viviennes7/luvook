package com.ms.luvook.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ms.luvook.common.service.JwtService;
import com.ms.luvook.member.domain.LoginVo;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ms.luvook.common.domain.Result;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberService;

import java.util.Map;

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

    @PostMapping(value="/signin")
    public Result signin(@RequestBody LoginVo loginVo, HttpServletResponse response){
        System.out.println(loginVo);
        final MemberMaster loginMember = memberService.signin(loginVo);
        String token = jwtService.createMember(loginMember);
        response.setHeader("Authorization", token);
        return Result.successInstance();
    }
}