package editor.actions.clip;

import editor.UndoManager;
import editor.actions.undoables.UndoableAction;
import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PasteAndTakeAction extends AbstractAction {

    private TextEditorModel model;
    private ClipboardStack clipboard;
    private UndoManager manager;

    public PasteAndTakeAction(String name, TextEditorModel model, ClipboardStack clipboard, UndoManager manager){
        super(name);
        this.model = model;
        this.clipboard = clipboard;
        this.manager = manager;
        this.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control shift V"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = clipboard.pop();

        UndoableAction a = new UndoableAction(model);

        a.setCursorPrior(model.getCursor());
        a.setTextPrior(model.getLines());

        model.insert(text);

        a.setCursorPosterior(model.getCursor());
        a.setTextPosterior(model.getLines());

        manager.push(a);
    }
}
