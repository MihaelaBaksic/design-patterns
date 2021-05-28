package components;

import model.DocumentModel;
import model.GraphicalObject;
import model.LineSegment;
import model.Oval;
import render.G2DRenderer;

import javax.swing.*;
import java.awt.*;
import render.Renderer;
import util.Point;

public class Canvas extends JComponent {

    private DocumentModel model;

    public Canvas(DocumentModel model){
        this.model = model;
        this.model.addGraphicalObject(new LineSegment(new Point[] {new Point(100, 200), new Point(50, 20)}));
        this.model.addGraphicalObject(new Oval(new Point[]{new Point(200, 100), new Point(150, 150)}));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Renderer r = new G2DRenderer(g2d);

        for(GraphicalObject o : model.list()){
            o.render(r);
        }
    }
}
