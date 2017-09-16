package com.ms.luvook.common.service.jwt;

import java.util.Map;

import com.ms.luvook.member.domain.MemberMaster;

public interface JwtService {
	String createMember(MemberMaster member);
	Map<String, Object> get(String key);
	int getMemberId();
	boolean isUsable(String jwt);
	
}
