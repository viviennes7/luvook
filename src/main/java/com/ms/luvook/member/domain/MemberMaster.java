package com.ms.luvook.member.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ms.luvook.board.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Created by vivie on 2017-06-08.
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "member_master")
public class MemberMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int memberId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Basic(fetch = FetchType.LAZY)
    private String password;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "member_type")
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(name = "reg_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Column(name = "mod_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;

    @OneToMany(mappedBy = "member")
    private List<Board> boards;

    public MemberMaster() {}

    public MemberMaster(String name, String email, String password,
                        String profileImg, MemberType memberType,
                        Date regDate, Date modDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.memberType = memberType;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}