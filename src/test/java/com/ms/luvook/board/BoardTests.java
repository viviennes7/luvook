package com.ms.luvook.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.transaction.Transactional;

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
	private MemberService memberService;
	@Autowired
	private BoardHeartRepository boardHeartRepository;
	
	private BookBoard bookBoard;
	private MemberMaster memberMaster;
	
	@Before
	public void setup(){
		bookBoard = new BookBoard(1,"즐거운책", 5, "책 제목", 12345, "img_url", 11111, 2222222); 
		memberMaster = new MemberMaster("김민수",  "test1@naver.com", "123123", "img", MemberType.USER, new Date(), new Date());
	}
	
	@Test
	public void saveAndFindTest(){
		//When
		int savedBookId = boardService.save(bookBoard);
		Board savedBoard = boardService.find(savedBookId);
		
		//Then
		assertNotNull(savedBoard.getRegDate());
		assertNotNull(savedBoard.getModDate());
		assertThat(savedBoard.getIsUse(), is(IsUse.Y));
		assertThat(savedBoard.getContents(), is(bookBoard.getContents()));
	}
	
	@Test
	public void deleteBookTest(){
		//Given
		int savedBookId = boardService.save(bookBoard);
		
		//When
		boardService.delete(savedBookId);
		Board savedBoard = boardService.find(savedBookId);
		
		//Then
		assertThat(savedBoard.getIsUse(), is(IsUse.N));
	}
	
	@Test
	public void toggleHeart(){
		//Given
		int savedBookId = boardService.save(bookBoard);
		MemberMaster savedMember = memberService.signup(memberMaster);
		int savedMemberId = savedMember.getMemberId();
		
		//Case1 - Heart is not exist 
		boardService.toggleHeart(savedMemberId, savedBookId);
		BoardHeart boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBookId);
		assertNotNull(boardHeart);
		
		//Case2 - Heart is exist and IsUse is 'Y'
		boardService.toggleHeart(savedMemberId, savedBookId);
		boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBookId);
		assertThat(boardHeart.getIsUse(), is(IsUse.N));
		
		//Case2 - Heart is exist and IsUse is 'N'
		boardService.toggleHeart(savedMemberId, savedBookId);
		boardHeart = boardHeartRepository.findByMemberIdAndBoardId(savedMemberId, savedBookId);
		assertThat(boardHeart.getIsUse(), is(IsUse.Y));
		
	}
	
	/*@Test
	public void setHeartCountTest(){
		//When
		int savedBookId = boardService.save(bookBoard);
		Board savedBoard = boardService.find(savedBookId);
		
		for (int i = 0; i < 5; i++) {
		}
	}*/
	
	
}
