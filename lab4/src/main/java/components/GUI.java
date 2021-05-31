package components;

import model.CompositeShape;
import model.DocumentModel;
import model.DocumentModelListener;
import model.GraphicalObject;
import render.G2DRenderer;
import render.Renderer;
import render.SVGRenderer;
import state.*;
import util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
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
        canvas = new Canvas(model);

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
                    }
                }
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
                    catch (Exception exception) {}
                }
            }
        });
        saveButton.setFocusable(false);
        toolBar.add(saveButton);


        JButton loadButton = new JButton(new AbstractAction("Učitaj") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

                int returnVal = chooser.showOpenDialog(null);

                if(returnVal==JFileChooser.APPROVE_OPTION){
                    File f = chooser.getSelectedFile();

                    try {
                        BufferedReader w = new BufferedReader(new FileReader(f));
                        Stack<GraphicalObject> objects = new Stack<>();

                        Map<String, GraphicalObject> loaders = new HashMap<>();
                        for(GraphicalObject o : prototypes){
                            loaders.put(o.getShapeID(), o);
                        }
                        loaders.put("@COMP", new CompositeShape());

                        w.lines().forEach( l -> {
                                try{
                                    String[] args = l.strip().split(" ");
                                    loaders.get(args[0]).load(objects,l);
                                }
                                catch (Exception exception){
                                    exception.printStackTrace();
                                }
                        });

                        while(!objects.empty())
                            model.addGraphicalObject(objects.pop());

                        w.close();
                    }
                    catch (Exception exception) {}
                }
            }
        });
        loadButton.setFocusable(false);
        toolBar.add(loadButton);


        JButton buttonSelect = new JButton(new AbstractAction("Selektiraj") {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new SelectShapeState(model);
            }
        });
        buttonSelect.setFocusable(false);
        toolBar.add(buttonSelect);

        JButton button = new JButton(new AbstractAction("Brisalo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentState.onLeaving();
                currentState = new EraseState(model);
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
               }
               else{
                   canvas.keyPressed(e.getKeyCode());
               }
            }
        });
    }


    class Canvas extends JComponent implements DocumentModelListener {

        private DocumentModel model;

        public Canvas(DocumentModel model){
            this.model = model;
            this.model.addDocumentModelListener(this);

            initMouseListeners();
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
                    currentState.mouseDown(new util.Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    currentState.mouseUp(new util.Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
                }

                @Override
                public void mouseDragged(MouseEvent e) {
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

        public void keyPressed(int keyCode){
            currentState.keyPressed(keyCode);
        }

    }


}
