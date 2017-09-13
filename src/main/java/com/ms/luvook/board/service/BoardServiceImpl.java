package com.ms.luvook.board.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.ms.luvook.common.service.JwtService;
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
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public Board save(Board board) {
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int) memberMap.get("memberId");
		board.setMemberId(memberId);
		board.setIsUse(IsUse.Y);
		EntityUtils.initializeRegAndModDate(board);
		String contents = board.getContents();
		board.setContents(HtmlUtils.parseBrTag(contents));
		Board savedBoard =  boardRepository.save(board);
		this.setAdditionalInfo(savedBoard);
		
		MemberMaster writter = memberService.findByMemberId(memberId);
		savedBoard.setMember(writter);
		return savedBoard;
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
		this.setAdditionalInfo(board);
		return board;
	}
	
	@Override
	public List<Board> findAll(int pageNum) {
		PageRequest page = PageRequest.of(pageNum, 10, new Sort(Direction.DESC, "boardId"));
		List<Board> boards = boardRepository.findAllByIsUseOrderByBoardIdDesc(IsUse.Y,page);
		for(Board board : boards){
			this.setAdditionalInfo(board);
		}
		
		return boards;
	}
	
	@Override
	public List<Board> findAllByMember(int memberId) {
		List<Board> boards = boardRepository.findAllByMemberIdAndIsUseOrderByBoardIdDesc(memberId, IsUse.Y);
		for(Board board : boards){
			this.setAdditionalInfo(board);
		}
		
		return boards;
	}
	
	//TODO : 나중에 QueryDSL로 짜볼것
	private void setAdditionalInfo(Board board){
		this.setHeartCount(board);
		this.setCommentCount(board);
		this.setIsClickedHeart(board);
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
        String contents = boardComment.getContents();
        boardComment.setContents(HtmlUtils.parseBrTag(contents));
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
	
	@Override
	public int findAllReceivedHeartCount() {
		Map<String, Object> memberMap = jwtService.get("member");
		int memberId = (int) memberMap.get("memberId");
		int heartCount = boardRepository.findAllReceivedHeartCount(memberId);
		return heartCount;
	}
}
