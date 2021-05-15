package editor.actions;

import editor.LocationRange;
import editor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectLeftAction extends AbstractAction {

    private TextEditorModel model;


    public SelectLeftAction(String name, TextEditorModel model){
        super(name);
        this.model = model;
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
