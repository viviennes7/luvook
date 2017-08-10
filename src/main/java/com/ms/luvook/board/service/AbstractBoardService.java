package com.ms.luvook.board.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.repository.BoardRepository;
import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.common.util.DateUtil;
import com.ms.luvook.member.domain.MemberMaster;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public abstract class AbstractBoardService implements BoardService{

	@Autowired
	private BoardRepository boardRepository;

	@Override
	public Board find(int boardId) {
		Board board = boardRepository.findById(boardId).get();
		return board;
	}
	
	@Override
	public List<Board> findAll(int pageNum) {
		PageRequest page = PageRequest.of(pageNum, 10, new Sort(Direction.DESC, "boardId"));
		Page<Board> result = boardRepository.findAll(page);
		List<Board> boards = result.getContent();

		return boards;
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
		final Board currentBoard = this.find(board.getBoardId());
        final MemberMaster boardWriter = currentBoard.getMember();
        final Date regDate = currentBoard.getRegDateObj();

        board.setModDate(new Date());
        board.setRegDate(regDate);
		board.setMember(boardWriter);
		Board updatedBoard = boardRepository.save(board);
		return updatedBoard.getBoardId();
	}
	
	@Override
	public void delete(int boardId) {
		Board board = this.find(boardId);
		board.setIsUse(IsUse.N);
		board.setModDate(new Date());
		boardRepository.save(board);
	}
	
	@Override
	public List<Board> findAllByMember(int memberId) {
		PageRequest pageRequest = PageRequest.of(0, 10);
		return boardRepository.findAllByMemberIdOrderByBoardIdDesc(memberId, pageRequest);
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
	public List<BoardComment> findAllComment(int boardCommentId) {
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
