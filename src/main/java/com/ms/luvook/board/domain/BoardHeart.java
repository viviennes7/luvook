package com.ms.luvook.board.domain;

import java.util.Date;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_heart")
public class BoardHeart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boardHeartId;
	
	private int boardId;

	private int memberId;
	
	@Enumerated(EnumType.STRING)
	private IsUse isUse;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modDate;
	
}
