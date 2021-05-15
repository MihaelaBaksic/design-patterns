package editor.actions.deletion;

import editor.UndoManager;
import editor.actions.undoables.DeleteBeforeUndoable;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteBeforeAction extends AbstractAction {

    private TextEditorModel model;
    private UndoManager manager;

    public DeleteBeforeAction(String name, TextEditorModel model, UndoManager manager){
        super(name);
        this.model = model;
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteBefore();
        model.removeSelection();
    }
}
