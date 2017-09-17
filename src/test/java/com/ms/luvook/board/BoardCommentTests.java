package com.ms.luvook.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

import com.ms.luvook.member.domain.MemberMaster;
import com.ms.luvook.member.domain.MemberType;
import com.ms.luvook.member.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.repository.BoardCommentRepository;
import com.ms.luvook.board.service.BoardService;
import com.ms.luvook.common.domain.IsUse;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardCommentTests {
	
	@Autowired
	private BoardService boardService;

	@Autowired
	private MemberRepository memberRepository;

	private MemberMaster memberMaster;
	private int memberId;
	private BoardComment boardComment;


	@Before
	public void setup(){
		memberMaster = new MemberMaster("%test_nickname",  "%test1@naver.com", "123123", "img", MemberType.USER, new Date(), new Date());
		memberMaster = memberRepository.save(memberMaster);
		memberId = memberMaster.getMemberId();
		boardComment = new BoardComment(0, 1, null,1, "댓글테스트", null, null, null);
	}
	
	@Test
	public void saveAndFind(){
		//When
		BoardComment savedComment = boardService.saveComment(boardComment, memberId);
		
		//Then
		assertNotNull(savedComment.getModDate());
		assertNotNull(savedComment.getRegDate());
		assertThat(savedComment.getIsUse(), is(IsUse.Y));
		assertThat(boardComment.getContents(), is(savedComment.getContents()));
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
}