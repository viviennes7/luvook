package com.ms.luvook.book.controller;

import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.domain.Book;
import com.ms.luvook.book.service.BookService;
import com.ms.luvook.book.type.QueryType;
import com.ms.luvook.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

/**
 * Created by vivie on 2017-05-11.
 */

@RestController
public class BookController{

    @Autowired
    private BookService bookService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE , value="/test/{query}")
    public Flux<Integer> test(@PathVariable String query){
        new Member("1",2,"a");
//        Flux<Book> bookFlux = Flux.fromStream(Stream.generate(() -> new Member("1",2,"a")));

        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));


        return Flux
                .range(1,100)
                .map(s -> s * 10);
    }

    @GetMapping("/books/{itemId}")
    public Mono<String> findOne(@PathVariable String itemId, String itemIdType) throws Exception{
        return bookService.findOne(itemId, ItemIdType.valueOf(itemIdType));

    }

    @GetMapping("/books/{queryType}/{query}")
    public Flux<Book> find(@PathVariable String query, @PathVariable String queryType) throws Exception {
        return bookService.find(query, QueryType.valueOf(queryType));
    }
}
