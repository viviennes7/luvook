package com.ms.luvook.board.domain;

import java.util.Date;

import javax.persistence.*;

import com.ms.luvook.common.domain.IsUse;

import com.ms.luvook.member.domain.MemberMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board_comment")
public class BoardComment {
	@Id
	@Column(name = "board_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int boardCommentId;
	
	@Column(name = "member_id")
	int memberId;

	@ManyToOne(optional=false)
	@JoinColumn(name = "member_id", insertable=false, updatable=false)
	private MemberMaster member;

	@Column(name = "board_id")
	int boardId;
	
	@Column(name = "contents")
	String contents;
	
	@Column(name = "is_use")
	@Enumerated(EnumType.STRING)
	IsUse isUse;
	
	@Column(name = "reg_date")
	@Temporal(TemporalType.TIMESTAMP)
	Date regDate;
	
	@Column(name = "mod_date")
	@Temporal(TemporalType.TIMESTAMP)
	Date modDate;
}
