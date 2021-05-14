package editor;


import editor.actions.DeleteAfterAction;
import editor.actions.DeleteBeforeAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextEditor extends JFrame implements CursorObserver, TextObserver {

    private static final long serialVersionUID = 1L;

    private Action deleteAfter;
    private Action deleteBefore;

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
        model.addTextObserver(this);
        this.getContentPane().add(model);

        deleteAfter = new DeleteAfterAction(model);
        deleteBefore = new DeleteBeforeAction(model);

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
                        break;
                    case KeyEvent.VK_DELETE:
                        deleteAfter.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        deleteBefore.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                        break;
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

    @Override
    public void updateText() {
        repaint();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TextEditor();
        });
    }
}
