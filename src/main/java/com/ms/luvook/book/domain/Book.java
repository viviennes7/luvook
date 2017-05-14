package com.ms.luvook.book.domain;

import lombok.Data;

/**
 * Created by vivie on 2017-04-19.
 */
@Data
public class Book {
    private String title;
    private String author;
    private String description;
    private String isbn;
    private String isbn13;
    private String itemId;
    private String cover;
    private String categoryId;
    private String categoryName;
    private String publisher;
}
