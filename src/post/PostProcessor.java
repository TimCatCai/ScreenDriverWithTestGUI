package post;

import interpolation.Clustering;
import interpolation.IInterpolation;
import interpolation.Point;
import pre.Layer;

import java.util.List;

public class PostProcessor implements IPostProcessor {
    private IInterpolation interpolation;
    private int noiseDelta;
    public PostProcessor(){
        interpolation = new Clustering();
        noiseDelta = 25;
    }
    @Override
    public List<Point> process(Layer layer) {
        return interpolation.getPoints(layer,noiseDelta);
    }
}
