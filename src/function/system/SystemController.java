package function.system;

import function.screen.AbstractScreenOperation;
import interpolation.Point;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SystemController implements ISystemController {
    private Logger logger = Logger.getLogger(SystemController.class.getName());
    private List<AbstractScreenOperation> screenOperations;

    public SystemController() {
        this.screenOperations = new ArrayList<>();
    }

    @Override
    public void addScreenOperation(AbstractScreenOperation screenOperation) {
        screenOperations.add(screenOperation);
    }

    @Override
    public List<AbstractScreenOperation> getScreenOperation() {
        return  screenOperations;
    }

    @Override
    public List<Message> processNewFragment(List<Point> points) {
        Message message;
        List<Message> messages = new ArrayList<>();
        for (AbstractScreenOperation operation : screenOperations) {
            int code = getSystemStateCode(points.size());
            logger.debug("Point num: " + points.size() + "code: " + code);
            message = operation.nextState(code, points);
            if (message != null) {
                messages.add(message);
            }
        }
        return messages;
    }

    private int getSystemStateCode(int pointsNum) {
        // 其他点数当成空处理
        switch (pointsNum) {
            case 1:
                return SystemStateCodeEnum.ONE_POINT.getStateCode();
            case 2:
                return SystemStateCodeEnum.TWO_POINT.getStateCode();
            default:
                return SystemStateCodeEnum.ZERO_POINT.getStateCode();
        }
    }

}
