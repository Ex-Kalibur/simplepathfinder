package data;

import java.awt.*;

/**
 *
 */
public class Layout {
    private Dimension size;
    private double[][] weights;

    public Layout(){
        size = new Dimension(1, 1);
        weights = new double[1][1];
        weights[0][0] = 0;
    }

    /**
     * Constructs a new Layout with the specified weights
     * @param weights
     */
    public Layout(double[][] weights){
        int height = 0;
        int width = 0;
        if(weights.length > 0) {
            width = weights.length;
            height = weights[0].length;
        }
        size = new Dimension(width, height);
        this.weights = weights;
    }

    public double[][] getWeights(){
        return weights;
    }

    public Dimension getSize(){
        return size;
    }

	public double getWeightAt(Point point){
		return getWeightAt(point.x, point.y);
	}
    public double getWeightAt(int x, int y){
        return weights[x][y];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(double[] row : weights){
            for(double weight : row){
                sb.append(weight + " ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
