package com.ms.luvook.member.service;

import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vivie on 2017-06-08.
 */
@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberMaster signup(MemberMaster memberMaster) {
        final String email = memberMaster.getEmail();
        if( this.isExist(email) ){
            throw new IllegalStateException("이미 계정이 존재합니다.");
        }
        final String password = memberMaster.getPassword();
        final String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt());
        memberMaster
            .setPassword(encodePassword);
        final MemberMaster createdMember = memberRepository.save(memberMaster);
        return createdMember;
    }

    public boolean isExist(String email) {
        boolean isExist = false;
        final MemberMaster member = memberRepository.findByEmail(email);
        if(member != null){
            isExist = true;
        }
        return isExist;
    }

    public MemberMaster signin(String email, String password) {

        final MemberMaster memberMaster = memberRepository.findByEmail(email);
        String encodePassword = null;
        boolean isSuccess = false;

        if(memberMaster != null){
            encodePassword = memberMaster.getPassword();
            isSuccess = BCrypt.checkpw(password, encodePassword);
        }

        if(memberMaster == null || !isSuccess){
            throw new IllegalStateException("로그인정보가 일치하지 않습니다.");
        }
        return memberMaster;
    }
}
