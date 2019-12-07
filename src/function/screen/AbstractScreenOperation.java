package function.screen;

import function.system.Message;
import function.state.FiniteStateMachine;
import function.state.State;
import function.system.SystemStateCodeEnum;
import interpolation.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public abstract class AbstractScreenOperation extends Observable {
    private FiniteStateMachine finiteStateMachine;
    public FiniteStateMachine getFiniteStateMachine() {
        return finiteStateMachine;
    }

    public void setFiniteStateMachine(FiniteStateMachine finiteStateMachine) {
        this.finiteStateMachine = finiteStateMachine;
    }

    public Message nextState(int statusCode, List<Point> points){
        finiteStateMachine.newSignal(statusCode);
        return operate(points);
    }

    protected abstract Message operate(List<Point> points);

    protected static void subscribe(State target, State zeroPoint, State onePoint, State towPoint){
        Map<Integer, State> changeMap = new HashMap<>(3);
        changeMap.put(SystemStateCodeEnum.ZERO_POINT.getStateCode(), zeroPoint);
        changeMap.put(SystemStateCodeEnum.ONE_POINT.getStateCode(), onePoint);
        changeMap.put(SystemStateCodeEnum.TWO_POINT.getStateCode(), towPoint);
        target.setStateChangeMap(changeMap);
    }
}
