package editor.actions;

import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteBeforeAction extends AbstractAction {

    private TextEditorModel model;

    public DeleteBeforeAction(TextEditorModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteBefore();
    }
}
