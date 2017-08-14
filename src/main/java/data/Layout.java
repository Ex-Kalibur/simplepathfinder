package data;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
public class Layout {
    private Dimension size;
    private Node[][] nodes;

    public Layout(){
        size = new Dimension(1, 1);
        nodes = new Node[1][1];
        nodes[0][0] = new Node();
    }

    /**
     * Constructs a new Layout with the specified nodes
     * @param nodes
     */
    public Layout(Node[][] nodes){
        int height = 0;
        int width = 0;
        if(nodes.length > 0) {
            width = nodes.length;
            height = nodes[0].length;
        }
        size = new Dimension(width, height);
        this.nodes = nodes;
    }

    /**
    * Gets the array of Nodes representing this Layout
    * @return the array of Nodes representing this Layout
    */
    public Node[][] getNodes(){
        return nodes;
    }

    /**
    * Gets the Dimensions of this Layout
    * @return the Dimensions of this Layout
    */
    public Dimension getSize(){
        return size;
    }

    public Node getNodeAt(int x, int y) throws IndexOutOfBoundsException {
        Node node = null;
        if(0 <= x && x < size.width){
            if(0 <= y && y < size.height){
                node = nodes[x][y];
            } else {
                throw new IndexOutOfBoundsException("Index: " + y + " Height: " + size.height);
            }
        } else {
            throw new IndexOutOfBoundsException("Index: " + x + " Height: " + size.width);
        }
        return node;
    }

    public Node getNodeAt(Point point) throws IndexOutOfBoundsException{
    	return getNodeAt(point.x, point.y);
	}

	public ArrayList<Node> getNodeList(ArrayList<Point> points){
    	ArrayList<Node> nodes = new ArrayList<>();

    	for(Point point : points){
    		Node node = getNodeAt(point);
    		nodes.add(node);
		}

		return nodes;
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Node[] row : nodes){
            for(Node node : row){
                if(node != null){
                	sb.append(node.getWeight() + " ");
				} else {
                	sb.append("1 ");
				}
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
