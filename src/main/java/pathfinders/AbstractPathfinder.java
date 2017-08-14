package pathfinders;

import data.Layout;

import java.awt.*;
import java.util.LinkedList;

public abstract class AbstractPathfinder {
    protected Point sourcePoint;
    protected Point destPoint;

    protected Layout layout;

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

    public abstract Path findPath();
}
