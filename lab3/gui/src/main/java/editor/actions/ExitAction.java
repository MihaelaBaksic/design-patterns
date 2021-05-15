package editor.actions;

import editor.components.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction {

    private TextEditor editor;

    public ExitAction(String name, TextEditor editor){
        super(name);
        this.editor = editor;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ((JFrame) SwingUtilities.getWindowAncestor(editor)).dispose();
    }
}
