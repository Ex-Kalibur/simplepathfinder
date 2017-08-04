package pathfinders;

import java.awt.Point;
import java.util.*;

/**
 * Created by Christopher on 2017-07-29.
 */
public class Path implements Iterable<Point> {
	private Stack<Point> points;

	public Path(){
		points = new Stack<Point>();
	}

	public Path(Path copy){
		points = new Stack<Point>();

		points.addAll(copy.points);
	}

	public void add(Point point){
		points.push(point);
	}

	public Point remove(Point point){
		if(!points.isEmpty()){
			return points.pop();
		}
		return null;
	}

	public Point last(){
		return points.peek();
	}

	@Override
	public Iterator<Point> iterator(){
		return points.iterator();
	}
}
