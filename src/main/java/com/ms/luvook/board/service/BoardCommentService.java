package com.ms.luvook.board.service;

import java.util.List;

import com.ms.luvook.board.domain.BoardComment;

public interface BoardCommentService {
	int saveComment(BoardComment boardComment);
	
	BoardComment findComment(int boardCommentId);
	
	List<BoardComment> findAllComment(int boardCommentId);
	
	void deleteComment(int boardCommentId);
	
	int updateComment(BoardComment boardComment);
}
