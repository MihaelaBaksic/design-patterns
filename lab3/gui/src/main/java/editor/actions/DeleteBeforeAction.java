package editor.actions;

import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteBeforeAction extends AbstractAction {

    private TextEditorModel model;

    public DeleteBeforeAction(TextEditorModel model){
        this.model = model;

        model.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("BACK_SPACE"),"deleteBefore");
        model.getActionMap().put("deleteBefore", this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.deleteBefore();
    }
}
