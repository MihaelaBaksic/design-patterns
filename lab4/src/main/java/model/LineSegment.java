package model;

import render.Renderer;
import util.GeometryUtil;
import util.Point;
import util.Rectangle;

import java.util.List;
import java.util.Stack;

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
    public String getShapeID() {
        return "@LINE";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] args = data.strip().split(" ");
        if(!args[0].equals("@LINE")) return;
        try{
            GraphicalObject object = new LineSegment(new Point[] { new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2])),
                    new Point(Integer.parseInt(args[3]), Integer.parseInt(args[4]))});

            stack.push(object);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(List<String> rows) {
        rows.add(String.format("%s %d %d %d %d", getShapeID(),getHotPoint(0).getX(), getHotPoint(0).getY(),
                getHotPoint(1).getX(), getHotPoint(1).getY()));
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

    @Override
    public void render(Renderer r){
        r.drawLine(getHotPoint(0), getHotPoint(1));
    }

}
