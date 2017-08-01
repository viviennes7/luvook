package com.ms.luvook.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
import com.ms.luvook.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by vivie on 2017-06-16.
 */
@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignInTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void signup() throws Exception{
        MemberMaster memberMaster =
                new MemberMaster("김민수",  "test1@naver.com", "123123",
                        "img", MemberType.USER, new Date(), new Date());

        MemberMaster signedupMember = memberService.signup(memberMaster);

        assertEquals(memberMaster, signedupMember);
    }

    @Test(expected = IllegalStateException.class)
    public void duplicateSignup(){
        MemberMaster memberMaster =
                new MemberMaster("김민수",  "test@naver.com", "123123",
                        "img", MemberType.USER, new Date(), new Date());

        memberService.signup(memberMaster);
        memberService.signup(memberMaster);
    }

    @Test
    public void signin() throws Exception{
        final String email = "signinTest@naver.com";
        final String passwd = "123123";
        MemberMaster memberMaster =
                new MemberMaster("김민수",  email, passwd,
                        "img", MemberType.USER, new Date(), new Date());
        memberService.signup(memberMaster);
        MemberMaster login = memberService.signin(email, passwd);
        assertNotNull(login);
        
    }
    
    @Test(expected = IllegalStateException.class)
    public void failSignin() throws Exception{
        final String email = "testtesttest123@naver.com";
        final String passwd = "123123";
        memberService.signin(email, passwd);
    }
}
