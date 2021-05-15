package editor.actions;

import editor.Location;
import editor.LocationRange;
import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectRightAction extends AbstractAction {

    private TextEditorModel model;

    public SelectRightAction(TextEditorModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LocationRange range = model.getSelectionRange();

        if(!range.isSelected()){
            model.moveCursorLeft();
            range.start = model.getCursorLocation();
            model.moveCursorRight();
        }

        range.end = model.getCursorLocation();

        model.setSelectionRange(range);
    }
}
