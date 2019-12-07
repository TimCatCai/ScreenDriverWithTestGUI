package function.system;

import interpolation.Point;

import java.util.List;

public class Message {
    private List<Point> points;
    private String mss;

    public Message(List<Point> points, String mss) {
        this.points = points;
        this.mss = mss;
    }

    @Override
    public String toString() {
        return  mss;
    }
}
