package com.ms.luvook.member;

import com.ms.luvook.common.domain.Result;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
import com.ms.luvook.member.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.slf4j.Slf4j;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by vivie on 2017-06-16.
 */
@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignInTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void signup() throws Exception{
        log.info("#################################");
        MemberMaster memberMaster =
                new MemberMaster("김민수",  "test@naver.com", "123123",
                        "반갑습니다.","img", "bgImg", MemberType.USER, new Date(), new Date(), new Date());

        memberService.signup(memberMaster);
    }
}
