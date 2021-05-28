package components;

import model.DocumentModel;
import model.GraphicalObject;
import state.AddShapeState;
import state.IdleState;
import state.SelectShapeState;
import state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GUI extends JFrame {

    private List<GraphicalObject> prototypes;
    private DocumentModel model;
    private Canvas canvas;
    private State currentState;

    public GUI(List<GraphicalObject> objectList){
        prototypes = objectList;
        model = new DocumentModel();
        currentState = new IdleState();
        canvas = new Canvas(model, currentState);

        JPanel cp = new JPanel();
        cp.setLayout(new BorderLayout());

        this.setContentPane(cp);


        JToolBar toolBar = new JToolBar();
        toolBar.add(new AbstractAction("Selektiraj") {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentState = new SelectShapeState(model);
                canvas.setCurrentState(currentState);
            }
        });

        for(GraphicalObject p : prototypes){
            toolBar.add(new JButton(new AbstractAction(p.getShapeName()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentState = new AddShapeState(p, model);
                    canvas.setCurrentState(currentState);
                }
            }));
        }

        cp.add(toolBar, BorderLayout.NORTH);
        cp.add(canvas, BorderLayout.CENTER);

        initKeyListeners();
    }

    private void initKeyListeners(){

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.out.println("ESCAPE");
                    e.consume();
                    currentState = new IdleState();
                }
            }
        });

    }


}
