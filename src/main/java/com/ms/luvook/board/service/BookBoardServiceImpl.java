package com.ms.luvook.board.service;

import org.springframework.stereotype.Service;

import com.ms.luvook.board.domain.Board;

@Service("bookBoardService")
public class BookBoardServiceImpl extends AbstractBoardService{

	@Override
	public Board find(int boardId) {
		return null;
	}

	@Override
	public Board findAll() {
		return null;
	}

	@Override
	public int update(Board board) {
		return 0;
	}

}
