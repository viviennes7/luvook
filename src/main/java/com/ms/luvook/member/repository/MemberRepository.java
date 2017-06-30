package com.ms.luvook.member.repository;

import com.ms.luvook.member.domain.MemberMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vivie on 2017-06-08.
 */
public interface MemberRepository extends JpaRepository<MemberMaster, Long>{
    MemberMaster findByEmail(String email);
    MemberMaster findByEmailAndPassword(String email, String password);
}
