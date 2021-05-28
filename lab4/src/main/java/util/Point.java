package util;


public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point translate(Point dp){
        return new Point(this.x + dp.getX(), this.y + dp.getY());
    }

    public Point difference(Point dp){
        return new Point(this.x - dp.getX(), this.y - dp.getY());
    }
}
