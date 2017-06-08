package com.ms.luvook.book.domain;
import java.util.List;

import lombok.Data;

@Data
public class BookSearch {
	private String version;
	private String logo;
	private String title;
	private String link;
	private String pubDate;
	private String imageUrl;
	private int totalResults;
	private int startIndex;
	private int itemsPerPage;
	private String query;
	private int searchCategoryId;
	private String searchCategoryName;
	private List<Book> item;
}
