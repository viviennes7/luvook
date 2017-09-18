package com.ms.luvook.book;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.luvook.book.service.BookService;
import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.type.QueryType;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by vivie on 2017-05-13.
 * 
 * 임시로 작성한 테스트코드입니다.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookTests {

	@Autowired
	private BookService bookService;  
	
	@After
	public void daemonThread(){
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
    @Test
    public void find(){
    	bookService.find("동화", QueryType.TITLE, 0, 50).subscribe(result -> {
    		log.info("find() ::: {}", result);
    	});
    }
    
    @Test
    public void findOne(){
    	bookService.findOne("9788949113623", ItemIdType.ISBN13).subscribe(result -> {
    		log.info("findOne() ::: {}", result);
    	}) ;
    }
}
