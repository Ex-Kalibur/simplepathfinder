package data;

import java.awt.*;
import java.util.ArrayList;

public class Node implements Comparable<Node>{
    private Point location;
    private Point parent;
    private double weight;
    private ArrayList<Point> neighbourNodes;

    public Node(){
        location = new Point(0, 0);
        parent = null;
        weight = 0;
        neighbourNodes = new ArrayList<>();
    }

    public Node(Node copy){
        this.location = new Point(copy.getLocation());
        this.parent = new Point(copy.getParent());
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

	public void setParent(Point point){
		this.parent = point;
	}

	public Point getParent(){
		return parent;
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

	@Override
	public int compareTo(Node o){
		return Double.compare(this.weight, o.weight);
	}

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Node node = (Node) o;

		return getLocation().equals(node.getLocation());
	}

	@Override
	public int hashCode(){
		return getLocation().hashCode();
	}

	@Override
	public String toString(){
		return "Node{" +
				"location=" + location +
				", parent=" + parent +
				", weight=" + weight +
				", neighbourNodes=" + neighbourNodes +
				'}';
	}
}
