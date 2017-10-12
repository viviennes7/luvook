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
import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.repository.BoardRepository;
import com.ms.luvook.board.service.BoardService;
import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardTests {
	
	@Autowired
	private BoardService boardService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoardRepository boardRepository;
	
	private BookBoard bookBoard;
	private MemberMaster memberMaster;
	private int memberId;

	@Before
	public void setup(){
		memberMaster = new MemberMaster("%test_nickname",  "%test1@naver.com", "123123", null, null, null, null);
		memberMaster = memberService.signup(memberMaster);
		memberId = memberMaster.getMemberId();
		bookBoard = new BookBoard(memberId,"즐거운책", 5, "책 제목", 12345, "img_url", "K12345678", "1234567891263"); 
	}
	
	@Test
	public void save(){
		//When
		Board savedBoard = boardService.save(bookBoard, memberId);
		
		//Then
		assertNotNull(savedBoard);
	}
	
	@Test
	public void setupForSave(){
		//When
		Board savedBoard = boardService.save(bookBoard, memberId);
		
		//Then
		assertThat(savedBoard.getMemberId(), is(memberId));
		assertThat(savedBoard.getIsUse(), is(IsUse.Y));
		assertNotNull(savedBoard.getContents());
	}
	
	
	@Test
	public void find(){
		//Given
		Board savedBoard = boardService.save(bookBoard, memberId);
		
		//When
		boardService.find(savedBoard.getBoardId(), memberId);
		
		//Then
		String savedTitle = ((BookBoard)savedBoard).getTitle();
		assertThat(savedTitle, is(bookBoard.getTitle()));
	}
	
	@Test
	public void findAllByMember(){
		//Given
		List<Board> boards = boardService.findAllByMember(memberId, memberId);
		
		//When
		boardService.save(bookBoard, memberId);
		boards = boardService.findAllByMember(memberId, memberId);
		
		//Then
		assertThat(boards.size(), is(1));
		
	}
	
	@Test
	public void setHeartCount(){
		
	}
	
	@Test
	public void delete(){
		//Given
		Board savedBoard = boardService.save(bookBoard, memberId);
		int savedBoardId = savedBoard.getBoardId();
		
		//When
		boardService.delete(savedBoardId, memberId);
		
		//Then
		assertThat(savedBoard.getIsUse(), is(IsUse.N));
	}

	
	@Test
	public void update(){
		//Given
		boardService.save(bookBoard, memberId);
		BookBoard willUpdateBoard = bookBoard;
		willUpdateBoard.setTitle("변경된 책제목");
		
		//When
		int updatedBoardId = boardService.update(willUpdateBoard);
		BookBoard updatedBoard = (BookBoard)boardService.find(updatedBoardId, memberId);
		
		//Then
		assertThat(updatedBoard.getTitle(), is(willUpdateBoard.getTitle()));
		
	}

	@Test
	public void findAllHeartCountReceivedMember(){
		//Given
		Board savedBoard = boardService.save(bookBoard, memberId);
		int heartCount = boardRepository.findAllReceivedHeartCount(memberId);
		
		//When
		boardService.toggleHeart(savedBoard.getBoardId(), heartCount);
		 
		//Then
		heartCount = boardRepository.findAllReceivedHeartCount(memberId);
		assertThat(heartCount, is(1));
	}
}
