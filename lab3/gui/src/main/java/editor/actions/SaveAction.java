package editor.actions;

import editor.FileManager;
import editor.UndoManager;
import editor.models.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Iterator;

public class SaveAction extends AbstractAction {

    private TextEditorModel model;
    private UndoManager undoManager;
    private FileManager fileManager;

    public SaveAction(String name, TextEditorModel model, UndoManager undoManager, FileManager fileManager){
        super(name);
        this.model = model;
        this.undoManager = undoManager;
        this.fileManager = fileManager;
        this.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control S"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //ako postoji fajl, pisi u njega
        // ako ne postoji trazi da se izabere fajl za sejvanje i spremi ga u onaj
        if(!fileManager.fileOpened()){
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(null);

            if(returnVal==JFileChooser.APPROVE_OPTION){
                File f = chooser.getSelectedFile();
                System.out.println(f.getName() + " opened");
                fileManager.setFile(f);
            }
            else
                return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileManager.getFile()));

            Iterator<String> it = model.allLines();

            while(it.hasNext()) {
                writer.write(it.next());
                if(it.hasNext())
                    writer.newLine();
            }
            writer.flush();
            writer.close();

            System.out.println("Sejvano");
            undoManager.clear();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
