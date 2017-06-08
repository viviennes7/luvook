package com.ms.luvook.book.adaptor;

import com.google.gson.Gson;
import com.ms.luvook.book.domain.Book;
import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.type.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by vivie on 2017-04-19.
 */
@Slf4j
@Component("bookAdaptorImpl")
public class BookAdaptorImpl implements BookAdaptor {

    @Value("${authkey.aladin}")
    String ttbKey;

    static final private String BASE_URL = "http://www.aladin.co.kr/ttb/api";
    private WebClient webClient = WebClient.create(BASE_URL);
    final Gson gson = new Gson();

    @Override
    public Mono<String> findOne(String itemId, ItemIdType itemIdType) throws Exception {
        log.info("itemIdType ::: {}", itemIdType.name());
        String uri = "/ItemLookUp.aspx?itemIdType="+itemIdType.name()+"&ItemId="+itemId+"&output=js&Version=20131101&ttbkey=" + ttbKey;
        log.info("URL ::: {}", BASE_URL + uri);

        return webClient
                .get()
                .uri("uri")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .log()
                .flatMap(response -> {
                    return response.bodyToMono(String.class);
                });
    }


    @Override
    public Flux<Book> find(String query, QueryType queryType) throws Exception {
        String uri = "ItemSearch.aspx?&Version=20131101&output=js&ttbkey=" + ttbKey;
//        Query=aladdin&QueryType=Title&MaxResults=10&start=1
        log.info("Start");
        final String call = BASE_URL + "?query="+query;

        log.info("URL ::: {}", call);

        return null;
    }
}
