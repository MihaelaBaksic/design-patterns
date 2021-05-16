package editor.components;


import editor.*;
import editor.actions.*;
import editor.actions.clip.CopyAction;
import editor.actions.clip.CutAction;
import editor.actions.clip.PasteAction;
import editor.actions.clip.PasteAndTakeAction;
import editor.actions.cursor.CursorToDocumentEndAction;
import editor.actions.cursor.CursorToDocumentStartAction;
import editor.actions.deletion.ClearDocumentAction;
import editor.actions.deletion.DeleteAfterAction;
import editor.actions.deletion.DeleteBeforeAction;
import editor.actions.deletion.DeleteSelectionAction;
import editor.actions.selection.SelectDownAction;
import editor.actions.selection.SelectLeftAction;
import editor.actions.selection.SelectRightAction;
import editor.actions.selection.SelectUpAction;
import editor.actions.undoables.RedoAction;
import editor.actions.undoables.UndoAction;
import editor.models.ClipboardStack;
import editor.models.Location;
import editor.models.LocationRange;
import editor.models.TextEditorModel;
import editor.observers.ClipboardObserver;
import editor.observers.CursorObserver;
import editor.observers.TextObserver;
import plugin.Plugin;
import plugin.PluginLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class TextEditor extends JPanel implements CursorObserver, TextObserver, ClipboardObserver {

    private static final long serialVersionUID = 1L;
    private static final Font font = new Font("Monospaced", Font.PLAIN, 12);

    private Action deleteAfter;
    private Action deleteBefore;
    private Action selectLeft;
    private Action selectRight;
    private Action selectUp;
    private Action selectDown;
    private Action clearDocument;
    private Action deleteSelection;
    private Action cursorToStart;
    private Action cursorToEnd;
    private Action exitAction;
    private Action copyAction;
    private Action cutAction;
    private Action pasteAction;
    private Action pasteAndTakeAction;

    private Action redoAction;
    private Action undoAction;

    private JButton copy;
    private JButton paste;
    private JButton cut;
    private JButton undo;
    private JButton redo;

    private JMenuItem itemOpen;
    private JMenuItem itemSave;
    private JMenuItem itemExit;

    private JMenuItem itemUndo;
    private JMenuItem itemRedo;
    private JMenuItem itemCut;
    private JMenuItem itemCopy;
    private JMenuItem itemPaste;
    private JMenuItem itemPasteAndTake;
    private JMenuItem itemDeleteSelection;
    private JMenuItem itemClearDocument;
    private JMenuItem itemCursorToStart;
    private JMenuItem itemCursorToEnd;
    private JMenuItem deleteAfterItem;

    private ClipboardStack clipboard;
    private TextEditorModel model;
    private UndoManager undoManager;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JLabel statusBar;


    public TextEditor() throws Exception {
        super();
        clipboard = new ClipboardStack();
        undoManager = UndoManager.getInstance();
        this.menuBar = new JMenuBar();
        this.toolBar = new Toolbar();
        this.statusBar = new JLabel();
        init();
        initMenu();
        initToolbar();
        initStatusBar();
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
    public JToolBar getToolBar() { return toolBar; }
    public JLabel getStatusBar() { return statusBar; }

    private void initMenu() throws Exception {
        JMenu menuFile = new JMenu("File");
        itemOpen = new JMenuItem("Open");
        itemSave = new JMenuItem("Save");
        itemExit = new JMenuItem(exitAction);

        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemExit);


        JMenu menuEdit = new JMenu("Edit");
        itemUndo = new JMenuItem(undoAction);
        itemRedo = new JMenuItem(redoAction);
        itemCut = new JMenuItem(cutAction);
        itemCopy = new JMenuItem(copyAction);
        itemPaste = new JMenuItem(pasteAction);
        itemPasteAndTake = new JMenuItem(pasteAndTakeAction);
        itemDeleteSelection = new JMenuItem(deleteSelection);
        itemClearDocument = new JMenuItem(clearDocument);


        menuEdit.add(itemUndo);
        menuEdit.add(itemRedo);
        menuEdit.add(itemCut);
        menuEdit.add(itemCopy);
        menuEdit.add(itemPaste);
        menuEdit.add(itemPasteAndTake);
        menuEdit.add(itemDeleteSelection);
        menuEdit.add(itemClearDocument);


        JMenu menuMove = new JMenu("Move");
        itemCursorToStart = new JMenuItem(cursorToStart);
        itemCursorToEnd = new JMenuItem(cursorToEnd);
        deleteAfterItem = new JMenuItem(deleteBefore);

        menuMove.add(deleteAfterItem);
        menuMove.add(itemCursorToStart);
        menuMove.add(itemCursorToEnd);

        JMenu menuPlugins = new JMenu("Plugins");

        //ServiceLoader<Plugin> plugins = PluginLoader.load();
        List<Plugin> plugins = PluginLoader.loadPlugins();
        for(var p : plugins){
            JMenuItem item = new JMenuItem(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.execute(model, undoManager, clipboard);
                }
            });
            item.setText(p.getName());
            menuPlugins.add(item);
        }


        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuMove);
        menuBar.add(menuPlugins);
    }

    private void init(){
        this.setLayout(new BorderLayout());
        this.setFocusable(true);

        model = new TextEditorModel("Kako je danas\nlijep i suncan dan.\nBas krasno.");
        model.addCursorObserver(this);
        model.addTextObserver(this);
        clipboard.addObserver(this);

        deleteAfter = new DeleteAfterAction("Delete",model, undoManager);
        //deleteAfter.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE));
        this.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE), "deleteKey");
        this.getActionMap().put("deleteKey", deleteAfter);

        deleteBefore = new DeleteBeforeAction("Back space", model, undoManager);
        deleteBefore.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke((char) KeyEvent.VK_BACK_SPACE));

        selectLeft = new SelectLeftAction("Select left", model);
        selectRight = new SelectRightAction("Select right", model);
        selectUp = new SelectUpAction("Select up", model);
        selectDown = new SelectDownAction("Select down", model);

        cursorToStart = new CursorToDocumentStartAction("Cursor to document start", model);
        cursorToEnd = new CursorToDocumentEndAction("Cursoe to document end", model);

        clearDocument = new ClearDocumentAction("Clear document", model, undoManager);
        deleteSelection = new DeleteSelectionAction("Delete selection", model, undoManager);
        exitAction = new ExitAction("Exit", this);

        copyAction = new CopyAction("Copy", model, clipboard);
        copyAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control C"));

        cutAction = new CutAction("Cut", model, clipboard, undoManager);
        cutAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control X"));

        pasteAction = new PasteAction("Paste", model, clipboard, undoManager);
        pasteAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control V"));

        pasteAndTakeAction = new PasteAndTakeAction("Paste and Take", model, clipboard, undoManager);
        pasteAndTakeAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("control shift V"));

        redoAction = new RedoAction("Redo", undoManager);
        redoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));

        undoAction = new UndoAction("Undo", undoManager);
        undoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y"));

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code){
                    case KeyEvent.VK_LEFT:
                        if(e.isShiftDown())
                            selectLeft.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                        else{
                            model.moveCursorLeft();
                            model.removeSelection();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(e.isShiftDown())
                            selectUp.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                        else {
                            model.moveCursorUp();
                            model.removeSelection();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(e.isShiftDown())
                            selectRight.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                        else{
                            model.moveCursorRight();
                            model.removeSelection();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(e.isShiftDown())
                            selectDown.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                        else
                        {
                            model.moveCursorDown();
                            model.removeSelection();
                        }
                        break;
                    default:
                        if(e.isControlDown())
                            return;
                        if(!e.isShiftDown()){
                            code = Character.toLowerCase(code);
                        }
                        new InsertCharAction("Insert char", model, (char) code, undoManager).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

    }

    private void initToolbar(){
        undo = new JButton(undoAction);
        undo.setFocusable(false);
        redo = new JButton(redoAction);
        redo.setFocusable(false);
        cut = new JButton(cutAction);
        cut.setFocusable(false);
        copy = new JButton(copyAction);
        copy.setFocusable(false);
        paste = new JButton(pasteAction);
        paste.setFocusable(false);

        toolBar.add(undo);
        toolBar.add(redo);
        toolBar.add(cut);
        toolBar.add(copy);
        toolBar.add(paste);

    }

    @Override
    protected void paintComponent(Graphics g){

        setVisibilities();

        super.paintComponent(g);
        g.setFont(font);

        int h = g.getFontMetrics().getHeight();
        int w = g.getFontMetrics().charWidth('i');
        int asc = g.getFontMetrics().getAscent();
        int diff = Math.abs(h - asc);

        //paint selection
        LocationRange selection = model.getSelectionRange();
        Location min = selection.getMin();
        Location max = selection.getMax();

        int rowDistance = selection.getRowDistance();
        g.setColor(Color.YELLOW);
        if(rowDistance==0){
            g.fillRect(min.x*w, min.y*h + diff, (max.x-min.x)*w, h);
        }
        else{
            //min from x to end
            g.fillRect(min.x*w, min.y*h + diff, getWidth() - (min.x)*w, h);
            //all full rows
            g.fillRect(0, (min.y+1)*h, getWidth(), h*(rowDistance-1));
            //last row from 0 to x
            g.fillRect(0, max.y*w + diff, max.x*w , h );
        }

        // paint text
        g.setColor(Color.BLACK);

        Iterator<String> linesIt = model.allLines();
        int offset_y=h;
        while(linesIt.hasNext()){
            g.drawString(linesIt.next(), 0, offset_y);
            offset_y+=h;
        }

        //paint cursor
        Location cursorLocation = model.getCursorLocation();
        g.drawLine(cursorLocation.x*w, cursorLocation.y*h + diff, cursorLocation.x*w, (cursorLocation.y+1)*h + diff);

        //paint status bar
        int x = model.getCursorLocation().x;
        int y = model.getCursorLocation().y;
        statusBar.setText("Cursor at (" + x + ", " + y + ")  Lines: " + model.linesNumber());
    }

    private void initStatusBar(){
        statusBar.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        int x = model.getCursorLocation().x;
        int y = model.getCursorLocation().y;
        statusBar.setText("Cursor at (" + x + ", " + y + ")  Lines: " + model.linesNumber());
        statusBar.setVisible(true);
    }

    @Override
    public void updateCursorLocation(Location loc) {
        repaint();
    }

    @Override
    public void updateText() {
        repaint();
    }


    @Override
    public void updateClipboard() {
        // updating activity of action buttons related to clipboard
        paste.setEnabled(!clipboard.isEmpty());

        // updating activity of items related to clipboard
        itemPaste.setEnabled(!clipboard.isEmpty());
        itemPasteAndTake.setEnabled(!clipboard.isEmpty());

    }


    private void setVisibilities(){
        // updating activity of action buttons
        paste.setEnabled(!clipboard.isEmpty());
        copy.setEnabled(model.getSelectionRange().isSelected());
        cut.setEnabled(model.getSelectionRange().isSelected());
        undo.setEnabled(!undoManager.undoStackEmpty());
        redo.setEnabled(!undoManager.redoStackEmpty());

        // updating activity of menu items
        itemPaste.setEnabled(!clipboard.isEmpty());
        itemPasteAndTake.setEnabled(!clipboard.isEmpty());
        itemCopy.setEnabled(model.getSelectionRange().isSelected());
        itemCut.setEnabled(model.getSelectionRange().isSelected());
        itemDeleteSelection.setEnabled(model.getSelectionRange().isSelected());
        itemUndo.setEnabled(!undoManager.undoStackEmpty());
        itemRedo.setEnabled(!undoManager.redoStackEmpty());
        itemSave.setEnabled(!undoManager.undoStackEmpty());

    }
}
