package com.ms.luvook.common.service;

import com.ms.luvook.member.domain.MemberMaster;

public interface JwtService {
	String createMember(MemberMaster member);
	boolean isUsable(String jwt);
}
