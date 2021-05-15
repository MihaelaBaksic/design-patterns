package plugin;

import editor.UndoManager;
import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

import javax.swing.*;

public class Statistics implements Plugin{

    private String name;
    private String description;

    public Statistics(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(TextEditorModel model, UndoManager manager, ClipboardStack clipboard) {
        int rows = model.linesNumber();
        int words = model.getLines().stream()
                .mapToInt(l ->{
                    return l.strip().split("\s").length;
                }).sum();
        int characters = model.getLines().stream()
                .mapToInt(l -> {
                    return l.replaceAll("\s", "").length();
                }).sum();

        JOptionPane.showMessageDialog(null, "Rows: " + rows + "  Words: " + words + "  Characters: " + characters);
    }
}
