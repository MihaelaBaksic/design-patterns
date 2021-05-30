package util;

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        points[2] = new Point(x + width, y + height);
        points[3] = new Point(x, y + height);


        return points;
    }

    public Rectangle union(Rectangle o){
        int x_ = Math.min(this.x, o.getX());
        int y_ = Math.min(this.y, o.getY());

        int width_ = Math.max((this.x - x_ + this.width), (o.x - x_ + o.width));
        int height_ = Math.max((this.y - y_ + this.height), (o.y - y_ + o.height));

        return new Rectangle(x_, y_, width_, height_);
    }
}
