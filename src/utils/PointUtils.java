package utils;

import interpolation.Point;

import java.util.List;

public class PointUtils {

    public static double twoPointSquareDistance(Point a, Point b){
        double x1 = a.getX();
        double x2 = b.getX();
        double temp = x1;
        x1 = Math.max(temp, x2);
        x2 = Math.min(temp, x2);

        double y1 = a.getY();
        double y2 = b.getY();
        temp = y1;
        y1 = Math.max(temp, y2);
        y2 = Math.min(temp, y2);

        return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
    }



}
