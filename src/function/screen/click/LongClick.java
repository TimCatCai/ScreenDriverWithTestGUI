package function.screen.click;

import function.screen.AbstractScreenOperation;
import function.state.FiniteStateMachine;
import function.system.Message;
import function.state.State;
import interpolation.Point;
import utils.PointUtils;

import java.util.List;

public class LongClick extends AbstractScreenOperation {
    private Point lastPoint;
    private static double VALID_RANGE_SQUARE = 1;
    private int flameCount = 2;
    public LongClick(){
        FiniteStateMachine finiteStateMachine = new FiniteStateMachine(LongClickStates.START);
        super.setFiniteStateMachine(finiteStateMachine);
    }

    @Override
    protected Message operate(List<Point> points) {
        Message mss = null;
        State currentState = getFiniteStateMachine().getCurrentState();
        if(currentState == LongClickStates.LONG_CLICK){
                double xTwoPointMedia = (lastPoint.getX() + points.get(0).getX()) / 2;
                double yTwoPointMedia = (lastPoint.getY() + points.get(0).getY()) / 2;
                flameCount ++;
                mss = new Message(points, String.format("LongClick State Location: (%.2f,%.2f)%n duration: %d flame",
                        xTwoPointMedia, yTwoPointMedia, flameCount));
        }else{
            flameCount = 2;
        }
        if(currentState.getStateCode() >= LongClickStates.ONCE.getStateCode()){
           lastPoint = points.get(0);
        }
        return mss;
    }

    static class LongClickStates {
        private static final State START = new State(0);
        private static final State BLANK = new State(1);
        private static final State ONCE = new State(2);
        private static final State SECOND = new State(3);
        private static final State LONG_CLICK = new State(4);
        static {
            subscribe(START, BLANK, START, START);
            subscribe(BLANK, BLANK, ONCE, START);
            subscribe(ONCE, BLANK, SECOND, START);
            subscribe(SECOND, BLANK, LONG_CLICK, START);
            subscribe(LONG_CLICK, BLANK, LONG_CLICK, START);
        }
    }

    @Override
    public Message nextState(int statusCode, List<Point> points) {
        FiniteStateMachine finiteStateMachine = getFiniteStateMachine();
        State currentState = finiteStateMachine.getCurrentState();
        if(points.size() != 0 && (currentState == LongClickStates.SECOND || currentState == LongClickStates.LONG_CLICK)){
            if(PointUtils.twoPointSquareDistance(points.get(0), lastPoint) <= VALID_RANGE_SQUARE){
                        finiteStateMachine.newSignal(statusCode);
            }else{
                // 如果不在有效范围内，返回初始状态
                finiteStateMachine.setCurrentState(LongClickStates.START);
            }
        }else{
            finiteStateMachine.newSignal(statusCode);
        }
        return operate(points);
    }
}
