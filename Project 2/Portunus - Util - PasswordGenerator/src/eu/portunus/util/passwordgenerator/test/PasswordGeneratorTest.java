package eu.portunus.util.passwordgenerator.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.portunus.util.passwordgenerator.CharacterSet;
import eu.portunus.util.passwordgenerator.PasswordGenerator;

public class PasswordGeneratorTest {
	//TODO: Write tests for the password generator.
	PasswordGenerator passwordgenerator;
	Collection<CharacterSet> characters;
	
	@BeforeEach
	public void setup() {
		passwordgenerator = new PasswordGenerator();
		characters = new ArrayList<CharacterSet>();
	}
	
	@AfterEach
	public void tearDown() {
		passwordgenerator = null;
		characters = null;
	}
	
	@Test
	public void TestAllCharacterSets() {
		characters.add(CharacterSet.DIGITS);
		characters.add(CharacterSet.UPPER_CASE);
		characters.add(CharacterSet.LOWER_CASE);
		characters.add(CharacterSet.MINUS);
		characters.add(CharacterSet.UNDERSCORE);
		characters.add(CharacterSet.SPACE);
		characters.add(CharacterSet.UNDERSCORE);

		String newpassword = passwordgenerator.generatePassword(15, characters);
		System.out.println(newpassword);
		assertEquals(15, newpassword.length());
	}
}
