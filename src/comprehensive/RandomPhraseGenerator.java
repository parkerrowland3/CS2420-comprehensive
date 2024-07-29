package comprehensive;

import java.io.IOException;

/**
 * This method generates random phrases based off a user inputed grammar file.
 * To execute, the program requires two command-line arguments: the grammar file
 * path, and the number phrases to be generated.
 * 
 */
public class RandomPhraseGenerator {
	/**
	 * This main method drives the random phrase generation.
	 * 
	 * @param args - Grammar File Name & Number of phrases to be generated
	 */
	public static void main(String[] args) {
		// Ensures proper number of arguments exist.
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: java RandomPhraseGenerator <grammar-file> <number-of-phrases>");
		}

		String gFileName = args[0];
		int numberOfPhrases;
		// Ensures the second argument is an integer.
		try {
			numberOfPhrases = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The second argument must be an integer.");
		}
		// Declare GrammarUtility class.
		GrammarUtility grammar;

		// Ensures grammar file can be opened. Terminates program if it can't.
		try {
			grammar = new GrammarUtility(gFileName);
		} catch (IOException e) {
			System.out.println("Error: Grammar file could not be opened.");
			return;
		}
		// Prints number of phrases specified by the user. Catches and handles a
		// StackOverflowError if the grammar file is too self-referential.
		try {
			for (int i = 0; i < numberOfPhrases; i++) {
				System.out.println(grammar.generatePhrase());
			}
		} catch (StackOverflowError e) {
			System.out.println("Error: Grammar file has too many loops in its productions.");
			return;
		}
	}

}
