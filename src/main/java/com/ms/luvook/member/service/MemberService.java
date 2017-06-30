package com.ms.luvook.member.service;

import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Date;

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

        if(validateEmail(email) == false){
            throw new IllegalArgumentException("이미 계정이 존재합니다.");
        }

        final String password = memberMaster.getPassword();
        final String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt());

        memberMaster
            .initializeRegAndModDate()
            .setPassword(encodePassword);

        final MemberMaster createdMember = memberRepository.save(memberMaster);

        return createdMember;
    }

    public boolean validateEmail(String email) {
        boolean alreadyExist = false;
        final MemberMaster member = memberRepository.findByEmail(email);

        if(member != null){
            alreadyExist = true;
        }

        return alreadyExist;
    }

    public boolean signin(String email, String password) {
        boolean isSuccess = false;

        final MemberMaster memberMaster = memberRepository.findByEmail(email);
        final String encodePassword = memberMaster.getPassword();

        BCrypt.checkpw(password, encodePassword);

        log.info("encodePass ::: {}", encodePassword);
        log.info("memberMaster ::: {}", memberMaster);

        if(memberMaster != null){
            isSuccess = true;
        }

        return isSuccess;
    }
}
