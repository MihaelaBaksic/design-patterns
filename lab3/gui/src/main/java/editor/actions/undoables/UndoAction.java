package editor.actions.undoables;

import editor.UndoManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {

    private UndoManager manager;

    public UndoAction(String name, UndoManager manager){
        super(name);
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!manager.undoStackEmpty()){
            manager.undo();
        }
    }
}
