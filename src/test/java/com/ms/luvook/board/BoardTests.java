package com.ms.luvook.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.transaction.Transactional;

import com.ms.luvook.member.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BoardHeart;
import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.repository.BoardHeartRepository;
import com.ms.luvook.board.repository.BoardRepository;
import com.ms.luvook.board.service.BoardService;
import com.ms.luvook.common.domain.IsUse;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
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
	private MemberRepository memberRepository;

	@Autowired
	private BoardRepository boardRepository;
	
	private BookBoard bookBoard;
	private MemberMaster memberMaster;
	private int memberId;

	@Before
	public void setup(){
		bookBoard = new BookBoard(1,"즐거운책", 5, "책 제목", 12345, "img_url", "11111", "2222222"); 
		memberMaster = new MemberMaster("%test_nickname",  "%test1@naver.com", "123123", "img", MemberType.USER, new Date(), new Date());
		memberMaster = memberRepository.save(memberMaster);
		memberId = memberMaster.getMemberId();
	}
	
	@Test
	public void saveAndFindTest(){
		//When
		Board savedBoard = boardService.save(bookBoard, memberId);
		
		//Then
		assertNotNull(savedBoard.getRegDate());
		assertNotNull(savedBoard.getModDate());
		assertThat(savedBoard.getIsUse(), is(IsUse.Y));
		assertThat(savedBoard.getContents(), is(bookBoard.getContents()));
	}
	
	@Test
	public void deleteTest(){
		//Given
		Board savedBoard = boardService.save(bookBoard, memberId);
		int savedBoardId = savedBoard.getBoardId();
		
		//When
		boardService.delete(savedBoardId, memberId);
		
		//Then
		assertThat(savedBoard.getIsUse(), is(IsUse.N));
	}

	
	//나중에 한번 손볼것
	@Test
	public void updateTest(){
		//Given
		bookBoard.setMemberId(0);
		bookBoard.setRegDate(null);
		log.info("   bookBoard ::: {}", bookBoard);
		
		BookBoard willUpdateBoard = bookBoard;
		willUpdateBoard.setTitle("변경된 책제목");
		boardService.save(bookBoard, memberId);
		
		//When
		int updatedBoardId = boardService.update(willUpdateBoard);
		BookBoard updatedBoard = (BookBoard)boardService.find(updatedBoardId, memberId);
		log.info("   bookBoard ::: {}", bookBoard);
		log.info("updatedBoard ::: {}", updatedBoard);
		
		//Then
		assertThat(updatedBoard.getTitle(), is(willUpdateBoard.getTitle()));
		
	}

	@Test
	public void findAllHeartCountReceivedMember(){
		//given
		int memberId = 4;
		
		//when
		int heartCount = boardRepository.findAllReceivedHeartCount(memberId);
		
		//then
		log.info("heartCount ::: {}", heartCount);
		
	}
}
