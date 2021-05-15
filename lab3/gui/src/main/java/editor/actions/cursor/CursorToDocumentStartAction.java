package editor.actions.cursor;

import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CursorToDocumentStartAction extends AbstractAction {

    private TextEditorModel model;

    public CursorToDocumentStartAction(String name, TextEditorModel model){
        super(name);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.cursorToStart();
        model.removeSelection();
    }
}
