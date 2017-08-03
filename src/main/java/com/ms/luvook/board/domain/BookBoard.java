package com.ms.luvook.board.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

	public BookBoard(int memberId, String contents, int grade, 
					 String title, int categoryId, String cover, long isbn, long isbn13) {
		super(memberId, contents, grade);
		this.title = title;
		this.categoryId = categoryId;	
		this.cover = cover;
		this.isbn = isbn;
		this.isbn13 = isbn13;
	}
    
}
