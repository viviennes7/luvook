package com.ms.luvook.book.type;

/**
 * Created by vivie on 2017-04-19.
 */
public enum QueryType {
    KEYWORD,
    TITLE,
    AUTHOR,
    PUBLISHER,
    
    
    ITEM_NEW_ALL,			//신간 전체 리스트
    ITEM_NEW_SPECIAL,		//주목할 만한 신간 리스트
    ITEM_EDITOR_CHOICE,		//편집자 추천 리스트 (카테고리로만 조회가능)
    BESTSELLER,				//베스트셀러
    BLOG_BEST				//북플 베스트셀러(국내도서만 조회 가능)
    
    
}
