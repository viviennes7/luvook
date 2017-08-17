package com.ms.luvook.board;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.repository.BoardHeartRepository;
import com.ms.luvook.board.service.BoardService;
import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
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
	public void saveAndFind(){
		
	}
	
	@Test
	public void delete(){
		
	}
	
	
}