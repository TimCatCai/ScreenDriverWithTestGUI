package api;

import function.system.SystemServer;

import java.util.Observer;

public class GuiSystem {
    public static void addListener(Observer listener){
        SystemServer.newInstance().addListener(listener);
    }
}
