package com.ms.luvook.member.controller;

import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity signup(MemberMaster memberMaster){
        memberService.signup(memberMaster);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad");
    }
}

