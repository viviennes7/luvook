package com.ms.luvook.board.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("B")
@Table(name = "board_book")
public class BookBoard extends Board {

    @Column(name = "title")
    private String title;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "cover")
    private String cover;

    @Column(name = "isbn")
    private long isbn;

    @Column(name = "isbn13")
    private long isbn13;

}
