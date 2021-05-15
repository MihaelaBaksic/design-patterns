package editor;

import java.util.Stack;

public class UndoManager {

    Stack<EditAction> undoStack;
    Stack<EditAction> redoStack;

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
    }

    public void undo(){
        if(!undoStack.empty()){
            EditAction action = undoStack.pop();
            redoStack.push(action);
            action.executeUndo();
        }
    }

    public void push(EditAction c){
        redoStack.clear();
        undoStack.push(c);
    }

    public boolean undoStackEmpty(){
        return undoStack.empty();
    }

    public boolean redoStackEmpty(){
        return redoStack.empty();
    }

}
