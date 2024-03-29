package function.system;

import function.screen.AbstractScreenOperation;
import function.screen.click.Click;
import function.screen.click.DoubleClick;
import function.screen.click.LongClick;
import function.screen.zoom.Zoom;
import gui.ClickListener;
import interpolation.Point;
import org.apache.log4j.Logger;
import post.IPostProcessor;
import post.PostProcessor;
import pre.Layer;

import java.util.List;
import java.util.Observer;

public class SystemServer implements ISystemServer {
    private Logger logger = Logger.getLogger(SystemServer.class.getName());
    private ISystemController systemController;
    private IPostProcessor postProcess;
    private int noiseDelta;
    private List<Point> currentAccuracyPointList;
    private static SystemServer systemServer;
    private SystemServer(){
        this.systemController = new SystemController();
        systemController.addScreenOperation(new Zoom());
        systemController.addScreenOperation(new Click());
        systemController.addScreenOperation(new DoubleClick());
        systemController.addScreenOperation(new LongClick());
        this.postProcess = new PostProcessor();
        this.noiseDelta = 25;
    }

     public static SystemServer newInstance(){
        if(systemServer == null){
            systemServer = new SystemServer();
        }
        return systemServer;
    }
    @Override
    public List<Message> newLayer(Layer layer){
        currentAccuracyPointList = postProcess.process(layer);
        return systemController.processNewFragment(currentAccuracyPointList);
    }

    @Override
    public List<Point> getCurrentAccuracyPointList() {
        return currentAccuracyPointList;
    }

    @Override
    public int getNoiseDelta() {
        return noiseDelta;
    }

    @Override
    public void addListener(Observer listener) {
        if(listener instanceof ClickListener){
            logger.info("add listener");
            for(AbstractScreenOperation operation: systemController.getScreenOperation()){
                if(operation instanceof Click){
                    logger.error("add listener");
                    operation.addObserver(listener);
                    break;
                }
            }
        }
    }
}
