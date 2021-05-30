package render;

import util.Point;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SVGRenderer implements Renderer{

    private List<String> lines;
    private String fileName;

    public SVGRenderer(String fileName) {
        // zapamti fileName; u lines dodaj zaglavlje SVG dokumenta:
        // <svg xmlns=... >
        // ...
        this.fileName = fileName;
        lines = new ArrayList<>();
        String header = "<svg height=\"600\" width=\"600\">";
        lines.add(header);
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
        String line = "<line x1=\"" + s.getX() +"\" y1=\"" + s.getY() +"\" x2=\"" + e.getX() + "\" y2=\"" + e.getY() +"\" style=\"stroke:rgb(0,0,255);stroke-width:1\" />";
        lines.add(line);

    }

    @Override
    public void fillPolygon(Point[] points) {
        String pointStr = String.join(" ", Arrays.stream(points).map( p -> (p.getX() + "," + p.getY())).collect(Collectors.toList()));
        String line = "<polygon points=\"" + pointStr + "\" style=\"fill:blue;stroke:red;stroke-width:1\" /> ";
        lines.add(line);
    }
}
