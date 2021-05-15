package editor.actions.deletion;

import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteAfterAction extends AbstractAction {


    private TextEditorModel model;

    public DeleteAfterAction(String name, TextEditorModel model){
        super(name);
        this.model = model;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteAfter();
        model.removeSelection();
    }
}
