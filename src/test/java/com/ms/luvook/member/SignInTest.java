package com.ms.luvook.member;

import com.ms.luvook.common.domain.Result;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
import com.ms.luvook.member.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.slf4j.Slf4j;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by vivie on 2017-06-16.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignInTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void signup() throws Exception{
        MemberMaster memberMaster =
                new MemberMaster("김민수",  "test12@naver.com", "123123",
                        "반갑습니다.","img", "bgImg", MemberType.USER, new Date(), new Date(), new Date());

        memberService.signup(memberMaster);
    }

    @Test(expected = IllegalStateException.class)
    public void duplicateSignup(){
        MemberMaster memberMaster =
                new MemberMaster("김민수",  "test2@naver.com", "123123",
                        "반갑습니다.","img", "bgImg", MemberType.USER, new Date(), new Date(), new Date());

        memberService.signup(memberMaster);
        memberService.signup(memberMaster);
    }

    @Test
    public void signin() throws Exception{
        final String email = "viviennes7@naver.com";
        final String passwd = "456552";
        memberService.signin(email, passwd);
    }
    @Test(expected = IllegalStateException.class)
    public void fialSignin() throws Exception{
        final String email = "testtesttest123@naver.com";
        final String passwd = "123123";
        memberService.signin(email, passwd);
    }
}
