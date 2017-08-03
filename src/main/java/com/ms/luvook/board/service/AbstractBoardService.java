package com.ms.luvook.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.repository.BoardRepository;
import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.common.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public abstract class AbstractBoardService implements BoardService{

	@Autowired
	private BoardRepository boardRepository;

	@Override
	public Board find(int boardId) {
		return boardRepository.findById(boardId).get();
	}
	
	@Override
	public Board findAll() {
		return null;
	}
	
	@Override
	public int save(Board board) {
		DateUtil.initializeRegAndModDate(board);
		board.setIsUse(IsUse.Y);
		Board savedBoard =  boardRepository.save(board);
		return savedBoard.getBoardId();
	}
	
	@Override
	public int update(Board board) {
		return 0;
	}
	
	@Override
	public void delete(int boardId) {
		boardRepository.deleteById(boardId);
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
