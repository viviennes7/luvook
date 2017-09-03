package com.ms.luvook.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.common.util.DateCalculator;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int boardCommentId;
	
	@Column(name = "member_id")
	int memberId;

	@ManyToOne(optional=false)
	@JoinColumn(name = "member_id", insertable=false, updatable=false)
	private MemberMaster member;

	int boardId;
	
	String contents;
	
	@Enumerated(EnumType.STRING)
	IsUse isUse;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date regDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date modDate;
	
	public String getRegDate(){
		if(regDate == null){
			return null;
		}else {
			return DateCalculator.calculateFromCurrent(regDate);
		}
	}
	
	public String getModDate(){
		if(regDate == null){
			return null;
		}else {
			return DateCalculator.calculateFromCurrent(modDate);
		}
	}
}
