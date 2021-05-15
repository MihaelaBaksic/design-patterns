package plugin;

import editor.UndoManager;
import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

public class FirstCapitalized implements Plugin{

    private String name;
    private String description;

    @Override
    public String getName() {
        return name;
    }

    public FirstCapitalized(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(TextEditorModel model, UndoManager manager, ClipboardStack clipboard) {

    }
}
