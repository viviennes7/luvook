package com.ms.luvook.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.board.domain.Board;
import com.ms.luvook.board.domain.BookBoard;
import com.ms.luvook.board.service.BookBoardServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookBoardTests {
	
	@Autowired
	private BookBoardServiceImpl bookBoardServiceImpl;
	
	@Test
	public void saveBook(){
		BookBoard bookBoard = new BookBoard(1,"즐거운책", 5, "책 제목", 12345, "img_url", 11111, 2222222);
		
		int savedBookId = bookBoardServiceImpl.save(bookBoard);
		Board savedBoard = bookBoardServiceImpl.find(savedBookId);
		
		assertNotNull(savedBoard);
		assertThat(savedBoard.getContents(), is(bookBoard.getContents()));
	}
	
	@Test
	public void findAll(){
		final int pageNum = 1;
		
		List<Board> boards = bookBoardServiceImpl.findAll(pageNum);
		
		log.info("boards ::: " + boards);
		
		
	}
}
