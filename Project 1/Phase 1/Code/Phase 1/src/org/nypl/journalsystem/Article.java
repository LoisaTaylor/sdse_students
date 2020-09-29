package org.nypl.journalsystem;

import java.util.List;

public class Article {
	int id;
	String title;
	List<String> authors;
	String ISSN;
	
	public Article(int id, String title, List<String> authors, String ISSN){
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.ISSN = ISSN;
		}
	
	public void printArticle() {
		System.out.format("    Name:%s, Authors:%s \n",title,authors);
	}
}