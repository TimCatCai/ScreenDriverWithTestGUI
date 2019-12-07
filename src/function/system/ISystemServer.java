package function.system;

import interpolation.Point;
import pre.Layer;

import java.util.List;
import java.util.Observer;

public interface ISystemServer {
    List<Message> newLayer(Layer layer);
    List<Point> getCurrentAccuracyPointList();
    int getNoiseDelta();
    void addListener(Observer listener);
 }
