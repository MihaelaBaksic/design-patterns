package model;

import render.Renderer;
import util.GeometryUtil;
import util.Point;
import util.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractGraphicalObject implements GraphicalObject{

    protected List<Point> hotPoints;
    protected List<Boolean> hotPointSelected;

    protected boolean selected;

    private List<GraphicalObjectListener> listeners;

    public AbstractGraphicalObject(Point... points){
        hotPoints = List.of(points);
        hotPointSelected = new ArrayList<>();
        for(int i=0; i<points.length; i++){
            hotPointSelected.add(false);
        }
        selected = false;
        listeners = new ArrayList<>();
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
        return hotPoints.size();
    }

    @Override
    public Point getHotPoint(int index) {
        return hotPoints.get(index);
    }

    @Override
    public void setHotPoint(int index, Point point) {
        hotPoints.set(index, point);
        notifyListeners();
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return hotPointSelected.get(index);
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        hotPointSelected.set(index, selected);
        notifySelectionListeners();
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return GeometryUtil.distanceFromPoint(mousePoint, hotPoints.get(index));
    }

    @Override
    public void translate(Point delta) {
        hotPoints = hotPoints.stream()
                .map(p -> p.translate(delta)).collect(Collectors.toList());
        notifyListeners();
    }

    @Override
    public Rectangle getBoundingBox() {
        return null;
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return 0;
    }

    @Override
    public void render(Renderer r){
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
        return null;
    }

    @Override
    public GraphicalObject duplicate() {
        return null;
    }

    protected void notifyListeners(){
        listeners.stream().forEach(l -> l.graphicalObjectChanged(this));
    }

    protected void notifySelectionListeners(){
        listeners.stream().forEach(l -> l.graphicalObjectSelectionChanged(this));
    }
}
