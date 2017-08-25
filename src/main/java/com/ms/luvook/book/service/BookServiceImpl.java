package com.ms.luvook.book.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.ms.luvook.book.domain.Book;
import com.ms.luvook.book.domain.BookSearch;
import com.ms.luvook.book.type.ItemIdType;
import com.ms.luvook.book.type.QueryType;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Created by vivie on 2017-05-11.
 */
@Slf4j
@Service("bookServiceImpl")
public class BookServiceImpl implements BookService{
	static final private String BASE_URL = "http://www.aladin.co.kr/ttb/api";

	@Value("${authkey.aladin}")
	String ttbKey;
    private WebClient webClient = WebClient.create(BASE_URL);
    final Gson gson = new Gson();
    
    @Override
    public Mono<Book> findOne(String itemId, ItemIdType itemIdType) {
        final String uri = "/ItemLookUp.aspx?itemIdType="+itemIdType.name()+"&ItemId="+itemId+"&cover=big&output=js&Version=20131101&ttbkey=" + ttbKey;

        return webClient
				.get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.flatMap(response -> response.bodyToMono(BookSearch.class))
				.flatMap(bookSearch -> Mono.just( bookSearch.getItem().get(0) ));
    }

    @Override
    public Mono<BookSearch> find(String query, QueryType queryType, int start, int maxResults) {
    	final String uri = "/ItemSearch.aspx?Version=20131101&Output=js&TTBKey=" + ttbKey + 
        		"&Start=" + start + "&MaxResults=" + maxResults + "&QueryType=" + queryType.name() + "&Query=" + query;
        
    	log.info("uri ::: {}", uri);
    	
        return webClient
        		.get()
        		.uri(uri)
        		.exchange()
        		.flatMap(response -> response.bodyToMono(BookSearch.class));
    }
    
    
    /**
     * QueryType 아래부분의 항목들 검색
     * */
    @Override
    public Mono<BookSearch> findByType(QueryType queryType, int start, int maxResults){
    	final String uri = "/ItemList.aspx?Version=20131101&Output=js&SearchTarget=book&TTBKey=" + ttbKey + 
        		"&Start=" + start + "&MaxResults=" + maxResults + "&QueryType=" + queryType.name();
    	
    	return webClient
        		.get()
        		.uri(uri)
        		.exchange()
        		.flatMap(response -> response.bodyToMono(BookSearch.class));

    }
}
