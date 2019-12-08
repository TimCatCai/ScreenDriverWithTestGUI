package pre;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Integer> xAxis;
    private List<Integer> yAxis;
    public Layer(){
        // 初始化容量是8，但并不固定，可以自动扩容
        xAxis = new ArrayList<>(8);
        yAxis = new ArrayList<>(8);
    }

    public Layer(List<Integer> xAxis, List<Integer>yAxis){
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
    public List<Integer> getxAxis() {
        return xAxis;
    }

    public List<Integer> getyAxis() {
        return yAxis;
    }

    public void setxAxis(List<Integer> xAxis) {
        this.xAxis = xAxis;
    }

    public void setyAxis(List<Integer> yAxis) {
        this.yAxis = yAxis;
    }

    @Override
    public String toString() {
        return "Layer{\n" +
                "xAxis=" + xAxis +
                "\n yAxis=" + yAxis +
                "}";
    }
}
