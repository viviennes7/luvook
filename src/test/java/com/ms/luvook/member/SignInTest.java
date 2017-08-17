package com.ms.luvook.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
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
    
    private MemberMaster memberMaster;
    
    @Before
    public void setup(){
    	memberMaster =new MemberMaster("김민수",  "test1@naver.com", "123123", "img", MemberType.USER, new Date(), new Date());
    }
    
    @Test
    public void signup() throws Exception{
    	//When
        MemberMaster signedupMember = memberService.signup(memberMaster);

        //Then
        assertEquals(memberMaster, signedupMember);
    }

    @Test(expected = IllegalStateException.class)
    public void duplicateSignup(){
    	//When, Then
        memberService.signup(memberMaster);
        memberService.signup(memberMaster);
    }

    @Test
    public void signin() throws Exception{
    	//Given
        final String email = "test1@naver.com";
        final String passwd = "123123";
        
        //When
        memberService.signup(memberMaster);
        MemberMaster login = memberService.signin(email, passwd);
        
        //Then
        assertNotNull(login);
    }
    
    @Test(expected = IllegalStateException.class)
    public void failSignin() throws Exception{
    	//Given
        final String email = "testtesttest123@naver.com";
        final String passwd = "123123";
        
        //When, Then
        memberService.signin(email, passwd);
    }
}
