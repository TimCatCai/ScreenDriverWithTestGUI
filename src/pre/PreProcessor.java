package pre;

import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public class PreProcessor implements IPreProcessor {
    private Logger logger = Logger.getLogger(PreProcessor.class.getName());
    /**
     * 过滤所有小于等于noise delta的值
     * @param layer
     * @param noiseDelta
     */
    @Override
    public void preprocess(Layer layer, int noiseDelta) {

        StringBuilder info = new StringBuilder();
        info.append("x: [");
        for(Integer a: layer.getxAxis()){
            info.append(a);
            info.append(",");
        }
        info.append("] y: [");

        for (Integer a: layer.getyAxis()){
            info.append(a);
            info.append(",");
        }
        info.append("]\n");

        List<Integer> xAxi = layer.getxAxis();
        for(int i = 0; i < xAxi.size(); i++){
            if(xAxi.get(i) <= noiseDelta){
                xAxi.set(i, 0);
            }
        }

        List<Integer> yAxi = layer.getyAxis();
        for(int i = 0; i < yAxi.size(); i++){
            if(yAxi.get(i) <= noiseDelta){
                yAxi.set(i, 0);
            }
        }

        info.append("x: [");
        for(Integer a: layer.getxAxis()){
            info.append(a);
            info.append(",");
        }
        info.append("] y: [");

        for (Integer a: layer.getyAxis()){
            info.append(a);
            info.append(",");
        }
        info.append("]\n");
        logger.debug(info.toString());
    }
}
