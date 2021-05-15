package editor.actions;

import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteAfterAction extends AbstractAction {


    private TextEditorModel model;

    public DeleteAfterAction(String name, TextEditorModel model){
        super(name);
        this.model = model;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteAfter();
    }
}
