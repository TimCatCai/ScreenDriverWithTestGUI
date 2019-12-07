package function.screen.zoom;

import function.screen.AbstractScreenOperation;
import function.state.FiniteStateMachine;
import function.system.Message;
import function.state.State;
import interpolation.Point;
import org.apache.log4j.Logger;
import utils.PointUtils;

import java.util.List;

public class Zoom extends AbstractScreenOperation {
    private Logger logger = Logger.getLogger(Zoom.class.getName());
    private List<Point> lastPointsList;
    private double zoom;

    public Zoom() {
        FiniteStateMachine finiteStateMachine = new FiniteStateMachine(ZoomStates.START);
        super.setFiniteStateMachine(finiteStateMachine);
    }

    @Override
    protected Message operate(List<Point> points) {
        State currentState = getFiniteStateMachine().getCurrentState();
        Message mss = null;
        if (currentState == ZoomStates.ONCE) {
            lastPointsList = points;
        } else if (currentState == ZoomStates.SECOND) {
            double lastPointSquareDistance = PointUtils.twoPointSquareDistance(lastPointsList.get(0), lastPointsList.get(1));
            double currentPointSquareDistance = PointUtils.twoPointSquareDistance(points.get(0), points.get(1));
            logger.debug(String.format("last: %f, current: %f", lastPointSquareDistance, currentPointSquareDistance));
            zoom = Math.sqrt(lastPointSquareDistance / currentPointSquareDistance);
            // 更新当前点集
            lastPointsList = points;
            mss = new Message(points, String.format("last two points: %s%n current two points: %s%n Zoom: state: %s: %.2f", lastPointsList, points,getZoomState(zoom), zoom));
            notifyObservers(mss);
        }
        return mss;
    }

    private String getZoomState(double zoom) {
        if (zoom > 1) {
            return "缩小";
        }

        if (zoom < 1) {
            return "放大";
        }

        return "不变";
    }

    static class ZoomStates {
        private static final State START = new State(0);
        private static final State ONCE = new State(1);
        private static final State SECOND = new State(2);;
        static {
            subscribe(START, START, START, ONCE);
            subscribe(ONCE, START, START, SECOND);
            subscribe(SECOND, START, START, SECOND);
        }
    }
}
