package com.ms.luvook.book.service;

import com.ms.luvook.book.domain.Book;
import com.ms.luvook.book.domain.BookSearch;
import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.type.QueryType;

import reactor.core.publisher.Mono;

/**
 * Created by vivie on 2017-05-11.
 */
public interface BookService {
    Mono<Book> findOne(String itemId, ItemIdType itemIdType) throws Exception;
    Mono<BookSearch> find(String query, QueryType queryType, int start, int maxResults) throws Exception;
}
