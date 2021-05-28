package model;

import util.Point;
import util.Rectangle;

public class Oval extends AbstractGraphicalObject{

    private Point center;

    public Oval(){
        super(new Point[]{new Point(10, 0), new Point(0, 10)});
    }

    public Oval(Point[] points){
        super(points);
    }

    @Override
    public String getShapeName(){
        return "Oval";
    }

    @Override
    public double selectionDistance(Point mousePoint){
        return 0;
    }

    @Override
    public Rectangle getBoundingBox(){
        Point center = getCenter();
        int width = Math.abs(center.getX()-getHotPoint(0).getX())*2;
        int height = Math.abs(center.getY()-getHotPoint(1).getY())*2;

        int x = center.getX() -  width/2;
        int y = center.getY() - height/2;

        return new Rectangle(x, y, width, height);
    }

    @Override
    public GraphicalObject duplicate(){
        return new Oval(new Point[]{getHotPoint(0), getHotPoint(1)});
    }

    private Point getCenter(){
        return new Point(getHotPoint(1).getX(), getHotPoint(0).getY());
    }
}
