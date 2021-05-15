package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextEditorModel implements LinesIterable{

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
        selectionRange = new LocationRange();
    }

    public Location getCursorLocation(){
        return cursorLocation;
    }

    public String getLine(int index){
        return lines.get(index);
    }

    @Override
    public Iterator<String> allLines() {
        return lines.iterator();
    }

    @Override
    public Iterator<String> linesRange(int index1, int index2) {
        return lines.subList(index1, index2).iterator();
    }

    public void clear(){
        lines = new ArrayList<>();
        lines.add("");

        cursorLocation.x = 0;
        cursorLocation.y = 0;

        notifyTextObservers();
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

    public void cursorToStart(){
        cursorLocation.x = 0;
        cursorLocation.y = 0;

        notifyCursorObservers();
    }

    public void cursorToEnd(){
        cursorLocation.y = lines.size() > 0 ? lines.size()-1 : 0;
        cursorLocation.x = lines.get(lines.size()-1).length();

        notifyCursorObservers();
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

        if(selectionRange.isSelected()){
            deleteRange(this.selectionRange);
            return;
        }

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

        if(selectionRange.isSelected()){
            deleteRange(this.selectionRange);
            return;
        }

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

        if(!r.isSelected())
            return;

        Location minLocation = r.getMin();
        Location maxLocation = r.getMax();

        int indexLastLine = maxLocation.y;
        int indexFirstLine = minLocation.y;

        String firstLine = lines.get(indexFirstLine);
        String lastLine = lines.get(indexLastLine);
        String newLine = firstLine.substring(0, minLocation.x) + lastLine.substring(maxLocation.x);
        lines.set(minLocation.y, newLine);

        while(indexLastLine > indexFirstLine){
            lines.remove(indexLastLine);
            indexLastLine--;
        }

        notifyTextObservers();
        notifyCursorObservers();
    }

    public LocationRange getSelectionRange(){
        return selectionRange;
    }

    public void setSelectionRange(LocationRange range){
        this.selectionRange = range;
        notifyCursorObservers();
    }

    public void insert(Character c){

        if( !Character.isLetterOrDigit(c) && !(c==10))
            return;

        if(c==10)
            enter();
        else{
            StringBuilder sb = new StringBuilder(lines.get(cursorLocation.y));
            String newLine = sb.insert(cursorLocation.x, c).toString();
            lines.set(cursorLocation.y, newLine);

            cursorLocation.x++;
        }

        System.out.println(lines);
        notifyTextObservers();
        notifyCursorObservers();
    }

    public void insert(String text){

    }

    private void enter(){
        String currentLine = lines.get(cursorLocation.y);

        lines.set(cursorLocation.y, currentLine.substring(0, cursorLocation.x));
        lines.add(cursorLocation.y+1, currentLine.substring(cursorLocation.x));

        cursorLocation.y++;
        cursorLocation.x = 0;
    }




}
