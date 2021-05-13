package editor;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TextEditorModel extends JComponent implements LinesIterable {

    private List<String> lines;
    private Location cursorLocation;
    private LocationRange selectionRange;

    public TextEditorModel(String text){
        lines = Arrays.asList(text.split("\n").clone());
    }


    @Override
    protected void paintComponent(Graphics g){

        int h = g.getFontMetrics().getHeight();

        for(String line : lines){
            g.drawString(line, 0, h);
            h+=h;
        }

    }



    @Override
    public Iterator<String> allLines() {
        return lines.iterator();
    }

    @Override
    public Iterator<String> linesRange(int index1, int index2) {
        return lines.subList(index1, index2).iterator();
    }


    private static class Location{
        public int x;
        public int y;
    }

    private static class LocationRange{
        public Location start;
        public Location end;
    }
}
