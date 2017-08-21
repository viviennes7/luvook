package com.ms.luvook.common.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.ms.luvook.member.domain.MemberMaster;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("jwtService")
public class JwtServiceImpl implements JwtService{
	
	public String createMember(MemberMaster member){
		String jwt = Jwts.builder()
				  .setHeaderParam("typ", "JWT")
				  .setHeaderParam("regDate", System.currentTimeMillis())
				  .setSubject("user")
				  .claim("member", member)
				  .signWith(SignatureAlgorithm.HS256, this.generateKey())
				  .compact();
		return jwt;
	}
	
	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = "luvookSecret".getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		
		return key;
	}

	@Override
	public boolean isUsable(String jwt) {
		try{
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(jwt);
			return true;
			
		}catch (Exception e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error(e.getMessage());
			}
			return false;
		}
	}
}
