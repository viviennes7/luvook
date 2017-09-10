package com.ms.luvook.member.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ms.luvook.member.domain.MemberMaster;

/**
 * Created by vivie on 2017-06-08.
 */
@Service
public interface MemberService {

    MemberMaster signup(MemberMaster memberMaster);

    boolean isExist(String email);

    MemberMaster signin(String email, String password);
    
    MemberMaster signinJwt();

	void updateInfo(String nickname, String password);

	MemberMaster findByMemberId(int memberId);

	void uploadProfileImg(String encodeImg);
}
