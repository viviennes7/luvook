package com.ms.luvook.book.adaptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.ms.luvook.book.domain.Book;
import com.ms.luvook.book.domain.BookSearch;
import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.type.QueryType;

import lombok.extern.slf4j.Slf4j;
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
    public Mono<Book> findOne(String itemId, ItemIdType itemIdType) throws Exception {
        log.info("itemIdType ::: {}", itemIdType.name());
        final String uri = "/ItemLookUp.aspx?itemIdType="+itemIdType.name()+"&ItemId="+itemId+"&output=js&Version=20131101&ttbkey=" + ttbKey;
        log.info("URI ::: {}", BASE_URL + uri);

        return webClient
				.get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.flatMap(response -> response.bodyToMono(BookSearch.class))
				.flatMap(bookSearch -> Mono.just( bookSearch.getItem().get(0) ));
    }


    @Override
    public Mono<BookSearch> find(String query, QueryType queryType, int start, int maxResults) throws Exception {
        final String uri = "/ItemSearch.aspx?Version=20131101&output=js&ttbkey=" + ttbKey + 
        		"&start=" + start + "&MaxResults=" + maxResults + "&queryType=" + queryType.name() + "&Query=" + query;
        
        log.info("URI ::: {}", BASE_URL + uri);

        return webClient
        		.get()
        		.uri(uri)
        		.exchange()
        		.flatMap(response -> response.bodyToMono(BookSearch.class));
    }
}