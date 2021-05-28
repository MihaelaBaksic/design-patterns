package model;

import render.Renderer;
import util.Point;
import util.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Oval extends AbstractGraphicalObject{

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

    @Override
    public void render(Renderer r){
        Point center = getCenter();
        int cX = center.getX();
        int cY = center.getY();

        int width = Math.abs(cX-getHotPoint(0).getX())*2;
        int height = Math.abs(cY-getHotPoint(1).getY())*2;

        List<Point> points = new ArrayList<>();

        for(int i= 0; i<=width; i++){
            int x = cX - width/2 + i;
            int sq= calcForX(x, width/2, height/2, cX, cY);
            points.add(new Point(x,cY + sq));
            points.add(new Point(x, cY - sq));
        }

        r.fillPolygon(points.toArray(new Point[0]));
    }

    private Point getCenter(){
        return new Point(getHotPoint(1).getX(), getHotPoint(0).getY());
    }

    private int calcForX(int x, int a, int b, int cX, int cY){
        return (int) (b*Math.sqrt((1 - ((double)(x-cX)*(x-cX) / (double) (a*a)))));
    }
}
