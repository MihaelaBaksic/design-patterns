package model;

import render.Renderer;
import util.GeometryUtil;
import util.Point;
import util.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
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
        Rectangle boundingBox = this.getBoundingBox();
        Point[] bbPoints = boundingBox.getPointsOfRectangle();

        List<Double> distances = new ArrayList<>();

        if(mousePoint.getX() >= bbPoints[0].getX() && mousePoint.getX() <= bbPoints[1].getX() &&
                mousePoint.getY() >= bbPoints[0].getY() && mousePoint.getY() <= bbPoints[3].getY())
            return 0;

        distances.add(GeometryUtil.distanceFromLineSegment(bbPoints[0], bbPoints[1], mousePoint));
        distances.add(GeometryUtil.distanceFromLineSegment(bbPoints[1], bbPoints[2], mousePoint));
        distances.add(GeometryUtil.distanceFromLineSegment(bbPoints[2], bbPoints[3], mousePoint));
        distances.add(GeometryUtil.distanceFromLineSegment(bbPoints[3], bbPoints[0], mousePoint));

        return distances.stream().mapToDouble(d -> d).min().getAsDouble();
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

        List<Point> pointsTopArch = new ArrayList<>();
        List<Point> pointsBottomArch = new ArrayList<>();

        for(int i= 0; i<=width; i++){
            int x = cX - width/2 + i;
            int sq= calcForX(x, width/2, height/2, cX, cY);
            pointsTopArch.add(new Point(x,cY + sq));
            pointsBottomArch.add(new Point(x, cY - sq));
        }
        Collections.reverse(pointsBottomArch);
        pointsTopArch.addAll(pointsBottomArch);
        r.fillPolygon(pointsTopArch.toArray(new Point[0]));
    }

    private Point getCenter(){
        return new Point(getHotPoint(1).getX(), getHotPoint(0).getY());
    }

    private int calcForX(int x, int a, int b, int cX, int cY){
        return (int) (b*Math.sqrt((1 - ((double)(x-cX)*(x-cX) / (double) (a*a)))));
    }
}
