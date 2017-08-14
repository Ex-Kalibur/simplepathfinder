package parsers;

import data.Layout;
import data.Node;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
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
            int lineNumber = 1;

            //For each line in the file
            ArrayList<double[]> weights = new ArrayList<>();
            while(scanner.hasNext()){
                //Split line into tokens
                String line = scanner.nextLine();
                String[] tokens = splitLine(line);

                if(numTokens == -1){
                    numTokens = tokens.length;
                } else if(numTokens != tokens.length){
                    throw new ParseException("Expected number of tokens do not match. " +
                            "Expected: " + numTokens + " Got: " + tokens.length, lineNumber);
                }

                //Parse each token
                double[] lineWeights = new double[tokens.length];
                for(int i = 0; i < tokens.length; i++){
                    lineWeights[i] = parseToken(tokens[i]);
                }

                //Add the parsed line of weights to the list
                weights.add(lineWeights);
            }

            //Convert array of weights to nodes
            Node[][] nodes = convertWeightsToNodes(weights);

            //Add to layout
            layout = new Layout(nodes);
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


    private static Node[][] convertWeightsToNodes(ArrayList<double[]> weights) {
        Node[][] nodes = new Node[weights.size()][];
        for(int x = 0; x < weights.size(); x++) {
            double[] weightCol = weights.get(x);
            Node[] nodeCol = new Node[weightCol.length];

            for(int y = 0; y < weightCol.length; y++) {
                double weight = weightCol[y];
                if(weight != 1) {
                    Node node = new Node();
                    node.setWeight(weight);
                    node.setLocation(new Point(x, y));

                    //Check neighbours: U, R, D, L
                    if(0 <= y-1 && weights.get(x)[y] != 1) node.addNeighbour(new Point(x, y-1));
                    if(x+1 < weightCol.length && weights.get(x)[y] != 1) node.addNeighbour(new Point(x+1, y));
                    if(y+1 < weights.size() && weights.get(x)[y] != 1) node.addNeighbour(new Point(x, y+1));
                    if(0 <= x-1 && weights.get(x)[y] != 1) node.addNeighbour(new Point(x-1, y));

                    nodeCol[y] = node;
                }
            }

            nodes[x] = nodeCol;
        }

        return nodes;
    }
}
