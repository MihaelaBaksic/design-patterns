package editor.actions.undoables;

import editor.UndoManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {

    private UndoManager manager;

    public RedoAction(String name, UndoManager manager){
        super(name);
        this.manager = manager;
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!manager.redoStackEmpty()){
            manager.redo();
        }
    }
}