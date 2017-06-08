package com.ms.luvook.book.controller;

import com.ms.luvook.book.domain.Book;
import com.ms.luvook.book.domain.BookSearch;
import com.ms.luvook.book.service.BookService;
import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.type.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by vivie on 2017-05-11.
 */

@Slf4j
@RestController
public class BookController{

    @Autowired
    private BookService bookService;

    @GetMapping("/books/{itemId}")
    public Mono<Book> findOne(@PathVariable String itemId, String itemIdType) throws Exception{
        return bookService.findOne(itemId, ItemIdType.valueOf(itemIdType));

    }

    @GetMapping(value = "/books/{queryType}/{query}")
    public Mono<BookSearch> find(@PathVariable String queryType,
    							 @PathVariable String query, 
    							 int start, int maxResults) throws Exception {
    	
        return bookService.find(query, QueryType.valueOf(queryType), start, maxResults);
    }
}
