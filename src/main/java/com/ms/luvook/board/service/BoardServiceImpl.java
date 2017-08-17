package com.ms.luvook.board.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.domain.BoardHeart;
import com.ms.luvook.board.repository.BoardCommentRepository;
import com.ms.luvook.board.repository.BoardHeartRepository;
import com.ms.luvook.board.repository.BoardRepository;
import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.common.util.EntityUtils;
import com.ms.luvook.member.domain.MemberMaster;

@Transactional
@Component("boardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardHeartRepository boardHeartRepository;
	
	@Autowired
	private BoardCommentRepository boardCommentRepository;
	
	@Override
	public int save(Board board) {
		EntityUtils.initializeRegAndModDate(board);
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
	public Board find(int boardId) {
		Board board = boardRepository.findById(boardId).get();
		this.setHeartCount(board);
		this.setCommentCount(board);
		return board;
	}
	
	@Override
	public List<Board> findAll(int pageNum) {
		PageRequest page = PageRequest.of(pageNum, 10, new Sort(Direction.DESC, "boardId"));
		Page<Board> result = boardRepository.findAll(page);
		List<Board> boards = result.getContent();
		
		for(Board board : boards){
			this.setHeartCount(board);
			this.setCommentCount(board);
		}
		
		return boards;
	}
	
	@Override
	public List<Board> findAllByMember(int memberId) {
		PageRequest pageRequest = PageRequest.of(0, 10);
		List<Board> boards = boardRepository.findAllByMemberIdOrderByBoardIdDesc(memberId, pageRequest);
		
		for(Board board : boards){
			this.setHeartCount(board);
			this.setCommentCount(board);
		}
		
		return boards;
	}
	
	private void setHeartCount(Board board){
		int boardId = board.getBoardId();
		int heartCount = boardHeartRepository.findByBoardIdAndIsUse(boardId, IsUse.Y).size();
		board.setHeartCount(heartCount);
	}
	
	private void setCommentCount(Board board){
		int boardId = board.getBoardId();
		int commentCount = boardCommentRepository.findByBoardIdAndIsUse(boardId, IsUse.Y).size();
		board.setCommentCount(commentCount);
	}
	
	@Override
	public void toggleHeart(int memberId, int boardId) {
		BoardHeart preHeart = boardHeartRepository.findByMemberIdAndBoardId(memberId, boardId);
		BoardHeart newOrModHeart = null;
		if(preHeart == null){
			newOrModHeart = this.newHeart(memberId, boardId);
		}else{
			newOrModHeart = this.modifyHeart(preHeart); 
		}
		boardHeartRepository.save(newOrModHeart);
	}
	
	private BoardHeart newHeart(int memberId, int boardId){
		BoardHeart newHeart = new BoardHeart();
		newHeart.setMemberId(memberId);
		newHeart.setBoardId(boardId);
		newHeart.setIsUse(IsUse.Y);
		EntityUtils.initializeRegAndModDate(newHeart);
		return newHeart;
	}
	
	private BoardHeart modifyHeart(BoardHeart preHeart){
		BoardHeart modHeart = new BoardHeart();
		IsUse inverseIsUse = preHeart.getIsUse().inverse();
		modHeart = preHeart;
		modHeart.setIsUse(inverseIsUse);
		modHeart.setModDate(new Date());
		return modHeart;
	}
	
	@Override
	public int saveComment(BoardComment boardComment) {
		EntityUtils.initializeRegAndModDate(boardComment);
		boardComment.setIsUse(IsUse.Y);
		BoardComment savedComment = boardCommentRepository.save(boardComment);
		return savedComment.getBoardCommentId();
	}
	
	@Override
	public void deleteComment(int boardCommentId) {
		BoardComment boardComment = boardCommentRepository.findById(boardCommentId).get();
		boardComment.setIsUse(IsUse.N);
		boardComment.setModDate(new Date());
		boardCommentRepository.save(boardComment);
	}
	
	@Override
	public int updateComment(BoardComment boardComment) {
		return 0;
	}
}
