package editor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextEditor extends JFrame implements CursorObserver {

    private static final long serialVersionUID = 1L;

    public TextEditor(){
        super();

        this.setTitle("TextEditor");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        init();
    }

    private void init(){
        this.getContentPane().setLayout(new BorderLayout());

        TextEditorModel model = new TextEditorModel("Kjduet\nLOLOLOaushaihs asduhsh sshkjieej");
        model.addCursorObserver(this);
        this.getContentPane().add(model);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code){
                    case KeyEvent.VK_LEFT:
                        model.moveCursorLeft();
                        break;
                    case KeyEvent.VK_UP:
                        model.moveCursorUp();
                        break;
                    case KeyEvent.VK_RIGHT:
                        model.moveCursorRight();
                        break;
                    case KeyEvent.VK_DOWN:
                        model.moveCursorDown();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        this.setSize(600, 600);
    }


    @Override
    public void updateCursorLocation(TextEditorModel.Location loc) {
        repaint();
    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TextEditor();
        });
    }


}
