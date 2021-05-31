package model;

import render.Renderer;
import util.Point;
import util.Rectangle;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class CompositeShape implements GraphicalObject{

    private List<GraphicalObject> children;
    private boolean selected;
    private List<GraphicalObjectListener> listeners;

    public CompositeShape(List<GraphicalObject> children){
        super();
        this.children = new ArrayList<>();
        this.children.addAll(children);
        selected = false;
        listeners = new ArrayList<>();
    }

    public CompositeShape(){
        this(new ArrayList<>());
    }

    public void setChildren(List<GraphicalObject> children){
        this.children = new ArrayList<>();
        this.children.addAll(children);
    }

    public List<GraphicalObject> getChildren(){
        return children;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        notifySelectionListeners();
    }

    @Override
    public int getNumberOfHotPoints() {
        return 0;
    }

    @Override
    public Point getHotPoint(int index) {
        return null;
    }

    @Override
    public void setHotPoint(int index, Point point) {}

    @Override
    public boolean isHotPointSelected(int index) {
        return true;
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {}

    @Override
    public double getHotPointDistance(int index, Point mousePoint) { return selectionDistance(mousePoint);}

    @Override
    public void translate(Point delta) {
        for(GraphicalObject c : children){
            c.translate(delta);
        }
        notifyListeners();
    }

    @Override
    public Rectangle getBoundingBox() {
        List<Rectangle> childrenBBoxes = children.stream().map(c -> c.getBoundingBox()).collect(Collectors.toList());
        Rectangle r = childrenBBoxes.get(0);
        for(int i=1; i<childrenBBoxes.size(); i++){
            r = r.union(childrenBBoxes.get(i));
        }
        return r;
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        List<Double> distances = new ArrayList<>();
        for(GraphicalObject child : children){
            distances.add(child.selectionDistance(mousePoint));
        }
        return distances.stream().mapToDouble(d -> d).min().getAsDouble();
    }

    @Override
    public void render(Renderer r) {
        for(GraphicalObject child : children){
            child.render(r);
        }
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.remove(l);
    }

    @Override
    public String getShapeName() {
        return "Kompozit";
    }

    @Override
    public GraphicalObject duplicate() {
        return null;
    }

    @Override
    public String getShapeID() {
        return "@COMP";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] args = data.strip().split(" ");
        if(!args[0].equals("@COMP")) return;
        try{
            int numberOfChildren = Integer.parseInt(args[1]);
            List<GraphicalObject> children = new ArrayList<>();
            for(int i=0; i<numberOfChildren; i++)
                children.add(stack.pop());

            stack.push(new CompositeShape(children));
        }catch (IndexOutOfBoundsException | EmptyStackException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(List<String> rows) {
        for(GraphicalObject o : children)
            o.save(rows);

        rows.add(String.format("%s %d", getShapeID(), children.size()));
    }

    protected void notifyListeners(){
        listeners.stream().forEach(l -> l.graphicalObjectChanged(this));
    }

    protected void notifySelectionListeners(){
        listeners.stream().forEach(l -> l.graphicalObjectSelectionChanged(this));
    }
}
