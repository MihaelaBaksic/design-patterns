package editor;


import editor.actions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

public class TextEditor extends JPanel implements CursorObserver, TextObserver {

    private static final long serialVersionUID = 1L;
    private static final Font font = new Font("Monospaced", Font.PLAIN, 12);

    private Action deleteAfter;
    private Action deleteBefore;
    private Action selectLeft;
    private Action selectRight;

    private ClipboardStack clipboard;

    private TextEditorModel model;

    private UndoManager undoManager = UndoManager.getInstance();

    public TextEditor(){
        super();
        this.setVisible(true);
        clipboard = new ClipboardStack();
        init();
    }

    private void init(){
        this.setLayout(new BorderLayout());
        this.setFocusable(true);

        model = new TextEditorModel("Kjduet\nLOLOLOaushaihs asduhsh sshkjieej");
        model.addCursorObserver(this);
        model.addTextObserver(this);
        this.add(model);

        deleteAfter = new DeleteAfterAction(model);
        deleteBefore = new DeleteBeforeAction(model);
        selectLeft = new SelectLeftAction(model);
        selectRight = new SelectRightAction(model);

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
                    default:
                        new InsertCharAction(model, (char) code).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

    }

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        // paint text
        g.setFont(font);
        int h = g.getFontMetrics().getHeight();
        int w = g.getFontMetrics().charWidth('i');
        Iterator<String> linesIt = model.allLines();
        while(linesIt.hasNext()){
            g.drawString(linesIt.next(), 0, h);

            h+=h;
        }

        //paint cursor
        int asc = g.getFontMetrics().getAscent();
        h = g.getFontMetrics().getHeight();
        int diff = Math.abs(h - asc);
        Location cursorLocation = model.getCursorLocation();
        g.drawLine(cursorLocation.x*w, cursorLocation.y*h + diff, cursorLocation.x*w, (cursorLocation.y+1)*h + diff);

        //paint selection
        LocationRange selection = model.getSelectionRange();
        Location min = selection.getMin();
        Location max = selection.getMax();

        int rowDistance = selection.getRowDistance();

        /*System.out.println(min.x*w);
        System.out.println(min.y*h);
        System.out.println((max.x-min.x)*w);
        System.out.println(h);*/

        //if(rowDistance==0){
        // g.setColor(Color.YELLOW);
        // g.fillRect(min.x*w, min.y*h, (max.x-min.x)*w, h);
        //g.fillRect(10, 10, 200, 200);
        //}
        //else{

        //}

    }


    @Override
    public void updateCursorLocation(Location loc) {
        repaint();
    }

    @Override
    public void updateText() {
        repaint();
    }


}
