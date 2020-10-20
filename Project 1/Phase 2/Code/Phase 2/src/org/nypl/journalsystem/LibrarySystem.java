package org.nypl.journalsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nypl.journalsystem.core.IArticle;
import org.nypl.journalsystem.core.IAuthor;
import org.nypl.journalsystem.core.IJournal;
import org.nypl.journalsystem.core.ILibrarySystem;

public class LibrarySystem implements ILibrarySystem{
	Journal journal1;
	Journal journal2;
	Journal journal3;
	Journal journal4;
	Journal journal5;
	List<Journal> journals;
	Map<Integer, Author> authors;
	Map<Integer, Article> articles;
	
	public LibrarySystem() {
		//TODO: Initialize system with default journals.
		journal1 = new Journal("Higher Education", new Publisher("Springer","Germany"),"0018-1560");
		journal2 = new Journal("System", new Publisher("Elsevier","Netherlands"),"0346-2511");
		journal3 = new Journal("Chem", new Publisher("Elsevier","Netherlands"),"2451-9294");
		journal4 = new Journal("Nature", new Publisher("Nature Research","Great Britain"),"1476-4687");
		journal5 = new Journal("Higher Education", new Publisher("Springer","Germany"),"0147-2011");
		
		journals = new ArrayList<Journal>();
		journals.add(journal1);
		journals.add(journal2);
        journals.add(journal3);
        journals.add(journal4);
        journals.add(journal5);
        
        authors = new HashMap<Integer,Author>();
        articles = new HashMap<Integer,Article>();

	}
	
	//public void load() throws FileNotFoundException, IOException {
	public void load() {
		loadAuthors();
		loadArticles();
	}
	
	private String cleanString(String rawValue) {
		rawValue = rawValue.trim();
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		else if(rawValue.startsWith("\"")) {
			return rawValue.substring(1, rawValue.length());
		}
		else if(rawValue.endsWith("\"")) {
			return rawValue.substring(0, rawValue.length()-1);
		}
		return rawValue;
	}
	
	private String cleanIndex(String rawValue) {
		rawValue = rawValue.trim();
		if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		return rawValue;
	}
	
	protected void loadAuthors() {
		File file = new File("data/Authors.csv");
		
		//TODO: Load authors from file 
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			br.readLine();
			
			String line;
			while ((line = br.readLine()) != null) {
				String[] rawValues = line.split(",");
				int aid = Integer.parseInt(rawValues[0]);
				String fullname = (cleanString(rawValues[2]) + " " + cleanString(rawValues[1]));
				Author author = new Author(aid, fullname);
				authors.put(aid,author);
			}
			
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}
	
	protected void loadArticles() {
		File file = new File("data/Articles.csv");
		
		//TODO: Load articles from file and assign them to appropriate journal
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			br.readLine();
			
			String line;
			while ((line = br.readLine()) != null) {
				String[] rawValues = line.split(",");
				int arid = Integer.parseInt(rawValues[0]);
				String title = cleanString(rawValues[1]);
				String index = cleanIndex(rawValues[2]);
				String SSN = cleanString(rawValues[3]);
				String arts = cleanIndex(rawValues[4]);
				String[] indexs = index.split("; ");
				String[] artindexs = arts.split(";");
				
				List<Integer> citedArt = new ArrayList<Integer>();
				List<Author> authorids = new ArrayList<Author>();
				
				for(String i : indexs) {
					int id = Integer.parseInt(i);
					authorids.add(authors.get(id));
				}
				
				for(String i : artindexs) {
					int id = Integer.parseInt(i);
					citedArt.add(id);
				}
				
				Article article = new Article(arid,title,authorids,SSN,citedArt);
				articles.put(arid,article);
				
				for (Journal j : journals) {
					if (j.ISSN.equals(SSN)){
						j.addArticle(article);
						
					}
				}
				
			}
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}
	
	public void listContents() {
		//TODO: Print all journals with their respective articles and authors to the console.
		for (Journal j: journals) {
			j.getArticles();
		}
	}
	
	public static final void main(String[] args) throws Exception {
		LibrarySystem librarySystem = new LibrarySystem();
		
		librarySystem.load();
		librarySystem.listContents();
		
	}

	@Override
	public Collection<? extends IAuthor> getAllAuthors() {
		return authors.values();
	}

	@Override
	public Collection<? extends IJournal> getAllJournals() {
		return journals;
	}

	@Override
	public Collection<? extends IArticle> getArticlesByAuthor(IAuthor arg0) {
		
		Map<IAuthor, List<Article>> authorMap = new HashMap<IAuthor, List<Article>>();
		for (Article a: articles.values()) {
			for (IAuthor auth: a.getAuthors()) {
				if (authorMap.containsKey(auth)){
					authorMap.get(auth).add(a);
				}
				else {
					List<Article> newart = new ArrayList<Article>();
					newart.add(a);
					authorMap.put(auth, newart);
				}
				
			}
		}
		return authorMap.get(arg0);
	}

	@Override
	public Collection<? extends IArticle> getArticlesCitedByArticle(IArticle arg0) {
		List<Integer> listcited = ((Article) arg0).citedArticleIds;
		List<IArticle> citedart = new ArrayList<IArticle>();
		for (Integer c:listcited) {
			citedart.add(articles.get(c));
		}
	
		return citedart;
	}

	@Override
	public Collection<? extends IArticle> getArticlesCitingArticle(IArticle arg0) {
		List<IArticle> articlesciting = new ArrayList<IArticle>();
		for (Article a: articles.values()) {
			for (Integer x: a.citedArticleIds) {
				if (x == ((Article) arg0).id){
					articlesciting.add(articles.get(x));
				}
			}
		}
		return articlesciting;
	}
}
