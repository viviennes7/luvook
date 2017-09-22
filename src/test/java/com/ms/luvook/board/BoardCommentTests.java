package com.ms.luvook.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.service.BoardService;
import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardCommentTests {
	
	@Autowired
	private BoardService boardService;

	@Autowired
	private MemberService memberService;

	private MemberMaster memberMaster;
	private int memberId;
	private int boardId;
	private BoardComment boardComment;

	@Before
	public void setup(){
		memberMaster = new MemberMaster("%test_nickname",  "%test1@naver.com", "123123", null, null, null, null);
		memberMaster = memberService.signup(memberMaster);
		memberId = memberMaster.getMemberId();
		
		Board bookBoard = new BookBoard(memberId,"즐거운책", 5, "책 제목", 12345, "img_url", "K12345678", "1234567891263");
		Board savedBoard = boardService.save(bookBoard, memberId);
		boardId = savedBoard.getBoardId();
		
		boardComment = new BoardComment(0, memberId, null, boardId, "댓글테스트", null, null, null);
	}
	
	@Test
	public void save(){
		//When
		BoardComment savedComment = boardService.saveComment(boardComment, memberId);
		
		//Then
		assertNotNull(savedComment);
	}
	
	@Test
	public void setupForSave(){
		//When
		BoardComment savedComment = boardService.saveComment(boardComment, memberId);
		
		//Then
		assertThat(savedComment.getMemberId(), is(memberId));
		assertThat(savedComment.getIsUse(), is(IsUse.Y));
		assertNotNull(savedComment.getContents());
	}
	
	@Test
	public void findAll(){
		//Given
		BoardComment savedComment = boardService.saveComment(boardComment, memberId);
		
		//When
		List<BoardComment> comments = boardService.findAllComment(boardId);
		BoardComment commentInList = comments.get(0);
		
		//Then
		assertThat(savedComment.getContents(), is(commentInList.getContents()));
	}
	
	@Test
	public void delete(){
		//Given
		BoardComment savedComment = boardService.saveComment(boardComment, memberId);
		int savedCommentId = savedComment.getBoardCommentId();
		
		//When
		boardService.deleteComment(savedCommentId);
		
		//Then
		assertThat(savedComment.getIsUse(), is(IsUse.N));
	}
	
	@Test
	public void update(){
		//Given
		BoardComment savedComment = boardService.saveComment(boardComment, memberId);
		String willUpdateContents = "댓글 변경 테스트";
		
		//When
		savedComment.setContents(willUpdateContents);
		boardService.updateComment(savedComment);
		
		//Then
		List<BoardComment> comments = boardService.findAllComment(boardId);
		BoardComment updatedComment = comments.get(0);
		assertThat(updatedComment.getContents(), is(willUpdateContents));
	}
}