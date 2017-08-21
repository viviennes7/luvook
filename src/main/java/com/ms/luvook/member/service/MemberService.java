package com.ms.luvook.member.service;

import org.springframework.stereotype.Service;

import com.ms.luvook.member.domain.LoginVo;
import com.ms.luvook.member.domain.MemberMaster;

/**
 * Created by vivie on 2017-06-08.
 */
@Service
public interface MemberService {

    MemberMaster signup(MemberMaster memberMaster);

    boolean isExist(String email);

    MemberMaster signin(LoginVo loginVo);
    
}
