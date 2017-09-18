package com.ms.luvook.member;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberServiceImpl;

/**
 * Created by vivie on 2017-06-16.
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberTests {

    @Autowired
    private MemberServiceImpl memberService;
    
    private MemberMaster memberMaster;
    
    @Before
    public void setup(){
    	memberMaster = new MemberMaster("%test_nickname",  "%test1@naver.com", "123123", null, null, null, null);
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
    
    @Test(expected = IllegalStateException.class)
    public void isExistEmail(){
    	//Given
    	memberService.signup(memberMaster);
    	String email = memberMaster.getEmail();
    	
    	//When
    	boolean isExist = memberService.isExist(email);
    	
    	//Then
    	assertThat(isExist, is(true));
    	memberService.validate(email);
    }
    
    @Test
    public void isNotExistEmail(){
    	//Given
    	String email = "random@email.com";
    	
    	//When
    	boolean isExist = memberService.isExist(email);
    	
    	//Then
    	assertThat(isExist, is(false));
    	memberService.validate(email);
    }

    @Test
    public void signin() throws Exception{
    	//Given
        String email = memberMaster.getEmail();
        String password = memberMaster.getPassword();
        
        //When
        memberService.signup(memberMaster);
        MemberMaster login = memberService.signin(email, password);
        
        //Then
        assertNotNull(login);
    }
    
    @Test(expected = NullPointerException.class)
    public void signinWrongEmail() throws Exception{
    	//Given
    	memberService.signup(memberMaster);
        String email = "%wrongEmail@naver.com";
        String password = memberMaster.getPassword();
        
        //When, Then
        memberService.signin(email, password);
    }
    
    @Test(expected = IllegalStateException.class)
    public void signinWrongPassword(){
    	//Given
    	memberService.signup(memberMaster);
        String email = memberMaster.getEmail();
        String password = "wrongPassword";
        
        //When, Then
        memberService.signin(email, password);
    }
    
    @Test
    public void updateNickname(){
    	//Given
    	MemberMaster signedMember = memberService.signup(memberMaster);
    	String anotherNickname = "$TestNickname$";
    	String password = "";
    	int memberId = signedMember.getMemberId();
    	
    	//When
    	memberService.updateInfo(anotherNickname, password, memberId);
    	MemberMaster updatedMember = memberService.findByMemberId(memberId);
    	
    	//Then
    	assertThat(anotherNickname, is(updatedMember.getNickname()));
    }
    
    @Test(expected = IllegalStateException.class)
    public void updateAlreadyExistNickname(){
    	//Given
    	MemberMaster previousMember = new MemberMaster("%pre_test_nickname",  "%test1@naver.com", "123123", null, null, null, null);
    	memberService.signup(previousMember);
    	MemberMaster signedMember = memberService.signup(memberMaster);
    	String password = "";
    	int memberId = signedMember.getMemberId();
    	
    	//When, Then
    	memberService.updateInfo(previousMember.getNickname(), password, memberId);
    	MemberMaster updatedMember = memberService.findByMemberId(memberId);
    }
    
    @Test
    public void updatePassword(){
    	//Given
    	MemberMaster signedMember = memberService.signup(memberMaster);
    	String email = signedMember.getEmail();
    	String nickname = signedMember.getNickname();
    	String changePassword = "change";
    	int memberId = signedMember.getMemberId();
    	
    	//When
    	memberService.updateInfo(nickname, changePassword, memberId);
    	
    	//Then
    	memberService.signin(email, changePassword);
    }
}