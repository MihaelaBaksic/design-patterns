package render;

import util.Point;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SVGRenderer implements Renderer{

    private List<String> lines = new ArrayList<>();
    private String fileName;

    public SVGRenderer(String fileName) {
        // zapamti fileName; u lines dodaj zaglavlje SVG dokumenta:
        // <svg xmlns=... >
        // ...
        this.fileName = fileName;
        String header = "<svg xmlns= >";
    }

    public void close() throws IOException {
        lines.add("</svg>");

        BufferedWriter f = new BufferedWriter(new FileWriter(new File(fileName)));
        for(String l : lines){
            f.write(l);
            f.newLine();
        }
        f.close();
    }

    @Override
    public void drawLine(Point s, Point e) {
        String line = "<line>";
        lines.add(line);

    }

    @Override
    public void fillPolygon(Point[] points) {

    }
}
