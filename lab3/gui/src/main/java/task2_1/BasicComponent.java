package task2_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

public class BasicComponent extends JComponent implements KeyListener{

    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D)g.create();

        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0, 10, this.getWidth(), 10);
        g2d.drawLine(10, 30, 10, 100);

        int h = g.getFontMetrics().getHeight();
        g.drawString("Bla bla ble", 10, 200);
        g.drawString("Ble ble ble", 10, 200+h);
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            System.out.println("ENTER");
            JFrame frame = (JFrame) SwingUtilities.getRoot(BasicComponent.this);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
