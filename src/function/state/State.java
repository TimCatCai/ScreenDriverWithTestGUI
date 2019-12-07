package function.state;

import java.util.Map;

public class State {
    /**
     * Integer: signalCodeTriggerToChange
     * State: next State
     */
    private Map<Integer, State> stateChangeMap;
    private int stateCode;

    public State(int stateCode) {
        this.stateCode = stateCode;
    }

    public State nextState(int signalCode) throws NoSuchStateException {
        if(stateChangeMap == null){
            throw new NoSuchStateException("state change map is empty");
        }
        State nextState = stateChangeMap.get(signalCode);

        if(nextState == null){
            throw new NoSuchStateException("state code error");
        }

        return nextState;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateChangeMap(Map<Integer, State> stateChangeMap) {
        this.stateChangeMap = stateChangeMap;
    }
}
