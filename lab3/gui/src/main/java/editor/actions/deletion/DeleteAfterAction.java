package editor.actions.deletion;

import editor.UndoManager;
import editor.actions.undoables.UndoableAction;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteAfterAction extends AbstractAction {


    private TextEditorModel model;
    private UndoManager manager;

    public DeleteAfterAction(String name, TextEditorModel model, UndoManager manager){
        super(name);
        this.model = model;
        this.manager = manager;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        UndoableAction a = new UndoableAction(model);
        a.setPrior(model.getLines(), model.getCursor());

        model.deleteAfter();
        model.removeSelection();

        a.setPosterior(model.getLines(), model.getCursor());
        manager.push(a);
    }
}
