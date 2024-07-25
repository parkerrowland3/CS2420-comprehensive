package comprehensive;

import java.io.FileNotFoundException;

public class RandomPhraseGenerator {

	public static void main(String[] args) throws FileNotFoundException {
		int numberOfPhrases;
		String gFileName;
		Grammar grammar;
		
		try {
			gFileName = args[0];
			
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		
		try {
			numberOfPhrases = Integer.parseInt(args[1]);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		grammar = new Grammar(gFileName);
		
		for (int i = 0; i < numberOfPhrases; i++) {
			System.out.println(grammar.generatePhrase());
		}

	}

}
