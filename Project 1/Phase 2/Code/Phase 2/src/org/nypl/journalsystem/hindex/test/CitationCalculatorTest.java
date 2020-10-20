package org.nypl.journalsystem.hindex.test;

import static org.junit.Assert.assertEquals;
import java.security.InvalidParameterException;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.nypl.journalsystem.Author;
import org.nypl.journalsystem.LibrarySystem;
import org.nypl.journalsystem.core.IAuthor;
import org.nypl.journalsystem.hindex.CitationCalculator;
import org.nypl.journalsystem.hindex.CitationCalculatorFixed;
import org.nypl.journalsystem.hindex.ICitationCalculator;

public class CitationCalculatorTest {
	private ICitationCalculator calculator;
	private CitationCalculatorFixed calculator2;
	LibrarySystem librarySystem;
	InvalidParameterException exception;
	
	@BeforeEach
	public void setup() {
		calculator = new CitationCalculator();
		calculator2 = new CitationCalculatorFixed();
		librarySystem = new LibrarySystem();
		librarySystem.load();
	}
	
	@AfterEach
	public void tearDown() {
		calculator = null;
		calculator2 = null;
	}
	
	//TODO: Implement test cases for the citation calculator
	@Test
	public void TestPositiveNumbers() {
		int[] array = {1,2,3,4};
		assertEquals(2, calculator.calculateHIndex(array));
		assertEquals(2, calculator2.calculateHIndex(array));
	}
	
	@Test
	public void TestNegativeNumbers() {
		int[] array = {-4,2,3,-1};
		assertEquals(2, calculator.calculateHIndex(array));
		assertEquals(2, calculator2.calculateHIndex(array));
	}
	
	@Test
	public void TestZeroArray() {
		int[] array = {0,0,0,0};
		assertEquals(0, calculator.calculateHIndex(array));
		assertEquals(0, calculator2.calculateHIndex(array));
	}
	
	@Test
	public void TestException() {
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			calculator2.calculateHIndex(null);
		});
		// error 1 - assertionexception not working in original

	}
	
	@Test
	public void TestAuthorInput() {
		//int result = 0;
		int result2 = 0;
		
		Collection<? extends IAuthor> authors = librarySystem.getAllAuthors();
		for (IAuthor auth : authors) { 
			//result = calculator.calculateHIndex(auth, librarySystem);
			result2 = calculator2.calculateHIndex(auth, librarySystem);
			break;
		}
		//assertEquals(1, result);
		assertEquals(2, result2);
		// error 2 - author input gives wrong output in original
		
	}
	
	@Test
	public void TestAuthorException() {
		IAuthor auth = new Author(0,"Loisa Taylor");
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			calculator2.calculateHIndex(auth, librarySystem);
		});
		// error 3 - author input exception not working in original
		
	}
}
