package state;

import model.DocumentModel;
import model.GraphicalObject;
import render.Renderer;
import util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EraseState implements State{

    private List<Point> points;
    private DocumentModel model;

    public EraseState(DocumentModel model){
        points = new ArrayList<>();
        this.model = model;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        points.clear();
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

        List<GraphicalObject> objects = model.list();

        new ArrayList<>(objects).stream().filter( o -> {
            try{
                double distance = points.stream().mapToDouble( p -> o.selectionDistance(p)).min().getAsDouble();
                return distance < 1;
            }
            catch (NoSuchElementException e){
                return false;
            }
        }).forEach( o -> model.removeGraphicalObject(o));

        points.clear();
        model.notifyListeners();
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        points.add(mousePoint);
        model.notifyListeners();
    }

    @Override
    public void keyPressed(int keyCode) {}

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {}

    @Override
    public void afterDraw(Renderer r) {
        // draw the line
        for(int i=1; i< points.size(); i++){
            r.drawLine(points.get(i-1), points.get(i));
        }
    }

    @Override
    public void onLeaving() {

    }
}
