package editor.actions.selection;

import editor.models.Location;
import editor.models.LocationRange;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectUpAction extends AbstractAction {

    private TextEditorModel model;

    public SelectUpAction(String name, TextEditorModel model){
        super(name);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LocationRange range = model.getSelectionRange();

        System.out.println(model.getSelectionRange());
        if(!range.isSelected())
            range.start = new Location(model.getCursorLocation());

        model.moveCursorUp();
        range.end = new Location(model.getCursorLocation());;

        model.setSelectionRange(range);
        System.out.println(model.getSelectionRange());
    }
}
