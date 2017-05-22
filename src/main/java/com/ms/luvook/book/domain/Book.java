package com.ms.luvook.book.domain;

import lombok.Data;

/**
 * Created by vivie on 2017-04-19.
 */
@Data
public class Book {
    private String title;
    private String link;
    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private String isbn13;
    private int itemId;
    private int priceSales;
    private int priceStandard;
    private String mallType;
    private String stockStatus;
    private String mileage;
    private String cover;
    private String categoryId;
    private String categoryName;
    private String publisher;
    private int salesPoint;
    private boolean fixedPrice;
    private int customerReviewRank;
    private BookSubInfo subInfo;
}
