package com.ms.luvook.board.domain;

import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.member.domain.MemberMaster;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;



/**
 * Created by vivie on 2017-07-17.
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(name = "board")
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    @Column(name = "member_id")
    private int memberId;

    @Lob
    @Column(name = "contents")
    private String contents;

    @Column(name = "grade")
    private int grade;

    @Column(name = "is_use")
    @Enumerated(EnumType.STRING)
    private IsUse isUse;

    @Column(name = "reg_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Column(name = "mod_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;

    @ManyToOne(optional=false)
    @JoinColumn(name = "member_id", insertable=false, updatable=false)
    private MemberMaster member;

    //private Book book;

}
