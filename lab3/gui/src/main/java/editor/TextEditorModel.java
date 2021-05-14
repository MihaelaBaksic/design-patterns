package editor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TextEditorModel extends JComponent implements LinesIterable{

    private static final Font font = new Font("Monospaced", Font.PLAIN, 12);

    private List<String> lines;
    private Location cursorLocation;
    private LocationRange selectionRange;
    private List<CursorObserver> cursorObservers;

    public TextEditorModel(String text){
        lines = Arrays.asList(text.split("\n").clone());
        cursorObservers = new ArrayList<>();
        cursorLocation = new Location(0,0);
    }


    @Override
    protected void paintComponent(Graphics g){

        g.setFont(font);
        int h = g.getFontMetrics().getHeight();
        System.out.println(h);
        int w = g.getFontMetrics().charWidth('i');
        Iterator<String> linesIt = lines.iterator();
        while(linesIt.hasNext()){
            g.drawString(linesIt.next(), 0, h);
            h+=h;
        }

        //paint cursor
        int asc = g.getFontMetrics().getAscent();
        h = g.getFontMetrics().getHeight();
        int diff = Math.abs(h - asc);
        g.drawLine(cursorLocation.x*w, cursorLocation.y*h + diff, cursorLocation.x*w, (cursorLocation.y+1)*h + diff);

    }



    @Override
    public Iterator<String> allLines() {
        return lines.iterator();
    }

    @Override
    public Iterator<String> linesRange(int index1, int index2) {
        return lines.subList(index1, index2).iterator();
    }


    public void addCursorObserver(CursorObserver o){
        cursorObservers.add(o);
    }

    public void removeCursorObserver(CursorObserver o){
        cursorObservers.remove(o);
    }

    private void notifyObservers(){
        for(var observer : cursorObservers){
            observer.updateCursorLocation(this.cursorLocation);
        }
    }

    public void moveCursorLeft(){
        if(cursorLocation.x > 0) {
            cursorLocation.x--;
            notifyObservers();
        }
    }

    public void moveCursorRight(){
        int n = lines.get(cursorLocation.y).length();
        if( cursorLocation.x < n) {
            cursorLocation.x++;
            notifyObservers();
        }
    }

    public void moveCursorUp(){
        if(cursorLocation.y > 0) {
            cursorLocation.y--;

            int n = lines.get(cursorLocation.y).length();
            if (n < cursorLocation.x)
                cursorLocation.x = n;

            notifyObservers();
        }
    }

    public void moveCursorDown(){
        int len = lines.size();

        if( cursorLocation.y+1 < len){
            cursorLocation.y++;

            int n = lines.get(cursorLocation.y).length();
            if(n < cursorLocation.x)
                cursorLocation.x = n;

            notifyObservers();
        }
    }



    public static class Location{

        public Location(int x, int y){
            this.x = x;
            this.y = y;
        }
        public int x;
        public int y;
    }

    public static class LocationRange{
        public Location start;
        public Location end;
    }
}
