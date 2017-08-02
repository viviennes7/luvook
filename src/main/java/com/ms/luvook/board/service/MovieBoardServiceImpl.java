package com.ms.luvook.board.service;

import com.ms.luvook.board.domain.Board;
import org.springframework.stereotype.Service;

@Service("movieBoardService")
public class MovieBoardServiceImpl extends AbstractBoardService{

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
