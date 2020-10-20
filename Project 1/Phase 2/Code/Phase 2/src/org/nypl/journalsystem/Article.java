package org.nypl.journalsystem;

import java.util.List;

import org.nypl.journalsystem.core.IArticle;
import org.nypl.journalsystem.core.IAuthor;

public class Article implements IArticle{
	int id;
	String title;
	List<Author> authors;
	String ISSN;
	List<Integer> citedArticleIds;
	String authornames;
	
	public Article(int id, String title, List<Author> authorids, String ISSN, List<Integer> citedArticleIds){
		this.id = id;
		this.title = title;
		this.authors = authorids;
		this.ISSN = ISSN;
		this.citedArticleIds = citedArticleIds;
		}
	
	public List<Integer> getCitations(){
		return citedArticleIds;
	}
	
	public void printArticle() {
		authornames = "";
		for (IAuthor a: authors) {
			authornames = authornames + a.getName() + ", ";
		}
		
		System.out.format("    Name:%s, Authors:%s \n",title, authornames.substring(0, authornames.length() - 2));
	}

	@Override
	public List<? extends IAuthor> getAuthors() {
		return authors;
	}

	@Override
	public String getTitle() {
		return title;
	}
}