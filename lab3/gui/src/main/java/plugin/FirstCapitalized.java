package plugin;

import editor.UndoManager;
import editor.actions.undoables.UndoableAction;
import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class FirstCapitalized implements Plugin{

    private String name;
    private String description;
    private UndoManager manager;

    @Override
    public String getName() {
        return name;
    }

    public FirstCapitalized(){
        this.name = "Capitalize first";
        this.description = "Capitalizes first letter of each word";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(TextEditorModel model, UndoManager manager, ClipboardStack clipboard) {
        UndoableAction a = new UndoableAction(model);
        a.setPrior(model.getLines(), model.getCursor());

        String regex = "\\b(.)(.*?)\\b";

        List<String> newLines = new ArrayList<>();

        Iterator<String> it = model.allLines();
        while(it.hasNext()) {
            String result = Pattern.compile(regex).matcher(it.next()).replaceAll(
                    m -> m.group(1).toUpperCase() + m.group(2)
            );
            newLines.add(result);
        }

        model.setLines(newLines);

        a.setPosterior(model.getLines(), model.getCursor());
        manager.push(a);
    }
}
