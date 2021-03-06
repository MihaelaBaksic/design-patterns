package editor.actions;

import editor.UndoManager;
import editor.actions.undoables.UndoableAction;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InsertCharAction extends AbstractAction {

    private TextEditorModel model;
    private Character c;
    private UndoManager manager;

    public InsertCharAction(String name, TextEditorModel model, Character c, UndoManager manager){
        super(name);
        this.model = model;
        this.c = c;
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        UndoableAction a = new UndoableAction(model);
        a.setPrior(model.getLines(), model.getCursor());

        if (model.insert(c)){
            a.setPosterior(model.getLines(), model.getCursor());
            manager.push(a);
        }
    }
}
