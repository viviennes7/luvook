package com.ms.luvook.board.service;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.repository.BoardRepository;
import com.ms.luvook.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractBoardService implements BoardService{

	@Autowired
	private BoardRepository boardRepository;

	@Override
	public int save(Board board) {
		DateUtil.initializeRegAndModDate(board);
		DateUtil.initializeRegAndModDate(new BookBoardServiceImpl());
		Board savedBoard =  boardRepository.save(board);

		log.info("savedBoard ::: ", savedBoard);

		return savedBoard.getBoardId();
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

	private void initializeRegAndModDate(){

	}
}
