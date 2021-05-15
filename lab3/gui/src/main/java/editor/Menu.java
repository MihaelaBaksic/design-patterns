package editor;

import javax.swing.*;

public class Menu extends JMenuBar {

    public Menu(){
        super();

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenuItem itemOpen = new JMenuItem("Open");
        JMenuItem itemSave = new JMenuItem("Save");
        JMenuItem itemExit = new JMenuItem("Exit");

        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemExit);


        JMenu menuEdit = new JMenu("Edit");
        JMenuItem itemUndo = new JMenuItem("Undo");
        JMenuItem itemRedo = new JMenuItem("Redo");
        JMenuItem itemCut = new JMenuItem("Cut");
        JMenuItem itemCopy = new JMenuItem("Copy");
        JMenuItem itemPaste = new JMenuItem("Paste");
        JMenuItem itemPasteAndTake = new JMenuItem("Paste and Take");
        JMenuItem itemDeleteSelection = new JMenuItem("Delete selection");
        JMenuItem itemClearDocument = new JMenuItem("Clear document");

        menuEdit.add(itemUndo);
        menuEdit.add(itemRedo);
        menuEdit.add(itemCut);
        menuEdit.add(itemCopy);
        menuEdit.add(itemPaste);
        menuEdit.add(itemPasteAndTake);
        menuEdit.add(itemDeleteSelection);
        menuEdit.add(itemClearDocument);


        JMenu menuMove = new JMenu("Move");
        JMenuItem itemCursorToStart = new JMenuItem("Cursor to document start");
        JMenuItem itemCursorToEnd = new JMenuItem("Cursor to document end");

        menuMove.add(itemCursorToStart);
        menuMove.add(itemCursorToEnd);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuMove);

        this.add(menuBar);

    }
}
