package components;

import model.DocumentModel;
import model.GraphicalObject;
import render.SVGRenderer;
import state.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

        JButton buttonSVG = new JButton(new AbstractAction("SVG Export") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

                int returnVal = chooser.showOpenDialog(null);

                if(returnVal==JFileChooser.APPROVE_OPTION){
                    File f = chooser.getSelectedFile();
                    SVGRenderer svgRenderer = new SVGRenderer(f.getAbsolutePath());

                    for(GraphicalObject o : model.list()){
                        o.render(svgRenderer);
                    }
                    try {
                        svgRenderer.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        return;
                    }
                }
                else
                    return;
            }
        });
        buttonSVG.setFocusable(false);
        toolBar.add(buttonSVG);

        JButton saveButton = new JButton(new AbstractAction("Pohrani") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

                int returnVal = chooser.showOpenDialog(null);

                if(returnVal==JFileChooser.APPROVE_OPTION){
                    File f = chooser.getSelectedFile();

                    try {
                        BufferedWriter w = new BufferedWriter(new FileWriter(f));
                        List<String> rows = new ArrayList<>();
                        for( GraphicalObject o : model.list()){
                            o.save(rows);
                        }

                        for(String s : rows){
                            w.write(s);
                            w.newLine();
                        }
                        w.close();
                    }
                    catch (Exception exception){
                        return;
                    }
                }
                else
                    return;
            }
        });
        saveButton.setFocusable(false);
        toolBar.add(saveButton);


        JButton buttonSelect = new JButton(new AbstractAction("Selektiraj") {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new SelectShapeState(model);
                canvas.setCurrentState(currentState);
            }
        });
        buttonSelect.setFocusable(false);
        toolBar.add(buttonSelect);

        JButton button = new JButton(new AbstractAction("Brisalo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new EraseState(model);
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
               else{
                   canvas.keyPressed(e.getKeyCode());
               }
            }
        });
    }



}
