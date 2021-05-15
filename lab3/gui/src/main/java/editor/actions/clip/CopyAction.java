package editor.actions.clip;

import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CopyAction extends AbstractAction {

    private TextEditorModel model;
    private ClipboardStack clipboard;

    public CopyAction(String name, TextEditorModel model, ClipboardStack clipboard){
        super(name);
        this.model = model;
        this.clipboard = clipboard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(model.getSelectionRange().isSelected()){
            String text = model.getSelectedText();
            clipboard.push(text);
        }
    }
}