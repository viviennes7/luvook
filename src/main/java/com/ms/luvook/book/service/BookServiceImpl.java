package com.ms.luvook.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.luvook.book.adaptor.BookAdaptor;
import com.ms.luvook.book.domain.Book;
import com.ms.luvook.book.domain.BookSearch;
import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.type.QueryType;

import reactor.core.publisher.Mono;

/**
 * Created by vivie on 2017-05-11.
 */
@Service("bookServiceImpl")
public class BookServiceImpl implements BookService{

    @Autowired
    BookAdaptor bookAdaptor;

    @Override
    public Mono<Book> findOne(String itemId, ItemIdType itemIdType) throws Exception {
        return bookAdaptor.findOne(itemId,itemIdType);
    }

    @Override
    public Mono<BookSearch> find(String query, QueryType queryType, int start, int maxResults) throws Exception {
        return bookAdaptor.find(query,queryType, start, maxResults);
    }
}
