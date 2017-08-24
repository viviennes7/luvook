package com.ms.luvook.book;

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
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookTests {

	@Autowired
	private BookService bookService;  
	
    @Test
    public void find() throws Exception {
    	bookService.find("동화", QueryType.TITLE, 0, 50).subscribe(result -> {
    		log.info("find() ::: {}", result);
    	});
    	Thread.sleep(5000);
    }
    
    @Test
    public void findOne() throws Exception{
    	bookService.findOne("9788949113623", ItemIdType.ISBN13).subscribe(result -> {
    		log.info("findOne() ::: {}", result);
    	}) ;
    	Thread.sleep(5000);
    }
}
