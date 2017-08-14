package pathfinders;

import data.Layout;
import data.Node;
import data.NodeScore;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created by Christopher on 2017-08-04.
 */
public class AStarPathfinder extends AbstractPathfinder{
	public enum HeuristicType {EUCLIDEAN_DISTANCE, EUCLIDEAN_SQ_DISTANCE, MANHATTAN_DISTANCE};
	private HeuristicType heuristic;

	/**
	 * Constructs an Pathfinder that implements the A* algorithm, using the specified Layout
	 * @param layout the Layout to navigate
	 */
	public AStarPathfinder(Layout layout, HeuristicType heuristic){
		super(layout);

		this.heuristic = heuristic;
	}

	/**
	 *
	 * @param fromNode
	 * @param toNode
	 * @return
	 */
	private double calculateDistanceFromHeuristic(Node fromNode, Node toNode){
		double distance;

		switch(heuristic){
			case EUCLIDEAN_DISTANCE:
				distance = calculateEuclideanDistance(fromNode, toNode);
				break;
			case EUCLIDEAN_SQ_DISTANCE:
				distance = calculateEuclideanSqDistance(fromNode, toNode);
				break;
			case MANHATTAN_DISTANCE:
				distance = calculateManhattanDistance(fromNode, toNode);
				break;
			default:
				distance = 0;
		}

		return distance;
	}

	/**
	 * Calculates the GScore between two nodes based on the heuristic
	 * @param fromNode the Node to start calculating from
	 * @param toNode   the Node to stop calculate to
	 * @return the GScore incurred between the two Nodes
	 */
	private double calculateGScore(Node fromNode, Node toNode){
		return 1 + calculateDistanceFromHeuristic(fromNode, toNode) * fromNode.getWeight();
	}

	/**
	 * Calculates the HScore estimate between two nodes based on the heuristic
	 * @param fromNode the Node to start calculating from
	 * @param toNode   the Node to stop calculate to
	 * @return the HScore estimate between the two Nodes
	 */
	private double calculateHScore(Node fromNode, Node toNode){
		return calculateDistanceFromHeuristic(fromNode, toNode);
	}

	/**
	 * Calculates the Euclidean Distance between two nodes
	 * @param fromNode the Node to start calculating distance from
	 * @param toNode   the Node to stop calculate distance to
	 * @return the Euclidean Distance between the two specified Nodes
	 */
	private double calculateEuclideanDistance(Node fromNode, Node toNode){
		return Math.sqrt(calculateEuclideanSqDistance(fromNode, toNode));
	}

	/**
	 * Calculates the Squared Euclidean Distance between two nodes
	 * @param fromNode the Node to start calculating distance from
	 * @param toNode   the Node to stop calculate distance to
	 * @return the Squared Euclidean Distance between the two specified Nodes
	 */
	private double calculateEuclideanSqDistance(Node fromNode, Node toNode){
		Point fromLocation = fromNode.getLocation();
		Point toLocation = toNode.getLocation();

		return Math.pow(toLocation.x - fromLocation.x, 2) + Math.pow(toLocation.y - fromLocation.y, 2);
	}

	/**
	 * Calculates the Manhattan Distance between two nodes
	 * @param fromNode the Node to start calculating distance from
	 * @param toNode the Node to stop calculate distance to
	 * @return the Manhattan Distance between the two specified Nodes
	 */
	private double calculateManhattanDistance(Node fromNode, Node toNode){
		Point fromLocation = fromNode.getLocation();
		Point toLocation = toNode.getLocation();

		return Math.abs(toLocation.x - fromLocation.x) + Math.abs(toLocation.y - fromLocation.y);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public Path findPath(){
		//Get Source Node
		Node sourceNode = layout.getNodeAt(this.sourcePoint);

		//The set of nodes already evaluated
		Map<Node, Double> closedSet = new HashMap<>();

		//The set of currently discovered
		Map<Node, Double> openSet = new HashMap<>();
		//Initially, only the start node is known
		openSet.put(sourceNode, 0.0);

		//Map of cost from source->Node
		Map<Node, Double> gScoreMap = new HashMap<>();
		gScoreMap.put(sourceNode, 0.0);
		Map<Node, Double> fScoreMap = new HashMap<>();
		fScoreMap.put(sourceNode, 0.0);

		Node foundNode = null;
		while(!openSet.isEmpty()){
			System.out.println("OpenSet: " + openSet.toString());
			System.out.println("ClosedSet: " + closedSet.toString());

			//Find the node with the least fScore
			Map.Entry<Node, Double> min = openSet.entrySet().stream().min(Map.Entry.comparingByValue()).get();
			Node current = min.getKey();
			System.out.println("Current: " + current.toString());

			//For each neighbour
			List<Node> neighbours = layout.getNodeList(current.getNeighbours());
			for(Node successor : neighbours){
				System.out.println("Successor: " + successor.toString());

				if(successor.getLocation().equals(destPoint)){
					//Stop the search
					System.out.println("FOUND");
					foundNode = successor;
					break;
				} else {
					successor.setParent(current.getLocation());

					//Calculate the successor's g score
					double gScore = gScoreMap.get(current) + calculateGScore(current, successor);
					gScoreMap.put(successor, gScore);

					//Calculate the successor's h score
					double hScore = calculateHScore(current, successor);
					double fScore = gScore + hScore;
					System.out.println("gScore: " + gScore + " hScore: " + hScore + " fScore: " + fScore);

					Double openListScore = openSet.get(successor);
					if(openListScore != null && openListScore < fScore){
						//If this node is already on the open List with a lower score, do nothing
					} else {
						Double closedListScore = closedSet.get(successor);
						if(closedListScore != null && closedListScore < fScore){
							//If this node is already on the closed List with a lower score, do nothing
						} else {
							openSet.put(successor, fScore);
						}
					}
				}
			}
			closedSet.put(current, fScoreMap.get(current));

			if(foundNode != null) break;
		}

		return reconstructPath(foundNode, closedSet);
	}

	/**
	 *
	 * @param endNode
	 * @param closedSet
	 * @return
	 */
	private Path reconstructPath(Node endNode, Map<Node, Double> closedSet){
		ArrayList<Node> nodes = new ArrayList<>();

		Node previous = endNode;
		do{

			nodes.add(previous);

			//Find parent in closedSet - we need this because parent information is stored in this set
			for(Node node : closedSet.keySet()){
				if(previous.getParent().equals(node.getLocation())){
					previous = node;
				}
			}
		} while(previous != null);

		Path path = new Path();
		for(int i = nodes.size() - 1; i >= 0; i--){
			path.add(nodes.get(i).getLocation());
		}

		return path;
	}
}
