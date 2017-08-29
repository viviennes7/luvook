package com.ms.luvook.board.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
    private String isbn;

    @Column(name = "isbn13")
    private String isbn13;

	public BookBoard(int memberId, String contents, int grade, 
					 String title, int categoryId, String cover, String isbn, String isbn13) {
		super(memberId, contents, grade);
		this.title = title;
		this.categoryId = categoryId;	
		this.cover = cover;
		this.isbn = isbn;
		this.isbn13 = isbn13;
	}

}
