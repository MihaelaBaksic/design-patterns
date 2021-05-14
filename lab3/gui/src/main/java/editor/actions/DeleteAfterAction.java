package editor.actions;

import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteAfterAction extends AbstractAction {


    private TextEditorModel model;

    public DeleteAfterAction(TextEditorModel model){
        this.model = model;

        //model.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteAfter();
    }
}
