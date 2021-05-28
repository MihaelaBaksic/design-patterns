package render;

import util.Point;

import java.awt.*;
import java.util.Arrays;

public class G2DRenderer implements Renderer{

    private Graphics2D g2D;

    public G2DRenderer(Graphics2D g2D){
        this.g2D = g2D;
    }

    @Override
    public void drawLine(Point s, Point e) {
        g2D.setColor(Color.blue);
        g2D.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
    }

    @Override
    public void fillPolygon(Point[] points) {
        g2D.setColor(Color.BLUE);
        int[] xPoints = Arrays.stream(points).mapToInt(Point::getX).toArray();
        int[] yPoints = Arrays.stream(points).mapToInt(Point::getY).toArray();
        g2D.fillPolygon(xPoints, yPoints, points.length);

        g2D.setColor(Color.red);
        g2D.drawPolygon(xPoints, yPoints, points.length);

    }
}
