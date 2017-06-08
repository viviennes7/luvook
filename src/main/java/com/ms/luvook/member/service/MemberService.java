package com.ms.luvook.member.service;

import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
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

    public boolean signup(MemberMaster memberMaster){
        int id = memberRepository.save(memberMaster).getId();
        return true;
    }
}
