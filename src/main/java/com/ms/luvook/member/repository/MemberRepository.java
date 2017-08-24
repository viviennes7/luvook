package com.ms.luvook.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.luvook.member.domain.MemberMaster;

/**
 * Created by vivie on 2017-06-08.
 */
public interface MemberRepository extends JpaRepository<MemberMaster, Integer>{
    MemberMaster findByEmail(String email);
    
    MemberMaster findByNickname(String nickname);
}
