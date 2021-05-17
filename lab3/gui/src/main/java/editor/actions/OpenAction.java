package editor.actions;

import editor.FileManager;
import editor.UndoManager;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OpenAction extends AbstractAction {

    private TextEditorModel model;
    private UndoManager undoManager;
    private FileManager fileManager;

    public OpenAction(String name, TextEditorModel model, UndoManager undoManager, FileManager fileManager){
        super(name);
        this.model = model;
        this.undoManager = undoManager;
        this.fileManager = fileManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);

        if(returnVal==JFileChooser.APPROVE_OPTION){
            File f = chooser.getSelectedFile();
            System.out.println(f.getName() + " opened");

            List<String> lines;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                lines = reader.lines().collect(Collectors.toList());
                reader.close();
            } catch (IOException exception ) {
                exception.printStackTrace();
                return;
            }

            fileManager.setFile(f);

            model.clear();
            model.setLines(lines);
            model.cursorToStart();
            undoManager.clear();
        }

    }
}
