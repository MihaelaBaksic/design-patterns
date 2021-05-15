package editor.actions.undoables;

public interface EditAction {
    void executeDo();
    void executeUndo();
}
