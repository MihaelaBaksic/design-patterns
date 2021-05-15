package editor;

import javax.swing.*;

public class Toolbar extends JToolBar {

    public Toolbar(){
        super();

        JButton undo = new JButton("Undo");
        JButton redo = new JButton("Redo");
        JButton cut = new JButton("Cut");
        JButton copy = new JButton("Copy");
        JButton paste = new JButton("Paste");

        this.add(undo);
        this.add(redo);
        this.add(cut);
        this.add(copy);
        this.add(paste);

    }
}
