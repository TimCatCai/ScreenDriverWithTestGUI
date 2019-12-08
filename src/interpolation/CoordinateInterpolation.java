package interpolation;

import org.apache.log4j.Logger;

import java.util.List;

public class CoordinateInterpolation{
    private static Logger logger = Logger.getLogger(CoordinateInterpolation.class.getName());
    public static double accuracyAxi(List<Integer> capacitanceArray, int start, int end){
        // 电容值的总和
        double totalCapacitance = 0;
        // 坐标和电容值乘积之和
        double productOfCoordinate = 0;
        for(int i = start; i <= end; i++){
            totalCapacitance += capacitanceArray.get(i);
            productOfCoordinate += i * capacitanceArray.get(i);
        }
        logger.debug(String.format("totalCapacitance: %f productOfCoordinate: %f", totalCapacitance,  productOfCoordinate));
        double result = 0;
        // 如果电容值总和为0，返回的是0
        if(totalCapacitance != 0){
            result = productOfCoordinate / totalCapacitance;
        }
        return result;
    }
}
