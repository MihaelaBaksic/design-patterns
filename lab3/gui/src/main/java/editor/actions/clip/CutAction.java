package editor.actions.clip;

import editor.UndoManager;
import editor.actions.undoables.UndoableAction;
import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CutAction extends AbstractAction {

    private TextEditorModel model;
    private ClipboardStack clipboard;
    private UndoManager manager;

    public CutAction(String name, TextEditorModel model, ClipboardStack clipboard, UndoManager manager){
        super(name);
        this.model = model;
        this.clipboard = clipboard;
        this.manager = manager;
        this.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control X"));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(model.getSelectionRange().isSelected()){
            String text = model.getSelectedText();
            clipboard.push(text);

            UndoableAction a = new UndoableAction(model);
            a.setPrior(model.getLines(), model.getCursor());

            model.deleteRange(model.getSelectionRange());
            model.removeSelection();

            a.setPosterior(model.getLines(), model.getCursor());
            manager.push(a);
        }
    }
}
