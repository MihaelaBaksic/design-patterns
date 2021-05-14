package editor;

import javax.swing.*;

public class Main extends JFrame{

    public Main() {
        super();

        this.setTitle("TextEditor");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(new TextEditor());
        this.setSize(600, 600);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new Main();
        });
    }
}
