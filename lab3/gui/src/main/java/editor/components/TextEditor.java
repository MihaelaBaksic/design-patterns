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
import editor.observers.UndoManagerObserver;
import plugin.Plugin;
import plugin.PluginLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class TextEditor extends JPanel implements CursorObserver, TextObserver, ClipboardObserver, UndoManagerObserver {

    private static final long serialVersionUID = 1L;
    private static final Font font = new Font("Monospaced", Font.PLAIN, 12);

    private ClipboardStack clipboard;
    private TextEditorModel model;
    private UndoManager undoManager;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JLabel statusBar;
    private FileManager fileManager;


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
    private Action saveAction;

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


    public TextEditor() throws Exception {
        super();
        clipboard = new ClipboardStack();
        undoManager = UndoManager.getInstance();
        this.menuBar = new JMenuBar();
        this.toolBar = new JToolBar();
        this.statusBar = new JLabel();
        this.fileManager = FileManager.getInstance();
        init();
        initMenu();
        initToolbar();
        initStatusBar();

        enableSelectionActions();
        enableUndoActions();
        enableClipboardActions();
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
    public JToolBar getToolBar() { return toolBar; }
    public JLabel getStatusBar() { return statusBar; }



    private void init(){
        this.setLayout(new BorderLayout());
        this.setFocusable(true);

        model = new TextEditorModel("");
        model.addCursorObserver(this);
        model.addTextObserver(this);
        clipboard.addObserver(this);
        undoManager.addObserver(this);

        deleteAfter = new DeleteAfterAction("Delete", model, undoManager);
        this.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE), "deleteKey");
        this.getActionMap().put("deleteKey", deleteAfter);
        deleteBefore = new DeleteBeforeAction("Back space", model, undoManager);

        selectLeft = new SelectLeftAction("Select left", model);
        selectRight = new SelectRightAction("Select right", model);
        selectUp = new SelectUpAction("Select up", model);
        selectDown = new SelectDownAction("Select down", model);

        cursorToStart = new CursorToDocumentStartAction("Cursor to document start", model);
        cursorToEnd = new CursorToDocumentEndAction("Cursor to document end", model);

        clearDocument = new ClearDocumentAction("Clear document", model, undoManager);
        deleteSelection = new DeleteSelectionAction("Delete selection", model, undoManager);
        exitAction = new ExitAction("Exit", this);

        copyAction = new CopyAction("Copy", model, clipboard);
        cutAction = new CutAction("Cut", model, clipboard, undoManager);
        pasteAction = new PasteAction("Paste", model, clipboard, undoManager);
        pasteAndTakeAction = new PasteAndTakeAction("Paste and Take", model, clipboard, undoManager);

        redoAction = new RedoAction("Redo", undoManager);
        undoAction = new UndoAction("Undo", undoManager);

        saveAction = new SaveAction("Save", model, undoManager, fileManager);

        this.addKeyListener(new KeyAdapter() {

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
        });

    }

    private void initMenu() throws Exception {

        JMenu menuFile = new JMenu("File");
        itemOpen = new JMenuItem(new OpenAction("Open", model, undoManager, fileManager));
        itemSave = new JMenuItem(saveAction);
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

        ClassLoader parent = PluginLoader.class.getClassLoader();

        URLClassLoader pluginClassLoader = new URLClassLoader(
                new URL[] {
                        new File("/home/mihaela/Documents/FER/ooup/labosi/designPatternsLab/lab3/plugin.jar").toURI().toURL()
                }, parent);

        List<Plugin> plugins = PluginLoader.loadPlugins(pluginClassLoader);
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

    private void initStatusBar(){
        statusBar.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        setStatusBarText();
        statusBar.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g){

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
            g.fillRect(min.x*w, min.y*h + diff, getWidth() - (min.x)*w, h); //min from x to end
            g.fillRect(0, (min.y+1)*h + diff, getWidth(), h*(rowDistance-1)); //all full rows
            g.fillRect(0, max.y*h + diff, max.x*w , h ); //last row from 0 to x
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

    }

    @Override
    public void updateCursorLocation(Location loc) {
        repaint();
        enableSelectionActions();
        setStatusBarText();
    }

    @Override
    public void updateText() {
        repaint();
        setStatusBarText();
    }

    @Override
    public void updateClipboard() {
        enableClipboardActions();
    }

    @Override
    public void updateForUndo() {
        enableUndoActions();
    }


    private void enableUndoActions(){
        undo.setEnabled(!undoManager.undoStackEmpty());
        redo.setEnabled(!undoManager.redoStackEmpty());
        itemUndo.setEnabled(!undoManager.undoStackEmpty());
        itemRedo.setEnabled(!undoManager.redoStackEmpty());
        itemSave.setEnabled(!undoManager.undoStackEmpty());

    }

    private void enableSelectionActions(){
        copy.setEnabled(model.getSelectionRange().isSelected());
        cut.setEnabled(model.getSelectionRange().isSelected());
        itemCopy.setEnabled(model.getSelectionRange().isSelected());
        itemCut.setEnabled(model.getSelectionRange().isSelected());
        itemDeleteSelection.setEnabled(model.getSelectionRange().isSelected());
    }

    private void enableClipboardActions(){
        paste.setEnabled(!clipboard.isEmpty());
        itemPaste.setEnabled(!clipboard.isEmpty());
        itemPasteAndTake.setEnabled(!clipboard.isEmpty());
    }

    private void setStatusBarText(){
        int x = model.getCursorLocation().x;
        int y = model.getCursorLocation().y;
        statusBar.setText("Cursor at (" + x + ", " + y + ")  Lines: " + model.linesNumber());
    }

}
