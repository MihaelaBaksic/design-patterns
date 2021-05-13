package editor;

import java.util.Iterator;

public interface LinesIterable {
    Iterator<String> allLines();
    Iterator<String> linesRange(int index1, int index2);
}
