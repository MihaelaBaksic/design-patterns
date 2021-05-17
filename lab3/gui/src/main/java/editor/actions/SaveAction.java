package editor.actions;

import editor.UndoManager;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveAction extends AbstractAction {

    private TextEditorModel model;
    private UndoManager manager;

    public SaveAction(String name, TextEditorModel model, UndoManager manager){
        super(name);
        this.model = model;
        this.manager = manager;
        this.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control S"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // sejvaj
        System.out.println("Sejvam");
        manager.clear();
    }
}
