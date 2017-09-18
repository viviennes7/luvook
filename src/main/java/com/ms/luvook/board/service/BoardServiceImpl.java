package com.ms.luvook.board.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.domain.BoardHeart;
import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.repository.BoardCommentRepository;
import com.ms.luvook.board.repository.BoardHeartRepository;
import com.ms.luvook.board.repository.BoardRepository;
import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.common.util.EntityUtils;
import com.ms.luvook.common.util.HtmlUtils;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberService;

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
	
	@Override
	public Board save(Board board, int memberId) {
		this.setupForSave(board, memberId);
		Board savedBoard =  boardRepository.save(board);
		this.setAdditionalInfo(savedBoard, memberId);
		MemberMaster writer = memberService.findByMemberId(memberId);
		savedBoard.setMember(writer);
		return savedBoard;
	}

	private void setupForSave(Board board, int memberId){
		board.setMemberId(memberId);
		board.setIsUse(IsUse.Y);
		EntityUtils.initializeRegAndModDate(board);
		String contents = board.getContents();
		board.setContents(HtmlUtils.parseBrTag(contents));
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
	public void delete(int boardId, int memberId) {
		Board board = this.find(boardId, memberId);
		board.setIsUse(IsUse.N);
		board.setModDate(new Date());
		boardRepository.save(board);
	}

	
	@Override
	public Board find(int boardId, int memberId) {
		Board board = boardRepository.findById(boardId).get();
		this.setAdditionalInfo(board, memberId);
		return board;
	}
	
	@Override
	public List<Board> findAll(int memberId, int pageNum) {
		PageRequest page = PageRequest.of(pageNum, 10, new Sort(Direction.DESC, "boardId"));
		List<Board> boards = boardRepository.findAllByIsUseOrderByBoardIdDesc(IsUse.Y,page);
		for(Board board : boards){
			this.setAdditionalInfo(board, memberId);
		}
		
		return boards;
	}
	
	@Override
	public List<Board> findAllByMember(int memberId) {
		List<Board> boards = boardRepository.findAllByMemberIdAndIsUseOrderByBoardIdDesc(memberId, IsUse.Y);
		for(Board board : boards){
			this.setAdditionalInfo(board, memberId);
		}
		
		return boards;
	}
	
	private void setAdditionalInfo(Board board, int memberId){
		this.setHeartCount(board);
		this.setCommentCount(board);
		this.setIsClickedHeart(board, memberId);
		((BookBoard)board).setBigCover();
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

	private void setIsClickedHeart(Board board, int memberId){
		BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardIdAndIsUse(memberId, board.getBoardId(), IsUse.Y);
		if(boardHeart == null){
			board.setIsClickedHeart(false);
		}else{
			board.setIsClickedHeart(true);
		}
	}
	
	@Override
	public void toggleHeart(int boardId, int memberId) {
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
		BoardHeart modHeart = null;
		IsUse inverseIsUse = preHeart.getIsUse().inverse();
		modHeart = preHeart;
		modHeart.setIsUse(inverseIsUse);
		modHeart.setModDate(new Date());
		return modHeart;
	}
	
	@Override
	public BoardComment saveComment(BoardComment boardComment, int memberId) {
		MemberMaster member = memberService.findByMemberId(memberId);
		this.setupCommentForSave(boardComment, memberId);
		BoardComment savedComment = boardCommentRepository.save(boardComment);
		savedComment.setMember(member);
		return savedComment;
	}

	private void setupCommentForSave(BoardComment boardComment, int memberId){
		boardComment.setMemberId(memberId);
		boardComment.setIsUse(IsUse.Y);
		EntityUtils.initializeRegAndModDate(boardComment);
		String contents = boardComment.getContents();
		boardComment.setContents(HtmlUtils.parseBrTag(contents));
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
	
	@Override
	public int findAllReceivedHeartCount(int memberId) {
		int heartCount = boardRepository.findAllReceivedHeartCount(memberId);
		return heartCount;
	}
}
