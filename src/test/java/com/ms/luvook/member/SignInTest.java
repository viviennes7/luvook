package com.ms.luvook.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.common.service.JwtService;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberServiceImpl;

/**
 * Created by vivie on 2017-06-16.
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignInTest {

    @Autowired
    private MemberServiceImpl memberService;
    
    @Autowired
    private JwtService jwtService;
    
    private MemberMaster memberMaster;
    
    @Before
    public void setup(){
    	memberMaster =new MemberMaster("test_nickname",  "test1@naver.com", "123123", null, null, null, null);
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
        String email = "test1@naver.com";
        String password = "123123";
        
        //When
        memberService.signup(memberMaster);
        MemberMaster login = memberService.signin(email, password);
        
        //Then
        assertNotNull(login);
    }
    
    /*@Test
    public void signinJwt() throws Exception{
    	//Given
    	MemberMaster signedupMember = memberService.signup(memberMaster);
		String jwt = jwtService.createMember(signedupMember);
		
		//When
		MemberMaster signedinMember = memberService.signinJwt();
		
		//Then
		assertThat(signedinMember.getEmail(), is(signedupMember.getEmail()));
    }*/
    
    @Test(expected = IllegalStateException.class)
    public void failSignin() throws Exception{
    	//Given
        String email = "testtesttest123@naver.com";
        String password = "123123";
        
        //When, Then
        memberService.signin(email, password);
    }
}
