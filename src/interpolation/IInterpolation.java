package interpolation;

import pre.Layer;

import java.util.List;

public interface IInterpolation {
    List<Point> getPoints(Layer layer, int noiseDelta);
}
