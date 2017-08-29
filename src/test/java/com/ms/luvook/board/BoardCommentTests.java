package com.ms.luvook.board;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.board.domain.BoardComment;
import com.ms.luvook.board.repository.BoardCommentRepository;
import com.ms.luvook.board.service.BoardService;

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
	
//	@Test
//	public void saveAndFind(){
//		//When
//		int savedCommentId = boardService.saveComment(boardComment);
//		BoardComment savedComment =  boardCommentRepository.findById(savedCommentId).get();
//		
//		//Then
//		assertNotNull(savedComment.getModDate());
//		assertNotNull(savedComment.getRegDate());
//		assertThat(savedComment.getIsUse(), is(IsUse.Y));
//		assertThat(boardComment.getContents(), is(savedComment.getContents()));
//	}
//	
//	@Test
//	public void delete(){
//		//Given
//		int savedCommentId = boardService.saveComment(boardComment);
//		
//		//When
//		boardService.deleteComment(savedCommentId);
//		BoardComment savedComment =  boardCommentRepository.findById(savedCommentId).get();
//		
//		//Then
//		assertThat(savedComment.getIsUse(), is(IsUse.N));
//	}
}