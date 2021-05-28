package util;

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        // ...
    };

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point[] getPointsOfRectangle(){
        Point[] points = new Point[4];
        points[0] = new Point(x, y);
        points[1] = new Point(x + width, y);
        points[2] = new Point(x, y + height);
        points[3] = new Point(x + width, y + height);

        return points;
    }
}
