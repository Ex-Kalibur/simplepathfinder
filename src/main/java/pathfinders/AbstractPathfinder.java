package pathfinders;

import data.Layout;

import java.awt.*;
import java.util.LinkedList;

public abstract class AbstractPathfinder {
    private static Point sourcePoint;
    private static Point destPoint;

    private static Layout layout;

    public AbstractPathfinder(Layout layout){
        sourcePoint = null;
        destPoint = null;
        this.layout = layout;
    }

    public void setSource(Point source){
        this.sourcePoint = source;
    }

    public void setDestination(Point dest){
        this.destPoint = dest;
    }

    public void setLayout(Layout layout){
        this.layout = layout;
    }

    public abstract LinkedList<Point> findPath();
}
