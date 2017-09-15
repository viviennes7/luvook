package com.ms.luvook.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

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

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardCommentTests {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private BoardCommentRepository boardCommentRepository;
	
	private BoardComment boardComment;
	
	@Before
	public void setup(){
		boardComment = new BoardComment(0, 1, null,1, "댓글테스트", null, null, null);
	}
	
	@Test
	public void saveAndFind(){
		//When
		BoardComment savedComment = boardService.saveComment(boardComment);
		
		//Then
		assertNotNull(savedComment.getModDate());
		assertNotNull(savedComment.getRegDate());
		assertThat(savedComment.getIsUse(), is(IsUse.Y));
		assertThat(boardComment.getContents(), is(savedComment.getContents()));
	}
	
	@Test
	public void delete(){
		//Given
		BoardComment savedComment = boardService.saveComment(boardComment);
		int savedCommentId = savedComment.getBoardCommentId();
		
		//When
		boardService.deleteComment(savedCommentId);
		
		//Then
		assertThat(savedComment.getIsUse(), is(IsUse.N));
	}
}