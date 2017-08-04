package data;

import java.awt.*;
import java.util.ArrayList;

public class Node {
    private Point location;
    private double weight;
    private ArrayList<Point> neighbourNodes;

    public Node(){
        location = new Point(0, 0);
        weight = 0;
        neighbourNodes = new ArrayList<>();
    }

    public Node(Node copy){
        this.location = new Point(copy.getLocation());
        this.weight = copy.getWeight();
        this.neighbourNodes = new ArrayList<>();

        for(Point point : copy.getNeighbours()){
            this.addNeighbour(new Point(point));
        }
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setLocation(Point point){
        this.location = point;
    }

    public Point getLocation(){
        return location;
    }

    public void setNeighbours(ArrayList<Point> neighbours){
        this.neighbourNodes = neighbours;
    }

    public ArrayList<Point> getNeighbours(){
        return neighbourNodes;
    }

    public boolean addNeighbour(Point neighbour){
        return neighbourNodes.add(neighbour);
    }
}
