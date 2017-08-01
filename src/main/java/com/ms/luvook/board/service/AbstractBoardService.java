package com.ms.luvook.board.service;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;

public abstract class AbstractBoardService implements BoardService{
	
	@Override
	public int save(Board board) {
		return 0;
	}
	
	@Override
	public void delete(int boardId) {
		
	}
	
	@Override
	public void toggleHeart(int memberId, int boardId) {
		
	}
	
	@Override
	public int saveComment(BoardComment boardComment) {
		return 0;
	}
	
	@Override
	public BoardComment findComment(int boardCommentId) {
		return null;
	}
	
	@Override
	public void deleteComment(int boardCommentId) {
		
	}
	@Override
	public int updateComment(BoardComment boardComment) {
		return 0;
	}
}
