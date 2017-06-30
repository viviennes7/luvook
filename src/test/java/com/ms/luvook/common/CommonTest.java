package com.ms.luvook.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by vivie on 2017-05-13.
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommonTest {
    private WebClient webClient = WebClient.create("http://localhost:8080");

    @Test
    public void test() throws Exception {
        String test = "123";

        log.info(test.hashCode()+"");

        test += "1234";

        log.info(test.hashCode()+"");

    }
}
