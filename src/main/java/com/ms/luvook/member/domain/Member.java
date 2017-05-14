package com.ms.luvook.member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by vivie on 2017-04-15.
 */
@Data
@AllArgsConstructor
public class Member {

    private String name;
    private int age;
    private String addr;

}
