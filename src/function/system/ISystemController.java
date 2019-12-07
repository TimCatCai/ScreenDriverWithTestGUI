package function.system;

import function.screen.AbstractScreenOperation;
import interpolation.Point;

import java.util.List;

public interface ISystemController {
    void addScreenOperation(AbstractScreenOperation screenOperation);
    List<AbstractScreenOperation> getScreenOperation();
    List<Message> processNewFragment(List<Point> points);
}
