package comprehensive;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GrammarGenerator {

    private static final Random rand = new Random();

    public static void generateGrammarFile(String filename, int numNonTerminals, int numProductions, int numOptions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < numNonTerminals; i++) {
                String nonTerminal = "<non_terminal" + i + ">";
                writer.write("{\n");
                writer.write(nonTerminal + "\n");
                for (int j = 0; j < numProductions; j++) {
                    StringBuilder production = new StringBuilder();
                    int productionLength = rand.nextInt(5) + 1; // production length between 1 and 5
                    for (int k = 0; k < productionLength; k++) {
                        if (rand.nextBoolean()) {
                            production.append("<non_terminal").append(rand.nextInt(numNonTerminals)).append(">");
                        } else {
                            production.append("terminal").append(rand.nextInt(numOptions));
                        }
                        if (k < productionLength - 1) {
                            production.append(" ");
                        }
                    }
                    writer.write(production.toString() + "\n");
                }
                writer.write("}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generateGrammarFile("small_grammar.txt", 5, 3, 5);
        generateGrammarFile("medium_grammar.txt", 10, 5, 10);
        generateGrammarFile("large_grammar.txt", 20, 10, 20);
        generateGrammarFile("very_large_grammar.txt", 50, 20, 50);
    }
}
