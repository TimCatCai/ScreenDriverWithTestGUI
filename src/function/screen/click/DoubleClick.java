package function.screen.click;

import function.screen.AbstractScreenOperation;
import function.state.FiniteStateMachine;
import function.system.Message;
import function.state.State;
import interpolation.Point;
import utils.PointUtils;

import java.util.List;

public class DoubleClick extends AbstractScreenOperation {
    /**
     * 双击第一帧的解析坐标
     */
    private Point oncePoint;
    /**
     * 双击第二帧的解析坐标
     */
    private Point secondPoint;
    /**
     * 连续两帧坐标的有效距离，即超过这个距离则视为非双击。
     */
    private static double VALID_RANGE_SQUARE = 1;
    public DoubleClick(){
        FiniteStateMachine finiteStateMachine = new FiniteStateMachine(DoubleClickStates.START);
        super.setFiniteStateMachine(finiteStateMachine);
    }

    @Override
    protected Message operate(List<Point> points) {
        Message mss = null;
        State currentState = getFiniteStateMachine().getCurrentState();
        if(currentState == DoubleClickStates.DOUBLE_CLICK){
            if(PointUtils.twoPointSquareDistance(oncePoint, secondPoint) <= VALID_RANGE_SQUARE){
                double xTwoPointMedia = (oncePoint.getX() + secondPoint.getX()) / 2;
                double yTwoPointMedia = (oncePoint.getY() + secondPoint.getY()) / 2;
                mss = new Message(points, String.format("DoubleClick State Location: (%.2f,%.2f)", xTwoPointMedia, yTwoPointMedia));
            }
        }else if(currentState == DoubleClickStates.ONCE){
            oncePoint = points.get(0);
        }else if(currentState == DoubleClickStates.SECOND){
            secondPoint = points.get(0);
        }
        return mss;
    }

    static class DoubleClickStates {
        /**
         * 起始状态
         */
        private static final State START = new State(0);
        private static final State BLANK = new State(1);
        private static final State ONCE = new State(2);
        private static final State SECOND = new State(3);
        private static final State DOUBLE_CLICK = new State(4);
        static {
            subscribe(START, BLANK, START, START);
            subscribe(BLANK, BLANK, ONCE, START);
            subscribe(ONCE, BLANK, SECOND, START);
            subscribe(SECOND, DOUBLE_CLICK, START, START);
            subscribe(DOUBLE_CLICK, BLANK, ONCE, START);
        }
    }
}
