package editor.actions.undoables;

import editor.models.Location;
import editor.models.TextEditorModel;

import java.util.ArrayList;
import java.util.List;

public class UndoableAction implements EditAction {


    private List<String> textPrior;
    private Location cursorPrior;

    private List<String> textPosterior;
    private Location cursorPosterior;

    private TextEditorModel model;

    public UndoableAction(List<String> textPrior, Location cursorPrior, List<String> textPosterior, Location cursorPosterior, TextEditorModel model){
        this.model = model;
        this.textPrior = textPrior;
        this.cursorPrior = cursorPrior;
        this.textPosterior = textPosterior;
        this.cursorPosterior = cursorPosterior;
    }

    public void setTextPrior(List<String> textPrior) {
        this.textPrior = new ArrayList<>(textPrior);
    }

    public void setCursorPrior(Location cursorPrior) {
        this.cursorPrior = new Location(cursorPrior);
    }

    public void setTextPosterior(List<String> textPosterior) {
        this.textPosterior = new ArrayList<>(textPosterior);
    }

    public void setCursorPosterior(Location cursorPosterior) {
        this.cursorPosterior = new Location(cursorPosterior);
    }

    public UndoableAction(TextEditorModel model){
        this.model = model;
        textPrior = new ArrayList<>();
        textPosterior = new ArrayList<>();
        cursorPrior = new Location(0,0);
        cursorPosterior = new Location(0,0);
    }

    @Override
    public void executeDo() {
        model.setLines(textPosterior);
        model.setCursorLocation(cursorPosterior);
    }

    @Override
    public void executeUndo() {
        model.setLines(textPrior);
        model.setCursorLocation(cursorPrior);
    }
}
