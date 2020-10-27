package eu.portunus.util.passwordgenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class PasswordGenerator implements IPasswordGenerator {
	@Override
	public String generatePassword(int length, Collection<CharacterSet> characterSets) {
		if (characterSets == null || characterSets.isEmpty() || length < 0) {
			return "";
		}
		
		//TODO: Implement the password generator.
		Random ran = new Random(); 
		HashSet<CharacterSet> charset = new HashSet<CharacterSet>(characterSets);
		
		String password = "";
		for (int i = 0; i < length; i++) {
			int charchtersetindex = ran.nextInt(charset.size());
			Object character = charset.toArray()[charchtersetindex];
			
			
			if (character == CharacterSet.DIGITS) {
				int passwordindex = ran.nextInt(9);
				password += passwordindex;
			}
			else if (character == CharacterSet.UPPER_CASE) {
				String upper_case = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				int charindex = ran.nextInt(upper_case.length());
				char letter = upper_case.charAt(charindex);
				password += letter;
			}
			else if (character == CharacterSet.LOWER_CASE) {
				String lower_case = "abcdefghijklmnopqrstuvwxyz";
				int charindex = ran.nextInt(lower_case.length());
				char letter = lower_case.charAt(charindex);
				password += letter;
			}
			else if (character == CharacterSet.MINUS) {
				password += "-";
			}
			else if (character == CharacterSet.UNDERSCORE) {
				password += "_";
			}
			else if (character == CharacterSet.SPACE) {
				password += " ";
			}
			else {
				System.out.println("Invalid character Set");
				return "";
			}
		}
		return password;
	}

	public static void main(String[] args) {
		PasswordGenerator passwordgenerator = new PasswordGenerator();
		
		Collection<CharacterSet> characters = new ArrayList<CharacterSet>();
		characters.add(CharacterSet.DIGITS);
		characters.add(CharacterSet.UPPER_CASE);
		characters.add(CharacterSet.LOWER_CASE);
		characters.add(CharacterSet.MINUS);
		characters.add(CharacterSet.UNDERSCORE);
		characters.add(CharacterSet.SPACE);

		String newpassword = passwordgenerator.generatePassword(15, characters);
		System.out.println(newpassword);
	}
}