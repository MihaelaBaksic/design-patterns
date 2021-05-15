package editor.models;

import editor.observers.ClipboardObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClipboardStack {

    private Stack<String> texts;
    private List<ClipboardObserver> observers;

    public ClipboardStack(){
        this.texts = new Stack<>();
        this.observers = new ArrayList<>();
    }

    public void clearStack(){
        texts.clear();
    }

    public void push(String text){
        texts.push(text);
        notifyObservers();
    }

    public String pop(){
        if (!isEmpty()){
            notifyObservers();
            return texts.pop();
        }
        return "";
    }

    public String peekTop(){
        if(!isEmpty())
            return texts.peek();

        return "";
    }

    private void notifyObservers(){
        for(var o : observers)
            o.updateClipboard();
    }

    public boolean isEmpty(){
        return texts.isEmpty();
    }

    public void addObserver(ClipboardObserver c){
        observers.add(c);
    }
}
