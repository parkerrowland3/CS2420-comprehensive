package comprehensive;

import java.io.IOException;

public class RandomPhraseGenerator {

	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: java RandomPhraseGenerator <grammar-file> <number-of-phrases>");
		}

		String gFileName = args[0];
		int numberOfPhrases;

		try {
			numberOfPhrases = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The second argument must be an integer.");
		}

		GrammarUtility grammar;

		try {
			grammar = new GrammarUtility(gFileName);
		} catch (IOException e) {
			e.printStackTrace();
			return; // Exit the program if the grammar file cannot be read
		}

		for (int i = 0; i < numberOfPhrases; i++) {
			System.out.println(grammar.generatePhrase());
		}
	}

}
