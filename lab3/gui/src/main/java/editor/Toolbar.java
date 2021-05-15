package editor;

import javax.swing.*;

public class Toolbar extends JToolBar {

    public Toolbar(){
        super();
        setFocusable(false);
        JButton undo = new JButton("Undo");
        undo.setFocusable(false);
        JButton redo = new JButton("Redo");
        redo.setFocusable(false);
        JButton cut = new JButton("Cut");
        cut.setFocusable(false);
        JButton copy = new JButton("Copy");
        copy.setFocusable(false);
        JButton paste = new JButton("Paste");
        paste.setFocusable(false);

        this.add(undo);
        this.add(redo);
        this.add(cut);
        this.add(copy);
        this.add(paste);

    }
}
