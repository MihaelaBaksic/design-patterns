package components;

import model.DocumentModel;
import model.DocumentModelListener;
import model.GraphicalObject;
import render.G2DRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import render.Renderer;
import state.State;
import util.Point;
import util.Rectangle;


public class Canvas extends JComponent implements DocumentModelListener {

    private DocumentModel model;
    private State currentState;

    public Canvas(DocumentModel model, State currentState){
        this.model = model;
        this.model.addDocumentModelListener(this);
        this.currentState = currentState;
        //this.model.addGraphicalObject(new LineSegment(new Point[] {new Point(100, 200), new Point(50, 20)}));
        //this.model.addGraphicalObject(new Oval(new Point[]{new Point(200, 100), new Point(150, 150)}));
        initKeyListeners();
        initMouseListeners();
    }

    public void setCurrentState(State state){
        currentState = state;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Renderer r = new G2DRenderer(g2d);

        for(GraphicalObject o : model.list()){
            o.render(r);
            currentState.afterDraw(r, o);
        }
        currentState.afterDraw(r);

    }

    private void initKeyListeners(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
        });
    }

    private void initMouseListeners(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentState.mouseDown(new Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentState.mouseUp(new Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                currentState.mouseDragged(new Point(e.getX(), e.getY()));
            }
        });
    }

    @Override
    public void documentChange() {
        repaint();
    }
}
