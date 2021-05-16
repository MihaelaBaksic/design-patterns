package editor;

import editor.components.TextEditor;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{

    public Main() throws Exception {
        super();

        this.setTitle("TextEditor");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        TextEditor textEditor = new TextEditor();

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        header.add(textEditor.getToolBar(), BorderLayout.NORTH);
        header.add(textEditor.getStatusBar(), BorderLayout.SOUTH);


        this.getContentPane().add(textEditor, BorderLayout.CENTER);
        this.getContentPane().add(header, BorderLayout.NORTH);
        this.setJMenuBar(textEditor.getMenuBar());

        this.setSize(600, 600);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new Main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
