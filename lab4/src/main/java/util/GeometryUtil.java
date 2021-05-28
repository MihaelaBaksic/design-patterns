package util;


public class GeometryUtil {

    public static double distanceFromPoint(Point point1, Point point2){
        return Math.sqrt(Math.pow(point1.getX()-point2.getX(), 2) + Math.pow(point1.getY() - point1.getY(), 2));
    }

    public static double distanceFromLineSegment(Point start, Point end, Point p){

        double a = p.getX() - start.getX();
        double b = p.getY() - start.getY();

        double c = end.getX() - start.getX();
        double d = end.getY() - start.getY();

        double e = -d;
        double f = c;

        double dot = a*e + b*f;
        double len_sq = e*e + f*f;

        return Math.abs(dot)/Math.sqrt(len_sq);
    }
}
