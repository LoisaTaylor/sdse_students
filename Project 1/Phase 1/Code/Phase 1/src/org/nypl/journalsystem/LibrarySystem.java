package org.nypl.journalsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibrarySystem {
	Journal journal1;
	Journal journal2;
	Journal journal3;
	Journal journal4;
	Journal journal5;
	List<Journal> journals;
	Map<String, Publisher> publishers;
	Map<Integer, Author> authors;
	
	public LibrarySystem() {
		//TODO: Initialize system with default journals.
		journal1 = new Journal("Higher Education", "Springer","Germany","0018-1560");
		journal2 = new Journal("System", "Elsevier","Netherlands","0346-2511");
		journal3 = new Journal("Chem", "Elsevier","Netherlands","2451-9294");
		journal4 = new Journal("Nature", "Nature Research","Great Britain","1476-4687");
		journal5 = new Journal("Higher Education", "Springer","Germany","0147-2011");
		
		journals = new ArrayList<Journal>();
		journals.add(journal1);
		journals.add(journal2);
        journals.add(journal3);
        journals.add(journal4);
        journals.add(journal5);
        
        authors = new HashMap<Integer,Author>();
        
        publishers = new HashMap<String, Publisher>();
        for (Journal j: journals) {
        	Publisher pub = new Publisher(j.publisher,j.location);

        	if (publishers.containsKey(pub.name)){
        		continue;
        		}
        	else {
        		publishers.put(pub.name, pub);
        	}
        }
	}
	
	public void load() throws FileNotFoundException, IOException {
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
	
	protected void loadAuthors() throws FileNotFoundException, IOException {
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
	
	protected void loadArticles() throws FileNotFoundException, IOException {
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
				String[] indexs = index.split("; ");
				
				List<String> authornames = new ArrayList<String>();
				for(String i : indexs) {
					int id = Integer.parseInt(i);
					authornames.add((authors.get(id).name));
				}
				
				Article article = new Article(arid,title,authornames,SSN);
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
}
