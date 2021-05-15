package editor.observers;

import editor.models.Location;

public interface CursorObserver {
    void updateCursorLocation(Location loc);
}
