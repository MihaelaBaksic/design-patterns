package editor.actions;

import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteRangeAction extends AbstractAction {

    private TextEditorModel model;

    public DeleteRangeAction(TextEditorModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteRange(model.getSelectionRange());
    }
}
