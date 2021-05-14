package editor;

public interface CursorObserver {
    void updateCursorLocation(TextEditorModel.Location loc);
}
