package com.ms.luvook.book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by vivie on 2017-05-13.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookTest {
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
