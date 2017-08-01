package com.ms.luvook.board.service;

import com.ms.luvook.board.domain.BoardComment;

public interface BoardCommentService {
	int saveComment(BoardComment boardComment);
	
	BoardComment findComment(int boardCommentId);
	
	void deleteComment(int boardCommentId);
	
	int updateComment(BoardComment boardComment);
}
