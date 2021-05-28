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

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Rectangle getHotPointRectangle(){
        return new Rectangle(x-10, y-10, 20, 20);
    }
}
