package com.ms.luvook.book;

import com.google.gson.Gson;
import com.ms.luvook.book.type.ItemIdType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Created by vivie on 2017-05-13.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class Book {
    private WebClient webClient = WebClient.create("http://localhost:8080");


    @Test
    public void test() throws Exception {
        final String query = "Query";
        System.out.println("시작!!!!!");

        webClient
                .get()
                .uri("/test/"+query)
                .exchange()
                .log()
                .flatMap(response -> response.bodyToMono(String.class))
                .subscribe(s -> {
                    System.out.println("subscribe");
                    System.out.println(s);
                });

        Thread.sleep(10000);
    }
}
