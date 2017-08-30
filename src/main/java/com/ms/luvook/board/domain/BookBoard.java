package com.ms.luvook.board.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    private String title;

    private int categoryId;

    private String cover;
    
    private String isbn;

    private String isbn13;

    @Transient
    private String bigCover;

	public BookBoard(int memberId, String contents, int grade, 
					 String title, int categoryId, String cover, String isbn, String isbn13) {
		super(memberId, contents, grade);
		this.title = title;
		this.categoryId = categoryId;	
		this.cover = cover;
		this.isbn = isbn;
		this.isbn13 = isbn13;
	}
	
	public void setBigCover(){
		this.bigCover = this.cover.replace("coversum", "cover");
	}
}
