package com.ms.luvook.board.domain;

import java.sql.Date;

import com.ms.luvook.common.domain.IsUse;

import lombok.Data;

@Data
public class BoardComment {
	int boardCommentId;
	int memberId;
	int boardId;
	String contents;
	IsUse isUse;
	Date regDate;
	Date modDate;
}
