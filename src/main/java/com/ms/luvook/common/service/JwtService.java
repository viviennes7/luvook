package com.ms.luvook.common.service;

import java.util.Map;

import com.ms.luvook.member.domain.MemberMaster;

public interface JwtService {
	String createMember(MemberMaster member);
	Map<String, Object> get(String jwt, String key);
	boolean isUsable(String jwt);
	
}
