package parsers;

import data.Layout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LayoutParser {

    /**
     * Parses the specified File, generating a Layout representing the data
     * @param file the File to parse
     * @return a Layout representing the parsed data
     */
    public static Layout parseLayout(File file){
        Layout layout = null;

        try {
            Scanner scanner = new Scanner(file);
            int numTokens = -1;

            //For each line in the file
            ArrayList<double[]> weights = new ArrayList<>();
            while(scanner.hasNext()){
                //Split line into tokens
                String line = scanner.nextLine();
                String[] tokens = splitLine(line);

                if(numTokens == -1){
                    numTokens = tokens.length;
                } else if(numTokens != tokens.length){
                    throw new IllegalArgumentException();
                }

                //Parse each token
                double[] lineWeights = new double[tokens.length];
                for(int i = 0; i < tokens.length; i++){
                    lineWeights[i] = parseToken(tokens[i]);
                }

                //Add the parsed line of weights to the list
                weights.add(lineWeights);
            }

            //Convert ArrayList to double[][]
            double[][] layoutWeights = new double[weights.size()][];
            for(int i = 0; i < weights.size(); i++) {
                layoutWeights[i] = weights.get(i);
            }

            //Add to layout
            layout = new Layout(layoutWeights);
            System.out.println(layout);
        } catch(Exception e){
            e.printStackTrace(System.err);
        }

        return layout;
    }

    /**
     * Splits the line on spaces
     * @param line the String line to split
     * @return
     */
    private static String[] splitLine(String line){
        return line.split(" ");
    }

    /**
     * Parses a string into a weight. Checks if the token contains an double
     * @param token the String token to parse
     * @return
     */
    private static double parseToken(String token){
        return Double.parseDouble(token);
    }
}
