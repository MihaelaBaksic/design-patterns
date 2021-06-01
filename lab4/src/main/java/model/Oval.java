package model;

import render.Renderer;
import util.GeometryUtil;
import util.Point;
import util.Rectangle;

import java.util.*;

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
        Point[] bbPoints = getOvalPoints();
        return Arrays.stream(bbPoints).mapToDouble( p -> {
            double mouseToCenter = GeometryUtil.distanceFromPoint(mousePoint, getCenter());
            double pToCenter = GeometryUtil.distanceFromPoint(p, getCenter());
            double mouseToP = GeometryUtil.distanceFromPoint(p, mousePoint);

            if(Math.abs(mouseToP + mouseToCenter - pToCenter) < 1)
                return 0;
            else
                return mouseToP;
        }).min().getAsDouble();
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
    public String getShapeID() {
        return "@OVAL";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] args = data.strip().split(" ");
        if(!args[0].equals("@OVAL")) return;
        try{
            GraphicalObject object = new Oval();
            Point[] hp = new Point[] { new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2])),
                    new Point(Integer.parseInt(args[3]), Integer.parseInt(args[4]))};
            object.setHotPoint(0, hp[0]);
            object.setHotPoint(1, hp[1]);

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
    public void render(Renderer r){
        Point[] points = getOvalPoints();
        r.fillPolygon(points);
    }

    private Point getCenter(){
        return new Point(getHotPoint(1).getX(), getHotPoint(0).getY());
    }

    private int calcForX(int x, int a, int b, int cX, int cY){
        return (int) (b*Math.sqrt((1 - ((double)(x-cX)*(x-cX) / (double) (a*a)))));
    }

    private Point[] getOvalPoints(){
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
        return pointsTopArch.toArray(new Point[0]);
    }
}
