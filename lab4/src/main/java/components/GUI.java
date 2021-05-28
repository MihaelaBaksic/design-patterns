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
        JButton button = new JButton(new AbstractAction("Selektiraj") {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new SelectShapeState(model);
                canvas.setCurrentState(currentState);
            }
        });
        button.setFocusable(false);
        toolBar.add(button);

        for(GraphicalObject p : prototypes){
            button = new JButton(new AbstractAction(p.getShapeName()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentState.onLeaving();
                    currentState = new AddShapeState(p, model);
                    canvas.setCurrentState(currentState);
                }
            });
            button.setFocusable(false);
            toolBar.add(button);
        }

        cp.add(toolBar, BorderLayout.NORTH);
        cp.add(canvas, BorderLayout.CENTER);

        toolBar.setFocusable(false);

        initKeyBindings();
    }

    private void initKeyBindings(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                   currentState.onLeaving();
                   currentState = new IdleState();
                   canvas.setCurrentState(currentState);
               }
            }
        });
    }



}
