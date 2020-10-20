package org.nypl.journalsystem.hindex;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;

import org.nypl.journalsystem.core.IArticle;
import org.nypl.journalsystem.core.IAuthor;
import org.nypl.journalsystem.core.ILibrarySystem;

public class CitationCalculatorFixed implements ICitationCalculator {
	
	public int calculateHIndex(IAuthor author, ILibrarySystem librarySystem) {
		try {
			Collection<? extends IArticle> arts = librarySystem.getArticlesByAuthor(author);
			int[] array = new int[arts.size()];
			int count = 0;
			for (IArticle a : arts) {
				int artcnt = librarySystem.getArticlesCitedByArticle(a).size();
				array[count] = artcnt;
				count++; 
			}
		
			return calculateHIndex(array);
		}
		catch (Exception ex) {
	        throw new InvalidParameterException
	            ("Error constructing "+ ex);
		}
	}

	public int calculateHIndex(int[] citationsPerArticle) {
		try {
			Arrays.sort(citationsPerArticle);
			 
		    int result = 0;
		    for(int i=citationsPerArticle.length-1; i>=0; i--){
		        int cnt = citationsPerArticle.length-i;
		        if(citationsPerArticle[i]>=cnt){
		            result = cnt;
		        }else{
		            break;
		        }
		    }
		 
		    return result;
		}
		catch (Exception ex) {
	        throw new InvalidParameterException
	            ("Error constructing "+ ex);
		}
	}
}

