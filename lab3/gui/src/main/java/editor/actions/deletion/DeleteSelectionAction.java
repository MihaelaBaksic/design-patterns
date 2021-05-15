package editor.actions.deletion;

import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteSelectionAction extends AbstractAction {

    private TextEditorModel model;

    public DeleteSelectionAction(String name, TextEditorModel model){
        super(name);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteRange(model.getSelectionRange());
        model.removeSelection();
    }
}
