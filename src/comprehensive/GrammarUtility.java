package comprehensive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class represents a grammar file parsed into a HashMap for easy
 * manipulation. It also houses utility functions to generate phrases based off
 * of the grammar file itself.
 * 
 */
public class GrammarUtility {

	// This Map represents the grammar. Each non-terminal acts as a key which is
	// mapped to a list of its corresponding productions.
	private Map<String, List<String>> grammar;
	private Random rand;

	/**
	 * This is the main constructor used for this class. It parses a given grammar
	 * file into a HashMap for easy access and manipulation.
	 * 
	 * @param filename - Location of grammar file.
	 * @throws IOException - If file cannot be opened.
	 */
	public GrammarUtility(String filename) throws IOException {
		grammar = new HashMap<>();
		rand = new Random();
		parseFile(filename);
	}

	/**
	 * This method uses a left-most derivation to generate a random phrase based off
	 * of the grammar in the respective instance of this class.
	 * 
	 * @return A randomly generated phrase.
	 */
	public String generatePhrase() {
		return generate("<start>");
	}

	/**
	 * This method uses a BufferedReader to parse an inputed grammar file into a
	 * HashMap with non-terminals as keys mapped to a list of their corresponding
	 * productions. Adheres strictly to the expected layout of a grammar file.
	 * 
	 * @param filename - Name of grammar file to be parsed.
	 * @throws IOException - If file cannot be opened.
	 */
	private void parseFile(String filename) throws IOException {
		try (BufferedReader bf = new BufferedReader(new FileReader(filename))) {
			String currentArea = null; // This variable keeps track of which non-terminal definition we are in (i.e.
										// the string after an opening curly brace is the non-terminal we are defining)
			boolean isInBrackets = false;
			String currentLine = null;

			while ((currentLine = bf.readLine()) != null) {

				if (currentLine.isEmpty()) // Skip white spaces
					continue;

				if (currentLine.equals("{")) { // Check to see if within a definition (if not within brackets, ignore
												// lines).
					isInBrackets = true;
					currentArea = null; // Reset current area to insure the clearing of previous area
				} else if (currentLine.equals("}")) {
					isInBrackets = false;
					currentArea = null; // reset current area for the next area
				} else if (isInBrackets)
					if (currentArea == null) { // If within brackets and current area is not defined, then the
												// non-terminal being defines is the current line
						currentArea = currentLine;
						grammar.putIfAbsent(currentLine, new ArrayList<>()); // Adds key if key doesn't exist
					} else {
						grammar.get(currentArea).add(currentLine); // Add the production to the list associated with the
																	// non-terminal being defined.
					}
			}
			bf.close(); // Closes file
		}
	}

	/**
	 * Private recursive method used to generate random phrases. It selects a random
	 * production from the list of productions mapped to a given non-terminal. This
	 * happens recursively until a production is completed.
	 * 
	 * @param nonTerminal
	 * @return
	 */
	private String generate(String nonTerminal) {
		List<String> productions = grammar.get(nonTerminal); // list of all productions associated with a non-terminal
																// definition

		if (productions == null) { // check to see if it is a terminal
			return nonTerminal; // Terminal symbol
		}

		String selectedProduction = productions.get(rand.nextInt(productions.size())); // Choose a random production
		char currentChar;
		StringBuilder newPhrase = new StringBuilder();

		boolean inBrackets = false;
		StringBuilder nonTerm = new StringBuilder();
		for (int i = 0; i < selectedProduction.length(); i++) {
			currentChar = selectedProduction.charAt(i);
			if ((currentChar != '<' && currentChar != '>') && !inBrackets) {
				newPhrase.append(currentChar);
			} else if (currentChar == '<' || inBrackets) {
				inBrackets = true;

				nonTerm.append(currentChar);
				if (selectedProduction.charAt(i) == '>') {
					inBrackets = false;
					newPhrase.append(generate(nonTerm.toString()));
					nonTerm.setLength(0);
				} else
					continue;
			}
		}

		return newPhrase.toString().trim();
	}
}
