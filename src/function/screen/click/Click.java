package function.screen.click;

import function.screen.AbstractScreenOperation;
import function.state.FiniteStateMachine;
import function.system.Message;
import function.state.State;
import interpolation.Point;

import java.util.List;

public class Click extends AbstractScreenOperation {
    private Point lastPoint;
    public Click(){
        FiniteStateMachine finiteStateMachine = new FiniteStateMachine(ClickStates.START);
        super.setFiniteStateMachine(finiteStateMachine);
    }
    @Override
    protected Message operate(List<Point> points) {
        Message mss = null;
        if(getFiniteStateMachine().getCurrentState() == ClickStates.CLICK){
            mss = new Message(points, "Click State Location: " + lastPoint);
            setChanged();
            notifyObservers(lastPoint);
        }else if(getFiniteStateMachine().getCurrentState() == ClickStates.ONCE){
            lastPoint = points.get(0);
        }
        return mss;
    }

    static class ClickStates {
        /**
         * 起点
         */
        private static final State START = new State(0);
        /**
         * 空白帧
         */
        private static final State BLANK = new State(1);
        /**
         * 空白帧之后出现一个单点帧
         */
        private static final State ONCE = new State(2);
        /**
         * 空白帧、单点帧之后，出现空白帧进入单击状态
         */
        private static final State CLICK = new State(3);

        /**
         * 对各种状态进行状态转移初始化
         */
        static {
            subscribe(START, BLANK, START, START);
            subscribe(BLANK, BLANK, ONCE, START);
            subscribe(ONCE, CLICK, START, START);
            subscribe(CLICK, BLANK, ONCE, START);
        }
    }



}
