package editor.actions;

import editor.LocationRange;
import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectLeftAction extends AbstractAction {

    private TextEditorModel model;

    public SelectLeftAction(TextEditorModel model){
        this.model = model;

        model.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("shift LEFT"),"selectLeft");
        model.getActionMap().put("selectLeft", this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LocationRange range = model.getSelectionRange();

        if(!range.isSelected()){
            range.start = model.getCursorLocation();
        }

        range.end = model.getCursorLocation();

        model.setSelectionRange(range);
    }
}
