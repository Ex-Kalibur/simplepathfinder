package ui;

import data.Layout;
import data.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LayoutComponent extends JComponent {
    private double scaleX;
    private double scaleY;
    private Layout layout;

    //Values are in values relative to the layout, not pixel coordinates
    private Point sourceLocation;
    private Point destinationLocation;

	/**
	 * Constructs an empty LayoutComponent whose Layout contains one cell
	 */
	public LayoutComponent(){
        this(new Layout());
    }

	/**
	 * Constructs a LayoutComponent with the specified Layout weights
	 * @param layout the Layout of this component
	 */
	public LayoutComponent(Layout layout){
        super();
        this.layout = layout;

        calculateScale();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isEnabled()){
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        doPlaceSource(e.getPoint());
                        repaint();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        doPlaceDestination(e.getPoint());
                        repaint();
                    }
                }
            }
        });
    }

	/**
	 * Sets the Layout of this LayoutComponent
	 * @param layout the Layout to set
	 */
	public void setLayoutComponent(Layout layout){
        this.layout = layout;

        calculateScale();

        invalidate();
        repaint();
    }

	/**
	 * Places the pathfinding source at the specified Point. The location will not change if it is already occupied
	 * or is a wall
	 * @param point the Point at which to place the pathfinding source
	 */
	private void doPlaceSource(Point point){
		Point cellLoc = convertPointToCell(point);
        if(!cellLoc.equals(sourceLocation) && !cellLoc.equals(destinationLocation)
				&& layout.getNodeAt(cellLoc) != null && layout.getNodeAt(cellLoc).getWeight() != 1){

			sourceLocation = cellLoc;
			System.out.println("Source placed in cell: " + sourceLocation);
		}
    }

	/**
	 * Places the pathfinding destination at the specified Point. The location will not change if it is already occupied
	 * or is a wall	 * @param point the Point at which to place the pathfinding destination
	 */
	private void doPlaceDestination(Point point){
		Point cellLoc = convertPointToCell(point);
        if(!cellLoc.equals(sourceLocation) && !cellLoc.equals(destinationLocation)
				&& layout.getNodeAt(cellLoc) != null && layout.getNodeAt(cellLoc).getWeight() != 1){

        	destinationLocation = cellLoc;
        	System.out.println("Destination placed in cell: " + sourceLocation);
		}
    }

	/**
	 * Calculates indexes of the cell containing the specified Point
	 * @param point the Point on which to find the containing cell
	 * @return a Point representing the coordinates of the cell
	 */
	private Point convertPointToCell(Point point){
        return new Point((int)(point.x / scaleX), (int)(point.y / scaleY));
    }

	/**
	 * Calculates the vertical and horizontal scaling factor used to determine the dimensions of each cell
	 */
	private void calculateScale(){
        Dimension size = getSize();
        Dimension layoutSize = layout.getSize();
        if(layoutSize.width > 0) {
            scaleX = size.width / layoutSize.width;
        } else {
            scaleX = size.width;
        }

        if(layoutSize.height > 0) {
            scaleY = size.height / layoutSize.height;
        } else {
            scaleY = size.height;
        }

        System.out.println("Size: " + size);
        System.out.println("LayoutSize: " + layoutSize);
        System.out.println("Scale set to: " + scaleX + " x " + scaleY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //Draw walls
		Dimension layoutSize = layout.getSize();
		for(int i = 0; i < layoutSize.width; i++){
			for(int j = 0; j < layoutSize.height; j++){
				Node node = layout.getNodeAt(i, j);
				if(node == null || node.getWeight() == 1){
					g2.setColor(Color.BLACK);
					g2.fillRect((int) (i * scaleX), (int) (j * scaleY), (int) scaleX, (int) scaleY);
				}
			}
		}

		//Variables for drawing source/destination circles
		int insetX = (int) (scaleX / 4);
		int insetY = (int) (scaleY / 4);
		int circleWidth = (int) (scaleX / 2);
		int circleHeight = (int) (scaleY / 2);

        //Draw source
        if(sourceLocation != null) {
            g2.setColor(new Color(255, 0, 0));
            g2.fillOval((int) (sourceLocation.x * scaleX) + insetX,
					(int) (sourceLocation.y * scaleY) + insetY,
					circleWidth,
					circleHeight);
        }

        //Draw destination
        if(destinationLocation != null) {
            g2.setColor(new Color(0, 0, 255));
			g2.fillOval((int) (destinationLocation.x * scaleX) + insetX,
					(int) (destinationLocation.y * scaleY) + insetY,
					circleWidth,
					circleHeight);
        }

        //Draw path
    }
}
