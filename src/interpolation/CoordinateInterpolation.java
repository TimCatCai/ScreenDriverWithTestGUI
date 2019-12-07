package interpolation;

import org.apache.log4j.Logger;

import java.util.List;

public class CoordinateInterpolation{
    private static Logger logger = Logger.getLogger(CoordinateInterpolation.class.getName());
    public static double accuracyAxi(List<Integer> capacitanceArray, int start, int end){
        double totalCapacitance = 0;
        double productOfCoordinate = 0;
        for(int i = start; i <= end; i++){
            totalCapacitance += capacitanceArray.get(i);
            productOfCoordinate += i * capacitanceArray.get(i);
        }
        logger.debug(String.format("totalCapacitance: %f productOfCoordinate: %f", totalCapacitance,  productOfCoordinate));
        double result = 0;
        if(totalCapacitance != 0){
            result = productOfCoordinate / totalCapacitance;
        }
        return result;
    }
}
