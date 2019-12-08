package function.system;

public enum SystemStateCodeEnum {
    /**
     * 屏幕静止状态 ，即所有的x,y通道都是0
     */
    ZERO_POINT(0),
    /**
     * 屏幕单点触控，即x,y通道都只有一个簇
     */
    ONE_POINT(1),
    /**
     * 屏幕双点触控，即x,y通道都有两个簇
     */
    TWO_POINT(2);
    private int stateCode;
    SystemStateCodeEnum(int stateCode){
        this.stateCode = stateCode;
    }

    public int getStateCode(){
        return this.stateCode;
    }
}
