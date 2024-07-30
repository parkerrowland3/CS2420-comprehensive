package comprehensive;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Template class which can be used as a skeleton for perfoming timing tests
 * Set the experiment parameters at the top.
 * Look for "FILL IN" comments.
 * 
 * @Author: Eric Heisler
 * @version 2024/5/10
 */
public class IncreasingNumberOfNonTerminalsTimer {
	
	// Set these numbers for your experiment ///////////////////////////////
	private static final int firstN = 10; // smallest value of N
	private static final int incrementForN = 10; // how much N increases by each step
	private static final int numberOfNValues = 10; // how many steps (values of N)
	// Increase timesToLoop to get more accurate, smoother results.
	private static final int timesToLoop = 100000;
	////////////////////////////////////////////////////////////////////////
	
	/**
	 * The main method runs the timing experiment, which prints results to the console.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		long startTime, afterTimedCode, afterCompensationLoop;
		
		//--------Self initialized variables ---------------
		GrammarUtility grammar10;
		try {
			grammar10 = new GrammarUtility("src/comprehensive/grammar_10.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar20;
		try {
			grammar20 = new GrammarUtility("src/comprehensive/grammar_20.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar30;
		try {
			grammar30 = new GrammarUtility("src/comprehensive/grammar_30.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar40;
		try {
			grammar40 = new GrammarUtility("src/comprehensive/grammar_40.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar50;
		try {
			grammar50 = new GrammarUtility("src/comprehensive/grammar_50.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar60;
		try {
			grammar60 = new GrammarUtility("src/comprehensive/grammar_60.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar70;
		try {
			grammar70 = new GrammarUtility("src/comprehensive/grammar_70.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar80;
		try {
			grammar80 = new GrammarUtility("src/comprehensive/grammar_80.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar90;
		try {
			grammar90 = new GrammarUtility("src/comprehensive/grammar_90.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		GrammarUtility grammar100;
		try {
			grammar100 = new GrammarUtility("src/comprehensive/grammar_100.txt");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		
		// Create a grammar array
		GrammarUtility[] grammarUtilityArr = {grammar10, grammar20, grammar30, grammar40, grammar50, grammar60, grammar70, grammar80, grammar90, grammar100};
		
		// You can also manually set values in the array if desired.
	    long[] problemSizes = new long[numberOfNValues];
	    problemSizes[0] = firstN;
	    for(int i = 1; i < numberOfNValues; i++) {
	    	problemSizes[i] = problemSizes[i - 1] + incrementForN;
	    }
	    
	    // Print a header
        System.out.println("size(N)    time(ns)");
        
        // Run for each value of problem size
    	int grammarUtilityArrIndex = 0;
        for(long N : problemSizes) {
        	
        	// Set up for this value of N
        	// FILL IN ////////////////////////////////////////////////
        	GrammarUtility currentGrammar = grammarUtilityArr[grammarUtilityArrIndex];
        	grammarUtilityArrIndex++;
        	///////////////////////////////////////////////////////////

            // Warm up (garbage collection, etc.)
            startTime = System.nanoTime();
            while(System.nanoTime() - startTime < 1000000000) {} // empty loop for waiting

            // Time the code you are interested in
            startTime = System.nanoTime();
            for(int i = 0; i < timesToLoop; i++) {
            	// FILL IN ////////////////////////////////////////////////
            	currentGrammar.generatePhrase();
            	///////////////////////////////////////////////////////////
            }
            afterTimedCode = System.nanoTime();
            
            // Compensation time to subtract overhead costs
            for(int i = 0; i < timesToLoop; i++) {
            	// FILL IN ////////////////////////////////////////////////
            	
            	///////////////////////////////////////////////////////////
            }
            afterCompensationLoop = System.nanoTime();
            
            long compensationTime = afterCompensationLoop - afterTimedCode;
            long totalTimedCodeTime = afterTimedCode - startTime;
            double averageTime = (double)(totalTimedCodeTime - compensationTime)/timesToLoop;
            
            // Here you can print out or process the result for this value of N
            System.out.println(N + " \t" + averageTime);
        }
	}
}
