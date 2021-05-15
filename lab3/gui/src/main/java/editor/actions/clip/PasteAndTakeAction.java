package editor.actions.clip;

import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PasteAndTakeAction extends AbstractAction {

    private TextEditorModel model;
    private ClipboardStack clipboard;

    public PasteAndTakeAction(String name, TextEditorModel model, ClipboardStack clipboard){
        super(name);
        this.model = model;
        this.clipboard = clipboard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = clipboard.pop();
        model.insert(text);
    }
}
