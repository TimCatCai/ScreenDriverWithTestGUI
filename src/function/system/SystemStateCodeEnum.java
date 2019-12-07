package function.system;

public enum SystemStateCodeEnum {
    ZERO_POINT(0), ONE_POINT(1), TWO_POINT(2);
    private int stateCode;
    SystemStateCodeEnum(int stateCode){
        this.stateCode = stateCode;
    }

    public int getStateCode(){
        return this.stateCode;
    }
}
