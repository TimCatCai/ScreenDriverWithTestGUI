package test;

import org.apache.commons.lang3.RandomUtils;
import org.omg.PortableInterceptor.INACTIVE;
import pre.Layer;

import java.util.ArrayList;
import java.util.List;

public class GenerateTestData {
    public static void generateClick() {

    }

    public static Layer generateOnePoint(int xAxiNumber, int yAxiNumber) {
        List<Integer> xAxi = oneAxi(xAxiNumber);
        List<Integer> yAxi = oneAxi(yAxiNumber);
        return new Layer(xAxi, yAxi);
    }

    public static Layer generateTowPoint(int xAxiNumber, int yAxiNumber){
        int breakPoint = RandomUtils.nextInt(0, xAxiNumber);

        List<Integer> first = oneAxi(breakPoint);
        List<Integer> second = oneAxi(xAxiNumber - breakPoint);
        List<Integer> xAxi = new ArrayList<>(first.size() + second.size());
        xAxi.addAll(first);
        xAxi.addAll(second);

        first = oneAxi(breakPoint);
        second = oneAxi(yAxiNumber - breakPoint);
        List<Integer> yAxi = new ArrayList<>(first.size() + second.size());
        yAxi.addAll(first);
        yAxi.addAll(second);

        return new Layer(xAxi, yAxi);
    }
    private static List<Integer> oneAxi(int totalNum) {
        int centerPoint = RandomUtils.nextInt(0, totalNum);
        int[] first = {170, 200};
        int[] second = {100, 170};
        int[] third = {20, 100};
        int[] noise = {0, 20};
        int firstNum;
        int secondNum;
        int thirdNum;
        int before;

        List<Integer> axi = new ArrayList<>();
        // 峰值前半部分
        int count = centerPoint;
        firstNum = 1;
        before = first[1];
        for (int i = 0; i < firstNum && count > 0; i++) {
            before = RandomUtils.nextInt(first[0], before);
            axi.add(0, before);
            count--;
        }

        secondNum = RandomUtils.nextInt(0, 2);
        before = second[1];
        for (int i = 0; i < secondNum && count > 0; i++) {
            before = RandomUtils.nextInt(second[0], before);
            axi.add(0, before);
            count--;
        }

        thirdNum = RandomUtils.nextInt(0, 3);
        before = third[1];
        for (int i = 0; i < thirdNum && count > 0; i++) {
            before = RandomUtils.nextInt(third[0], before);
            axi.add(0, before);
            count--;
        }

        for (int i = 0; i < count; i++) {
            before = RandomUtils.nextInt(noise[0], noise[1]);
            axi.add(0, before);
        }

        // 峰值后半部分
        count = totalNum - centerPoint;
        secondNum = RandomUtils.nextInt(0, 2);
        before = second[1];
        for (int i = 0; i < secondNum && count > 0; i++) {
            before = RandomUtils.nextInt(second[0], before);
            axi.add(before);
            count--;
        }

        thirdNum = RandomUtils.nextInt(0, 3);
        before = third[1];
        for (int i = 0; i < thirdNum && count > 0; i++) {
            before = RandomUtils.nextInt(third[0], before);
            axi.add(before);
            count--;
        }

        for (int i = 0; i < count; i++) {
            before = RandomUtils.nextInt(noise[0], noise[1]);
            axi.add(before);
        }

        return axi;
    }

    public static List<Integer> generateBlank(int totalNum, int noiseDelta){
        // 总数量小于等于0，或者噪声峰值小于等于0，返回null;
        if(totalNum <= 0 || noiseDelta <= 0){
            return null;
        }
        List<Integer> result = new ArrayList<>(totalNum);
        for(int i = 0; i < totalNum; i++){
            result.add(RandomUtils.nextInt(0, noiseDelta + 1));
        }
        return result;
    }

    public static Layer generateBlankPoints(int xAxiNumber, int yAxiNumber, int noiseDelta){
        List<Integer> xAxi =  generateBlank(xAxiNumber, noiseDelta);
        List<Integer> yAxi =  generateBlank(yAxiNumber, noiseDelta);
        return new Layer(xAxi, yAxi);
    }

}
