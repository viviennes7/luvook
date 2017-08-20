package com.ms.luvook.member.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginVo {
    private String email;
    private String password;
}
