package editor;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{

    public Main() {
        super();

        this.setTitle("TextEditor");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setJMenuBar(new Menu());
        this.getContentPane().add(new Toolbar(), BorderLayout.NORTH);
        this.getContentPane().add(new TextEditor(), BorderLayout.CENTER);

        this.setSize(600, 600);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new Main();
        });
    }
}
