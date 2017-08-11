package com.ms.luvook.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ms.luvook.common.domain.IsUse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "board_heart")
public class BoardHeart {
	
	@Id
	@Column(name = "board_heart_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boardHeartId;
	
	@Column(name = "board_id")
	private int boardId;

	@Column(name = "member_id")
	private int memberId;
	
	@Column(name = "is_use")
	@Enumerated(EnumType.STRING)
	private IsUse isUse;
	
	@Column(name = "reg_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@Column(name = "mod_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modDate;
	
}
