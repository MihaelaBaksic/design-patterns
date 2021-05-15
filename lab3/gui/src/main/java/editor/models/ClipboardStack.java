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
    }

    public String pop(){
        if (!isEmpty())
            return texts.pop();

        return "";
    }

    public String peekTop(){
        if(!isEmpty())
            return texts.peek();

        return "";
    }

    public boolean isEmpty(){
        return texts.isEmpty();
    }

    public void addObserver(ClipboardObserver c){
        observers.add(c);
    }
}
