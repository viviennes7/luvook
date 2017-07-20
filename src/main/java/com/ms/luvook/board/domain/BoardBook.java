//package com.ms.luvook.board.domain;
//
//import com.ms.luvook.book.domain.Book;
//import com.ms.luvook.common.domain.IsUse;
//import com.ms.luvook.member.domain.MemberMaster;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Date;
//
///**
// * Created by vivie on 2017-07-17.
// */
//@Data
//@Entity
//@Table(name = "board_book")
//public class BoardBook {
//
//    @Id
//    @Column(name = "board_book_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int boardBookId;
//
//    @Column(name = "member_id")
//    private int memberId;
//
//    @Column(name = "subject")
//    private String subject;
//
//    @Column(name = "contents")
//    private String contents;
//
//    @Column(name = "grade")
//    private byte grade;
//
//    @Column(name = "isbn")
//    private long isbn;
//
//    @Column(name = "isbn13")
//    private long isbn13;
//
//    @Column(name = "is_use")
//    @Enumerated(EnumType.STRING)
//    private IsUse isUse;
//
//    @Column(name = "reg_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date regDate;
//
//    @Column(name = "mod_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date modDate;
//
//    @ManyToOne(optional=false)
//    @JoinColumn(name = "member_id")
//    private MemberMaster member;
//
//    private Book book;
//
//}
