package state;

import model.CompositeShape;
import model.DocumentModel;
import model.GraphicalObject;
import render.Renderer;
import util.Point;
import util.Rectangle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
        switch (keyCode){
            case KeyEvent.VK_DOWN: moveSelectionDown(); break;
            case KeyEvent.VK_UP: moveSelectionUp(); break;
            case KeyEvent.VK_RIGHT: moveSelectionRight(); break;
            case KeyEvent.VK_LEFT: moveSelectionLeft(); break;
            case KeyEvent.VK_PLUS: model.moveSelectedForward(); break;
            case KeyEvent.VK_MINUS: model.moveSelectedBackward(); break;
            case KeyEvent.VK_G: createComposite(); break;
            case KeyEvent.VK_U: removeComposite(); break;
        }
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


    private void moveSelectionDown(){
        for( GraphicalObject o : model.getSelectedObjects()){
            o.translate(new Point(0, 1));
        }
        model.notifyListeners();
    }

    private void moveSelectionUp(){
        for( GraphicalObject o : model.getSelectedObjects()){
            o.translate(new Point(0, -1));
        }
        model.notifyListeners();
    }

    private void moveSelectionRight(){
        for( GraphicalObject o : model.getSelectedObjects()){
            o.translate(new Point(1, 0));
        }
        model.notifyListeners();
    }

    private void moveSelectionLeft(){
        for( GraphicalObject o : model.getSelectedObjects()){
            o.translate(new Point(-1, 0));
        }
        model.notifyListeners();
    }

    private void createComposite() {
        List<GraphicalObject> children = model.getSelectedObjects();
        if(children == null || children.size()==0) return;

        GraphicalObject composite = new CompositeShape(children);

        for( GraphicalObject c : new ArrayList<>(children))
            model.removeGraphicalObject(c);

        composite.setSelected(true);
        model.addGraphicalObject(composite);
    }

    private void removeComposite() {
        // Get selected objects
        List<GraphicalObject> selected = model.getSelectedObjects();
        if(selected.size() != 1) return;

        GraphicalObject object = selected.get(0);
        if( !(object instanceof CompositeShape) ) return;

        // object is composite
        List<GraphicalObject> children = ((CompositeShape)object).getChildren();
        model.removeGraphicalObject(object);
        for( GraphicalObject child : children){
            model.addGraphicalObject(child);
        }
    }


}
