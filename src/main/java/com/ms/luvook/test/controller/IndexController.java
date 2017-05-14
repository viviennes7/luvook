package com.ms.luvook.test.controller;

import com.ms.luvook.book.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by vivie on 2017-04-09.
 */

@Slf4j
@RestController
public class IndexController {

    @GetMapping("/")
    public Mono<Book> index() {

        return null;
    }
}