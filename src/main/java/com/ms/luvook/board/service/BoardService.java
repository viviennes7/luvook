package com.ms.luvook.board.service;

import com.ms.luvook.board.domain.Board;

/**
 * Created by vivie on 2017-07-17.
 */
public interface BoardService extends BoardCommentService{
	int save(Board board);
	
	Board find(int boardId);
	
	Board findAll();
	
	void delete(int boardId);
	
	int update(Board board);
	
	void toggleHeart(int memberId, int boardId);
	
}
