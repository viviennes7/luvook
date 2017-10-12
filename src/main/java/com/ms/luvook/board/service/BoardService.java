package com.ms.luvook.board.service;

import java.util.List;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;

/**
 * Created by vivie on 2017-07-17.
 */
public interface BoardService{
	Board save(Board board, int memberId);
	
	Board find(int boardId, int memberId);
	
	List<Board> findAll(int memberId, int pageNum);
	
	void delete(int boardId, int memberId);
	
	int update(Board board);
	
	void toggleHeart(int boardId, int memberId);

	List<Board> findAllByMember(int memberId, int connectingMemberId);

	BoardComment saveComment(BoardComment boardComment, int memberId);

	List<BoardComment> findAllComment(int boardId);

	void deleteComment(int boardCommentId);
	
	int updateComment(BoardComment boardComment);

	int findAllReceivedHeartCount(int memberId);
}
