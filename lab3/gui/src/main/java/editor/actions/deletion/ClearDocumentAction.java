package editor.actions.deletion;

import editor.UndoManager;
import editor.actions.undoables.UndoableAction;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClearDocumentAction extends AbstractAction {

    private TextEditorModel model;
    private UndoManager manager;

    public ClearDocumentAction(String name, TextEditorModel model, UndoManager manager){
        super(name);
        this.manager = manager;
        this.model = model;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        UndoableAction a = new UndoableAction(model);
        a.setPrior(model.getLines(), model.getCursor());

        model.clear();
        model.removeSelection();

        a.setPosterior(model.getLines(), model.getCursor());
        manager.push(a);
    }
}
