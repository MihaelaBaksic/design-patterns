package editor.actions;

import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CursorToDocumentEndAction extends AbstractAction {

    private TextEditorModel model;

    public CursorToDocumentEndAction(String name, TextEditorModel model){
        super(name);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.cursorToEnd();
    }
}
