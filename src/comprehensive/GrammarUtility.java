package comprehensive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 */
public class GrammarUtility {
	// the grammar is represented in a hashMap with keys being non-terminals, and
	// data being a list of productions
	// (A production is a line with nonTerminals or terminals separated by one
	// space).
	private Map<String, List<String>> grammar;
	private Random rand;

	/**
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public GrammarUtility(String filename) throws IOException {
		grammar = new HashMap<>();
		rand = new Random();
		parseFile(filename);
	}

	public String generatePhrase() {
		return generate("<start>");
	}

	/**
	 * 
	 * @param filename
	 * @throws IOException
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
			bf.close();
		}
	}

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
