package com.ms.luvook.board.service;

import java.util.List;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;

/**
 * Created by vivie on 2017-07-17.
 */
public interface BoardService{
	int save(Board board);
	
	Board find(int boardId);
	
	List<Board> findAll(int pageNum);
	
	void delete(int boardId);
	
	int update(Board board);
	
	void toggleHeart(int memberId, int boardId);

	List<Board> findAllByMember(int memberId);

	BoardComment saveComment(BoardComment boardComment);

	List<BoardComment> findAllComment(int boardId);

	void deleteComment(int boardCommentId);
	
	int updateComment(BoardComment boardComment);
}
