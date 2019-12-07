package interpolation;

import pre.IPreProcessor;
import pre.Layer;

import java.util.ArrayList;
import java.util.List;


public class Clustering implements IInterpolation{
    private IPreProcessor preProcessor;
    public Clustering(IPreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public List<Point> getPoints(Layer layer, int noiseDelta) {
        preProcessor.preprocess(layer, noiseDelta);
        // 判断是否是静止状态, 是则直接返回空的列表
        if(isAllZero(layer)){
            return new ArrayList<>();
        }

        List<Double> xAxiAccuracyPoint = clickPoints(layer.getxAxis());
        List<Double> yAxiAccuracyPoint = clickPoints(layer.getyAxis());
        return combineTwoAxis(xAxiAccuracyPoint, yAxiAccuracyPoint);
    }
    private boolean isAllZero(Layer layer){
        boolean isAllZero = true;
        for(Integer aix: layer.getxAxis()){
            if(aix != 0){
                isAllZero = false;
                break;
            }
        }

        if(isAllZero){
            for(Integer aix: layer.getyAxis()){
                if(aix != 0){
                    isAllZero = false;
                    break;
                }
            }
        }

        return isAllZero;
    }
    /**
     * 只要是极小值即是分割点，获取所有分割点
     * @param capacitanceArray 电容数组
     * @return 所有极小值所在坐标
     */
    private List<Integer> breakPoints(List<Integer> capacitanceArray){
        boolean breakPointLeft = false;
        boolean breakPointRight = false;
        List<Integer> result = new ArrayList<>();
        for(int i = 1; i < capacitanceArray.size(); i++){
            // 如何解决相等问题
            if(capacitanceArray.get(i) - capacitanceArray.get(i - 1) < 0){
                breakPointLeft = true;
            }
            if(breakPointLeft && capacitanceArray.get(i) - capacitanceArray.get(i - 1) > 0){
                breakPointRight = true;
            }
            if(breakPointLeft && breakPointRight){
                // 前一个是分割点
                result.add(i-1);
                breakPointLeft = false;
                breakPointRight = false;
            }
        }
        return result;
    }

    private List<Double> clickPoints(List<Integer> capacitanceArray){
        List<Integer> breakPoints = breakPoints(capacitanceArray);
        // 第一个及最后一个都当成分割点，方便聚簇操作
        breakPoints.add(0, 0);
        breakPoints.add(breakPoints.size(), capacitanceArray.size() - 1);
        List<Double> result = new ArrayList<>();
        double accuracyPoint;
        int start;
        int end;
        for(int i = 1; i < breakPoints.size(); i++){
            start = breakPoints.get(i - 1);
            end = breakPoints.get(i);
            // 这里分割点是算两次的，前一个峰算一次，后一个峰也算一次。
            // 连续几个相等应该取在哪里呢？？
            accuracyPoint = CoordinateInterpolation.accuracyAxi(capacitanceArray, start, end);
            result.add(accuracyPoint);
        }
        return result;
    }

    private List<Point> combineTwoAxis(List<Double> xAxiClickPoints, List<Double> yAxiClickPoints){
        double lastClickPoint = 0;
        List<Double> listAdd = null;
        int numberAdd = 0;
        // x,y电容序列中有一个为空，则返回空值
        if(xAxiClickPoints.size() == 0 || yAxiClickPoints.size() == 0){
            return null;
        }

        if(xAxiClickPoints.size() < yAxiClickPoints.size()){
            lastClickPoint = xAxiClickPoints.get(xAxiClickPoints.size() - 1);
            listAdd = xAxiClickPoints;
            numberAdd = yAxiClickPoints.size() - xAxiClickPoints.size();
        }else if(xAxiClickPoints.size() > yAxiClickPoints.size()){
            lastClickPoint = yAxiClickPoints.get(yAxiClickPoints.size() - 1);
            listAdd = yAxiClickPoints;
            numberAdd = xAxiClickPoints.size() - yAxiClickPoints.size();
        }
        if(listAdd != null){
            for(int i = 0; i < numberAdd; i++){
                listAdd.add(lastClickPoint);
            }
        }
        List<Point> result = new ArrayList<>(xAxiClickPoints.size());
        for(int i = 0; i < xAxiClickPoints.size(); i++){
            result.add(new Point(xAxiClickPoints.get(i), yAxiClickPoints.get(i)));
        }
        return result;
    }
}
