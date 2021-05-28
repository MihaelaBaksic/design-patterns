package state;

import model.DocumentModel;
import model.GraphicalObject;
import render.Renderer;
import util.Point;
import util.Rectangle;

import java.util.List;

public class SelectShapeState implements State{

    private DocumentModel model;

    public SelectShapeState(DocumentModel model){
        this.model = model;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        System.out.println("SELEKTIRAJ");
        if(!ctrlDown){
            model.clearSelection();
        }

        GraphicalObject selected = model.findSelectedGraphicalObject(mousePoint);
        if(selected != null)
            selected.setSelected(true);
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

    }

    @Override
    public void mouseDragged(Point mousePoint) {
        // if there is one selected, get that one
        System.out.println("Dragged");
        List<GraphicalObject> selected = model.getSelectedObjects();
        if(selected.size()==1){
            GraphicalObject go = selected.get(0);
            int index =  model.findSelectedHotPoint(go, mousePoint);
            if(index < 0) return;

            Point selectedHP = go.getHotPoint(index);
            Point newHP = selectedHP.translate(mousePoint.difference(selectedHP));
            go.setHotPoint(index, newHP);
        }
    }

    @Override
    public void keyPressed(int keyCode) {

    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        if(go.isSelected()){
            Rectangle boundingBox = go.getBoundingBox();
            drawRectangle(r, boundingBox);

            if(model.getSelectedObjects().size() == 1){ // This is the only selected object
                //Draw HotPoints
                int n = go.getNumberOfHotPoints();
                for(int i=0; i<n; i++){
                    Rectangle hpMark = go.getHotPoint(i).getHotPointRectangle();
                    drawRectangle(r, hpMark);
                }
            }
        }

    }

    @Override
    public void afterDraw(Renderer r) {

    }

    @Override
    public void onLeaving() {
        System.out.println("Leaving select shape state");
        List<GraphicalObject> selected = List.copyOf(model.getSelectedObjects());
        for(GraphicalObject o : selected){
            o.setSelected(false);
        }
    }

    private void drawRectangle(Renderer r, Rectangle rectangle){
        Point[] bbPoints = rectangle.getPointsOfRectangle();

        r.drawLine(bbPoints[0], bbPoints[1]);
        r.drawLine(bbPoints[1], bbPoints[2]);
        r.drawLine(bbPoints[2], bbPoints[3]);
        r.drawLine(bbPoints[3], bbPoints[0]);
    }

}
