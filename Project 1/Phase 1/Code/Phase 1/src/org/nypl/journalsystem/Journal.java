package org.nypl.journalsystem;

import java.util.ArrayList;
import java.util.List;

public class Journal {
	public String name;
	public String publisher;
	public String location;
	public String ISSN;
	public List<Article> articles;
	
	public Journal(String name, String publisher, String location, String ISSN){
		this.name = name;
		this.publisher=publisher;
		this.location=location;
		this.ISSN=ISSN;
		this.articles = new ArrayList<Article>();
	}
	
	public void addArticle(Article article) {
		articles.add(article);
	}
	
	public void getArticles() {
		if (articles.size()>2){
			System.out.format("Name:%s, publisher:%s, location:%s, ISSN:%s, Full issue\n", name, publisher, location, ISSN);
		}
		else {
			System.out.format("Name:%s, publisher:%s, location:%s, ISSN:%s, Not full issue\n", name, publisher, location, ISSN);
		}
		
		for (Article a: articles) {
			a.printArticle();
		}
		System.out.println();
	}
}
