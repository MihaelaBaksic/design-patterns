package components;

import model.DocumentModel;
import model.GraphicalObject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {

    private List<GraphicalObject> prototypes;
    private DocumentModel model;
    private Canvas canvas;

    public GUI(List<GraphicalObject> objectList){
        prototypes = objectList;
        model = new DocumentModel();
        canvas = new Canvas(model);

        JPanel cp = new JPanel();
        cp.setLayout(new BorderLayout());

        this.setContentPane(cp);

        JToolBar toolBar = new JToolBar();
        for(GraphicalObject p : prototypes){
            toolBar.add(new JButton(p.getShapeName()));
        }

        cp.add(toolBar, BorderLayout.NORTH);
        cp.add(canvas, BorderLayout.CENTER);
    }


}
