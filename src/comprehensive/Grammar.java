package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 */
public class Grammar {
	// the grammar is represented in a hashMap with keys being non-terminals, and
	// data being a list of productions
	// (A production is a line with nonTerminals or terminals separated by one
	// space).
	private Map<String, List<String>> grammar;
	private Random rand;

	/**
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public Grammar(String filename) throws FileNotFoundException {
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
	 * @throws FileNotFoundException
	 */
	private void parseFile(String filename) throws FileNotFoundException {
		try (Scanner scan = new Scanner(new File(filename))) {
			String currentArea = null; // This variable keeps track of which non-terminal definition we are in (i.e.
										// the string after an opening curly brace is the non-terminal we are defining)
			boolean isInBrackets = false;

			while (scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				// TODO Check if grammar is correct
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
			scan.close();
		}
	}

	private String generate(String nonTerminal) {
		List<String> productions = grammar.get(nonTerminal); // list of all productions associated with a non-terminal
																// definition

		if (productions == null) { // check to see if it is a terminal
			return nonTerminal; // Terminal symbol
		}

		String selectedProduction = productions.get(rand.nextInt(productions.size())); // Choose a random production

		// Split the production by white spaces into individual terminals and
		// non-terminals put into a basic string array.
		// (s+ would split based upon any number of white spaces, but a proper grammar
		// should never have more than one white space between).
		// A token represents a terminal or non terminal item in a production.
//        String[] tokens = selectedProduction.split("\\s+"); 

		StringBuilder newPhrase = new StringBuilder();
//        for (String token : tokens) {
//            if (token.startsWith("<") && (token.endsWith(">") || token.endsWith(">."))) { 
//            	// token in a non-terminal
//            	newPhrase.append(generate(token));
//            	newPhrase.append(" ");
//            } else if (token.startsWith("<") && !token.endsWith(">")){ 
//            	int bracketIndex = token.indexOf(">");
//            } else {
//            	newPhrase.append(token).append(" ");
//            }
//        }

		char[] prodArray = selectedProduction.toCharArray();
		boolean inBrackets = false;
		StringBuilder nonTerm = new StringBuilder();
		for (int i = 0; i < prodArray.length; i++) {
			if ((prodArray[i] != '<' && prodArray[i] != '>') && !inBrackets) {
				newPhrase.append(prodArray[i]);
			} else if (prodArray[i] == '<' || inBrackets) {
				inBrackets = true;
				
				nonTerm.append(prodArray[i]);
				if (prodArray[i] == '>') {
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
