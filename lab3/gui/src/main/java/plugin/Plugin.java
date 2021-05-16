package plugin;

import editor.UndoManager;

import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

public interface Plugin {

    String getName();
    String getDescription();
    void execute(TextEditorModel model, UndoManager manager, ClipboardStack clipboard);
}
