package com.ms.luvook.board.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.ms.luvook.board.domain.Board;

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardRepositoryQueryDsl{

	public BoardRepositoryImpl() {
		super(Board.class);
	}

}
