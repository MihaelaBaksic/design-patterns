package editor.actions;

import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InsertCharAction extends AbstractAction {

    private TextEditorModel model;
    private Character c;

    public InsertCharAction(TextEditorModel model, Character c){
        this.model = model;
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.insert(c);
    }
}
