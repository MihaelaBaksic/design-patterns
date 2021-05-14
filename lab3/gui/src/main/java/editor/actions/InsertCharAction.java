package editor.actions;

import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InsertCharAction extends AbstractAction {

    private TextEditorModel model;

    public InsertCharAction(TextEditorModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.insert('c');
    }
}
