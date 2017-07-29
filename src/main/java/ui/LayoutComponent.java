package ui;

import data.Layout;

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

    public LayoutComponent(){
        this(new Layout());
    }

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

    public void setLayoutComponent(Layout layout){
        this.layout = layout;

        calculateScale();

        invalidate();
        repaint();
    }

    private void doPlaceSource(Point point){
        sourceLocation = convertCoordToPoint(point);
        System.out.println("Source placed in cell: " + sourceLocation);
    }

    private void doPlaceDestination(Point point){
        destinationLocation = convertCoordToPoint(point);
        System.out.println("Destination placed in cell: " + sourceLocation);
    }

    private Point convertCoordToPoint(Point point){
        return new Point((int)(point.x / scaleX), (int)(point.y / scaleY));
    }

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

        //Draw source
        if(sourceLocation != null) {
            g2.setColor(new Color(255, 0, 0));
            g2.fillOval((int) (sourceLocation.x * scaleX) + 4, (int) (sourceLocation.y * scaleY) + 4, 10, 10);
        }

        //Draw destination
        if(destinationLocation != null) {
            g2.setColor(new Color(0, 0, 255));
            g2.fillOval((int) (destinationLocation.x * scaleX) + 4, (int) (destinationLocation.y * scaleY) + 4, 10, 10);
        }

        //Draw path
    }
}
