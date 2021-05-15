package editor.actions.deletion;

import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClearDocumentAction extends AbstractAction {

    private TextEditorModel model;

    public ClearDocumentAction(String name, TextEditorModel model){
        super(name);
        this.model = model;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        model.clear();
        model.removeSelection();
    }
}
