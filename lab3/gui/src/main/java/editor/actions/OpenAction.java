package editor.actions;

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
    private UndoManager manager;

    public OpenAction(String name, TextEditorModel model, UndoManager manager){
        super(name);
        this.model = model;
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //if can be saved ask to save
        // if yes, save

        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);

        if(returnVal==JFileChooser.APPROVE_OPTION){
            File f = chooser.getSelectedFile();
            System.out.println(f.getName() + " opened");

            List<String> lines = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                lines = reader.lines().collect(Collectors.toList());
            } catch (FileNotFoundException fileNotFoundException) {
                return;
            }


            model.clear();
            model.setLines(lines);
            model.cursorToStart();
            manager.clear();
        }

    }
}
