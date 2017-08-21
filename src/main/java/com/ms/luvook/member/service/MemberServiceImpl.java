package com.ms.luvook.member.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.luvook.common.util.EntityUtils;
import com.ms.luvook.member.domain.LoginVo;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
import com.ms.luvook.member.repository.MemberRepository;

/**
 * Created by vivie on 2017-06-08.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    public MemberMaster signup(MemberMaster memberMaster) {
        String email = memberMaster.getEmail();
        if( this.isExist(email) ){
            throw new IllegalStateException("이미 계정이 존재합니다.");
        }
        
        String password = memberMaster.getPassword();
        String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt());
        memberMaster.setPassword(encodePassword);
        memberMaster.setMemberType(MemberType.USER);
        EntityUtils.initializeRegAndModDate(memberMaster);
        
        MemberMaster createdMember = memberRepository.save(memberMaster);
        return createdMember;
    }

    public boolean isExist(String email) {
        boolean isExist = false;
        MemberMaster member = memberRepository.findByEmail(email);
        if(member != null){
            isExist = true;
        }
        return isExist;
    }

    public MemberMaster signin(LoginVo loginVo) {
        MemberMaster memberMaster = memberRepository.findByEmail(loginVo.getEmail());
        String encodePassword = null;
        boolean isAccordPassword = false;
    
        if(memberMaster != null){
            encodePassword = memberMaster.getPassword();
            isAccordPassword = BCrypt.checkpw(loginVo.getPassword(), encodePassword);
        }

        if(memberMaster == null || !isAccordPassword){
            throw new IllegalStateException("로그인정보가 일치하지 않습니다.");
        }
        return memberMaster;
    }
}
