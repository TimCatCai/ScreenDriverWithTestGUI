package gui;

import api.GuiSystem;
import function.system.SystemServer;
import interpolation.Point;
import org.apache.log4j.Logger;

import java.util.Observable;

public class OneProgram {
    private Logger logger = Logger.getLogger(OneProgram.class.getName());
    public void button(){
        GuiSystem.addListener(new ClickListener() {
            @Override
            public void onclick() {
                logger.error("Click");
            }

            @Override
            public void update(Observable o, Object arg) {
                Point point = (Point)arg;
                logger.error(point);
                onclick();
            }
        });
    }
}
