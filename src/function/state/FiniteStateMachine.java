package function.state;

public class FiniteStateMachine {
    protected State currentState;
    protected final State firstState;

    public FiniteStateMachine(State firstState) {
        this.currentState = firstState;
        this.firstState = firstState;
    }

    public State getCurrentState() {
        return currentState;
    }
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void newSignal(int signalCode){
        try {
            currentState = currentState.nextState(signalCode);
        } catch (NoSuchStateException e) {
            e.printStackTrace();
            // 状态转换出错，让当前状态指向第一个状态
            currentState = firstState;
        }
    }


}
