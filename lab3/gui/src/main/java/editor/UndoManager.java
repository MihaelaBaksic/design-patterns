package editor;

import editor.actions.undoables.EditAction;
import editor.observers.UndoManagerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UndoManager {

    private Stack<EditAction> undoStack;
    private Stack<EditAction> redoStack;

    private List<UndoManagerObserver> observers;

    private static UndoManager instance;

    public static UndoManager getInstance(){
        if(instance==null){
            instance = new UndoManager();
        }
        return instance;
    }

    private UndoManager(){
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        observers = new ArrayList<>();
    }

    public void undo(){
        if(!undoStack.empty()){
            EditAction action = undoStack.pop();
            redoStack.push(action);
            action.executeUndo();

            notifyObservers();
        }
    }

    public void redo(){
        if(!redoStackEmpty()){
            EditAction action = redoStack.pop();
            undoStack.push(action);
            action.executeDo();

            notifyObservers();
        }
    }


    public void push(EditAction c){
        redoStack.clear();
        undoStack.push(c);

        notifyObservers();
    }

    public boolean undoStackEmpty(){
        return undoStack.empty();
    }

    public boolean redoStackEmpty(){
        return redoStack.empty();
    }

    public void clear(){
        undoStack.clear();
        redoStack.clear();

        notifyObservers();
    }

    public void addObserver(UndoManagerObserver observer){
        this.observers.add(observer);
    }

    private void notifyObservers(){
        for(var o : observers)
            o.updateForUndo();
    }

}
