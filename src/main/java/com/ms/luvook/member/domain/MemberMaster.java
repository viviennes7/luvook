package com.ms.luvook.member.domain;

import com.ms.luvook.board.domain.BoardBook;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private long memberId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Basic(fetch = FetchType.LAZY)
    private String password;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "background_img")
    private String backgroundImg;

    @Column(name = "member_type")
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(name = "birthdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdate;

    @Column(name = "reg_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Column(name = "mod_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;

    @OneToMany(mappedBy = "member")
    private List<BoardBook> boardBooks;

    public MemberMaster() {}

    public MemberMaster(String name, String email, String password, String introduction,
                        String profileImg, String backgroundImg, MemberType memberType,
                        Date birthdate, Date regDate, Date modDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
        this.profileImg = profileImg;
        this.backgroundImg = backgroundImg;
        this.memberType = memberType;
        this.birthdate = birthdate;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
