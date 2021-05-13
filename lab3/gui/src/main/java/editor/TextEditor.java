package editor;


import javax.swing.*;
import java.awt.*;

public class TextEditor extends JFrame {

    private static final long serialVersionUID = 1L;

    public TextEditor(){
        super();

        this.setTitle("TextEditor");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGui();
    }

    private void initGui(){
        this.getContentPane().setLayout(new BorderLayout());

        TextEditorModel model = new TextEditorModel("Kjduet\nLOLOLOaushaihs asduhsh sshkjieej");
        this.getContentPane().add(model);

        this.setSize(600, 600);
    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TextEditor();
        });
    }

}
