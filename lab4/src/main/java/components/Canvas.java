package components;

import model.DocumentModel;
import model.DocumentModelListener;
import model.GraphicalObject;
import render.G2DRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import render.Renderer;
import state.State;
import util.Point;


public class Canvas extends JComponent implements DocumentModelListener {

    private DocumentModel model;
    private State currentState;

    public Canvas(DocumentModel model, State currentState){
        this.model = model;
        this.model.addDocumentModelListener(this);
        this.currentState = currentState;

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


    private void initMouseListeners(){
        MouseAdapter mouseAdapter = new MouseAdapter() {
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
                System.out.println("Dragged in canvas");
                currentState.mouseDragged(new Point(e.getX(), e.getY()));
            }
        };
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    @Override
    public void documentChange() {
        repaint();
    }
}
