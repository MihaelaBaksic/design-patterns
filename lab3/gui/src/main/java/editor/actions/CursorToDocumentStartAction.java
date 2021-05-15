package editor.actions;

import editor.TextEditorModel;

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
    }
}
