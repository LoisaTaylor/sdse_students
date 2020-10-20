package org.nypl.journalsystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.nypl.journalsystem.core.IArticle;
import org.nypl.journalsystem.core.IJournal;
import org.nypl.journalsystem.core.IPublisher;

public class Journal implements IJournal {
	public String name;
	public Publisher publisher;
	public String ISSN;
	public List<Article> articles;
	
	public Journal(String name, Publisher publisher, String ISSN){
		this.name = name;
		this.publisher=publisher;
		this.ISSN=ISSN;
		this.articles = new ArrayList<Article>();
	}
	
	public void addArticle(Article article) {
		articles.add(article);
	}
	
	@Override
	public Collection<? extends IArticle> getArticles() {
		System.out.format("%s, publisher:%s, location:%s, ISSN:%s, Full issue:%s\n", name, publisher.name, publisher.location, ISSN, isFullIssue());
		for (Article a: articles) {
			a.printArticle();
		}
		System.out.println();
		return articles;
	}

	@Override
	public String getIssn() {
		return ISSN;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPublisher getPublisher() {
		return publisher;
	}

	@Override
	public boolean isFullIssue() {
		return (articles.size()>2);
	}
}
