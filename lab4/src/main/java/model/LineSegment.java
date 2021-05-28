package model;

import util.GeometryUtil;
import util.Point;
import util.Rectangle;

import java.util.stream.Collectors;

public class LineSegment extends AbstractGraphicalObject{

    public LineSegment(){
        super(new Point[]{ new Point(0, 0), new Point(10,0)});
    }

    public LineSegment(Point[] points) {
        super(points);
    }

    @Override
    public String getShapeName(){
        return "Linija";
    }

    @Override
    public GraphicalObject duplicate(){
        return new LineSegment(new Point[] { getHotPoint(0), getHotPoint(1)});
    }

    @Override
    public double selectionDistance(Point mousePoint){
        return GeometryUtil.distanceFromLineSegment(getHotPoint(0), getHotPoint(1), mousePoint);
    }

    @Override
    public Rectangle getBoundingBox(){
        int x = hotPoints.stream().mapToInt(h -> h.getX()).min().getAsInt();
        int y = hotPoints.stream().mapToInt(h->h.getY()).min().getAsInt();

        int width = hotPoints.stream().mapToInt(p -> Math.abs(x - p.getX())).max().getAsInt();
        int height = hotPoints.stream().mapToInt(p -> Math.abs(y - p.getY())).max().getAsInt();

        return new Rectangle(x,y,width+1,height+1);
    }

}
