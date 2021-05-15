package plugin;

import editor.UndoManager;
import editor.components.TextEditor;
import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

public interface Plugin {

    String getName();
    String getDescription();
    void execute(TextEditorModel model, UndoManager manager, ClipboardStack clipboard);
}
