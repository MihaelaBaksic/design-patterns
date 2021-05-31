import components.GUI;
import model.CompositeShape;
import model.GraphicalObject;
import model.LineSegment;
import model.Oval;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {
            List<GraphicalObject> objects = new ArrayList<>();

            objects.add(new LineSegment());
            objects.add(new Oval());

            GUI gui = new GUI(objects);
            gui.setVisible(true);
            gui.setSize(600, 600);
            gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });

    }
}
