package task2_1;

import javax.swing.*;
import java.awt.*;

public class BasicFrame extends JFrame {

    public BasicFrame(){
        super();

        this.setSize(300, 300);
        this.setTitle("Basic Frame");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGui();
    }

    private void initGui(){
        this.getContentPane().setLayout(new BorderLayout());

        BasicComponent component = new BasicComponent();
        this.add(component);
        this.addKeyListener(component);
    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new BasicFrame();
        });
    }
}
