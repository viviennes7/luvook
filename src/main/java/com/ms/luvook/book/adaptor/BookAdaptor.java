package com.ms.luvook.book.adaptor;


import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.type.QueryType;
import com.ms.luvook.book.domain.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by vivie on 2017-04-19.
 */
public interface BookAdaptor {

    Mono<String> findOne(String itemId, ItemIdType itemIdType) throws Exception;
    Flux<Book> find(String query, QueryType queryType) throws Exception;
}
