package editor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextEditorModel extends JComponent implements LinesIterable{

    private static final Font font = new Font("Monospaced", Font.PLAIN, 12);

    private List<String> lines;
    private Location cursorLocation;
    private LocationRange selectionRange;
    private List<CursorObserver> cursorObservers;
    private List<TextObserver> textObservers;

    public TextEditorModel(String text){
        lines = new ArrayList<>(List.of(text.split("\n").clone()));
        cursorObservers = new ArrayList<>();
        textObservers = new ArrayList<>();
        cursorLocation = new Location(0,0);
    }


    @Override
    protected void paintComponent(Graphics g){

        // paint text
        g.setFont(font);
        int h = g.getFontMetrics().getHeight();
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

    public void addTextObserver(TextObserver o){
        textObservers.add(o);
    }

    public void removeTextObserver(TextObserver o){
        textObservers.remove(o);
    }

    private void notifyCursorObservers(){
        for(var observer : cursorObservers){
            observer.updateCursorLocation(this.cursorLocation);
        }
    }

    private void notifyTextObservers(){
        for(var observer : textObservers){
            observer.updateText();
        }
    }

    public void moveCursorLeft(){
        if(cursorLocation.x > 0) {
            cursorLocation.x--;
            notifyCursorObservers();
        }
    }

    public void moveCursorRight(){
        int n = lines.get(cursorLocation.y).length();
        if( cursorLocation.x < n) {
            cursorLocation.x++;
            notifyCursorObservers();
        }
    }

    public void moveCursorUp(){
        if(cursorLocation.y > 0) {
            cursorLocation.y--;

            int n = lines.get(cursorLocation.y).length();
            if (n < cursorLocation.x)
                cursorLocation.x = n;

            notifyCursorObservers();
        }
    }

    public void moveCursorDown(){
        int len = lines.size();

        if( cursorLocation.y+1 < len){
            cursorLocation.y++;

            int n = lines.get(cursorLocation.y).length();
            if(n < cursorLocation.x)
                cursorLocation.x = n;

            notifyCursorObservers();
        }
    }

    public void deleteBefore(){
        if(cursorLocation.x != 0){
            StringBuilder sb = new StringBuilder(lines.get(cursorLocation.y));
            String newLine = sb.deleteCharAt(cursorLocation.x-1).toString();
            lines.set(cursorLocation.y, newLine);

            cursorLocation.x--;
        }
        else {
            // beginning of first line
            if(cursorLocation.y == 0)
                return;

            //beginning of any other line
            int currLen = lines.get(cursorLocation.y-1).length();
            String combinedLines = lines.get(cursorLocation.y-1) + lines.get(cursorLocation.y);
            lines.set(cursorLocation.y-1, combinedLines);
            System.out.println(cursorLocation.y);
            lines.remove(cursorLocation.y);

            cursorLocation.y--;
            cursorLocation.x = currLen;

        }

        notifyTextObservers();
        notifyCursorObservers();
    }

    public void deleteAfter(){
        if(cursorLocation.x != lines.get(cursorLocation.y).length()){

            StringBuilder sb = new StringBuilder(lines.get(cursorLocation.y));
            String newLine = sb.deleteCharAt(cursorLocation.x).toString();
            lines.set(cursorLocation.y, newLine);

        }
        else{ // cursor is at the end of line
            if(cursorLocation.y == lines.size()-1) // at the end of the last line
                return;

            //at the end of any other line
            String combinedLines = lines.get(cursorLocation.y) + lines.get(cursorLocation.y + 1);
            lines.set(cursorLocation.y, combinedLines);
            lines.remove(cursorLocation.y+1);

        }
        notifyTextObservers();
    }
    public void deleteRange(LocationRange r){

    }

    public LocationRange getSelectionRange(){
        return selectionRange;
    }

    public void setSelectionRange(LocationRange range){
        this.selectionRange = range;
    }

    public void insert(char c){

        StringBuilder sb = new StringBuilder(lines.get(cursorLocation.y));
        String newLine = sb.insert(cursorLocation.x, c).toString();
        lines.set(cursorLocation.y, newLine);

        cursorLocation.x++;

        notifyTextObservers();
        notifyCursorObservers();
    }

    public void insert(String text){

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
