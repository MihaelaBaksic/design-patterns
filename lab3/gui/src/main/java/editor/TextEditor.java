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
    private Action clearDocument;
    private Action cursorToStart;
    private Action cursorToEnd;

    private ClipboardStack clipboard;

    private TextEditorModel model;

    private UndoManager undoManager = UndoManager.getInstance();

    private JMenuBar menuBar;

    public TextEditor(){
        super();
        clipboard = new ClipboardStack();
        this.menuBar = new JMenuBar();
        initGui();
        initMenu();
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private void initMenu(){
        JMenu menuFile = new JMenu("File");
        JMenuItem itemOpen = new JMenuItem("Open");
        JMenuItem itemSave = new JMenuItem("Save");
        JMenuItem itemExit = new JMenuItem("Exit");

        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemExit);


        JMenu menuEdit = new JMenu("Edit");
        JMenuItem itemUndo = new JMenuItem("Undo");
        JMenuItem itemRedo = new JMenuItem("Redo");
        JMenuItem itemCut = new JMenuItem("Cut");
        JMenuItem itemCopy = new JMenuItem("Copy");
        JMenuItem itemPaste = new JMenuItem("Paste");
        JMenuItem itemPasteAndTake = new JMenuItem("Paste and Take");
        JMenuItem itemDeleteSelection = new JMenuItem("Delete selection");
        JMenuItem itemClearDocument = new JMenuItem(clearDocument);


        menuEdit.add(itemUndo);
        menuEdit.add(itemRedo);
        menuEdit.add(itemCut);
        menuEdit.add(itemCopy);
        menuEdit.add(itemPaste);
        menuEdit.add(itemPasteAndTake);
        menuEdit.add(itemDeleteSelection);
        menuEdit.add(itemClearDocument);


        JMenu menuMove = new JMenu("Move");
        JMenuItem itemCursorToStart = new JMenuItem(cursorToStart);
        JMenuItem itemCursorToEnd = new JMenuItem(cursorToEnd);
        JMenuItem deleteAfterItem = new JMenuItem(deleteBefore);

        menuMove.add(deleteAfterItem);
        menuMove.add(itemCursorToStart);
        menuMove.add(itemCursorToEnd);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuMove);
    }

    private void initGui(){
        this.setLayout(new BorderLayout());
        this.setFocusable(true);

        model = new TextEditorModel("Kjduet\nLOLOLOaushaihs asduhsh sshk\njie     \nej");
        model.addCursorObserver(this);
        model.addTextObserver(this);

        deleteAfter = new DeleteAfterAction("Delete",model);
        deleteAfter.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE));
        this.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE), "deleteKey");
        this.getActionMap().put("deleteKey", deleteAfter);

        deleteBefore = new DeleteBeforeAction("Back space", model);
        deleteBefore.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke((char) KeyEvent.VK_BACK_SPACE));

        selectLeft = new SelectLeftAction("Select left", model);
        selectRight = new SelectRightAction("Select right", model);

        cursorToStart = new CursorToDocumentStartAction("Cursor to document start", model);
        cursorToEnd = new CursorToDocumentEndAction("Cursoe to document end", model);

        clearDocument = new ClearDocumentAction("Clear document", model);




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
                        new InsertCharAction("Insert char", model, (char) code).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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
        int offset_y=h;
        while(linesIt.hasNext()){
            g.drawString(linesIt.next(), 0, offset_y);
            offset_y+=h;
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
