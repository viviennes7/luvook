package com.ms.luvook.jwt;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.ms.luvook.common.service.jwt.JwtService;
import com.ms.luvook.common.service.jwt.JwtServiceImpl;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTests {
	private JwtService jwtService = new JwtServiceImpl();
	private MemberMaster memberMaster;
    
    @Before
    public void setup(){
    	memberMaster = new MemberMaster("김민수",  "test1@naver.com", "123123", "img", MemberType.USER, new Date(), new Date());
    }
    
	@Test
	public void createMemberTest() throws Exception{
		//When
		String jwt = jwtService.createMember(memberMaster);
		Jws<Claims> claims = Jwts.parser()
				  .setSigningKey("luvookSecret".getBytes("UTF-8"))
				  .parseClaimsJws(jwt);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> tokenMember = (LinkedHashMap<String, Object>)claims.getBody().get("member");
		
		//Then
		log.info("jwt ::: {}", jwt);
		log.info("tokenMember::: {}", tokenMember);
		assertThat(memberMaster.getEmail(), is(tokenMember.get("email")));
		
	}
	
	@Test
	public void getJwtTest(){
		//Given
		String jwt = jwtService.createMember(memberMaster);
		
		//When
		Map<String, Object> memberMap = jwtService.get("member");
		
		//Then
		assertThat(memberMap.get("email"), is("test1@naver.com"));
	}
	
}
