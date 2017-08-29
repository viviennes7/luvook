package com.ms.luvook.board.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ms.luvook.common.service.JwtService;
import com.ms.luvook.common.util.EntityUtils;

@Transactional
@Component("boardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardHeartRepository boardHeartRepository;
	
	@Autowired
	private BoardCommentRepository boardCommentRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public int save(Board board) {
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int) memberMap.get("memberId");
		board.setMemberId(memberId);
		board.setIsUse(IsUse.Y);
		EntityUtils.initializeRegAndModDate(board);
		Board savedBoard =  boardRepository.save(board);
		return savedBoard.getBoardId();
	}
	
	//나중에 한번 손볼것
	@Override
	public int update(Board board) {
		Board currentBoard = boardRepository.getOne(board.getBoardId());
		int writerId = currentBoard.getMemberId();
		Date regDate = currentBoard.getRegDateObj();
		
		board.setModDate(new Date());
		board.setRegDate(regDate);
		board.setMemberId(writerId);
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
		this.setIsClickedHeart(board);
		return board;
	}
	
	@Override
	public List<Board> findAll(int pageNum) {
		PageRequest page = PageRequest.of(pageNum, 10, new Sort(Direction.DESC, "boardId"));
		List<Board> boards = boardRepository.findAllByIsUseOrderByBoardIdDesc(IsUse.Y,page);
		
		for(Board board : boards){
			this.setHeartCount(board);
			this.setCommentCount(board);
			this.setIsClickedHeart(board);
		}
		
		return boards;
	}
	
	@Override
	public List<Board> findAllByMember() {
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int) memberMap.get("memberId");
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
		int commentCount = boardCommentRepository.findByBoardIdAndIsUseOrderByBoardCommentId(boardId, IsUse.Y).size();
		board.setCommentCount(commentCount);
	}

	private void setIsClickedHeart(Board board){
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int) memberMap.get("memberId");
		BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardIdAndIsUse(memberId, board.getBoardId(), IsUse.Y);
		if(boardHeart == null){
			board.setIsClickedHeart(false);
		}else{
			board.setIsClickedHeart(true);
		}
	}
	
	@Override
	public void toggleHeart(int boardId) {
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int) memberMap.get("memberId");
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
	public BoardComment saveComment(BoardComment boardComment) {
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int) memberMap.get("memberId");
		MemberMaster member = memberService.findByMemberId(memberId);
		boardComment.setMemberId(memberId);
		boardComment.setIsUse(IsUse.Y);
		EntityUtils.initializeRegAndModDate(boardComment);
		BoardComment savedComment = boardCommentRepository.save(boardComment);
		savedComment.setMember(member);
		return savedComment;
	}

	@Override
	public List<BoardComment> findAllComment(int boardId) {
		return boardCommentRepository.findByBoardIdAndIsUseOrderByBoardCommentId(boardId, IsUse.Y);
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
		int boardCommentId = boardComment.getBoardCommentId();
		BoardComment currentComment = boardCommentRepository.findById(boardCommentId).get();
		currentComment.setContents(boardComment.getContents());
		currentComment.setModDate(new Date());
		boardCommentRepository.save(currentComment);
		return boardCommentId;
	}
}
