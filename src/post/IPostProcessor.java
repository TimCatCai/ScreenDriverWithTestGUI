package post;

import interpolation.Point;
import pre.Layer;

import java.util.List;

public interface IPostProcessor {
    List<Point> process(Layer layer);
}
