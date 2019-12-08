package test_gui;

import function.system.ISystemServer;
import function.system.Message;
import function.system.SystemServer;
import interpolation.Point;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import pre.Layer;
import test.GenerateTestData;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class Controller{
    private Logger logger = Logger.getLogger(Controller.class.getName());
    @FXML
    Button button;
    @FXML
    GridPane root;
    @FXML
    Circle accuracyPointCircle1;
    @FXML
    Circle accuracyPointCircle2;
    @FXML
    Text stateText;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    Text originData;

    ISystemServer systemServer = SystemServer.newInstance();
    int count = 0;
    private Layer lastLayer = GenerateTestData.generateOnePoint(10,10);

    private int boundaryi = 0;
    private int getBoundaryj = 0;
    @FXML
    protected void click() throws Exception {
        ObservableList<Node> rootNodes = root.getChildren();

        int xAxiNumber = 10;
        int yAxiNumber = 10;
        int noiseDelta = systemServer.getNoiseDelta();
        Layer layer =  generateRandomTest(xAxiNumber,yAxiNumber,noiseDelta, choiceBox.getValue());
        originData.setText("原始通道数据：\n" + layer.toString());

        List<Message> messages = systemServer.newLayer(layer);
        stateText.setText("状态：无");
        for (Message message : messages) {
            logger.debug(message.toString());
            stateText.setText("状态：\n" + message.toString());
        }
        paintAccordingToCoordinate(layer, xAxiNumber, yAxiNumber, rootNodes);

        setAccuracyPointView();
    }

    private void setCirclePosition(Circle accuracyPointCircle, Point point) {
        GridPane.setColumnIndex(accuracyPointCircle, 0);
        GridPane.setRowIndex(accuracyPointCircle, 1 + (int) point.getY());
        accuracyPointCircle.setTranslateX(90 * point.getX() - 10 + 45);
        accuracyPointCircle.setTranslateY(90 * (point.getY() - (int) point.getY()) - 10 + 45);
    }

    private void paintAccordingToCoordinate(Layer layer, int xAxiNumber, int yAxiNumber, ObservableList<Node> rootNodes) {
        Integer[] x = layer.getxAxis().toArray(new Integer[0]);
        Integer[] y = layer.getyAxis().toArray(new Integer[0]);

        String[] colors = {"#ECF5FF", "#ECFFFF", "#D9FFFF", "#CAFFFF", "#BBFFFF", "#A6FFFF", "#80FFFF", "#4DFFFF",
                "#00FFFF", "#00E3E3", "#00CACA", "#00AEAE", "#009393", "#007979", "#005757", "#003E3E"};

        GridPane textGridPane;
        ObservableList<Node> textsGridPane;
        for (int i = 0; i < yAxiNumber; i++) {
            int indexLen = 400 / colors.length;
            textsGridPane = ((GridPane) rootNodes.get(i)).getChildren();
            for (int j = 0; j < xAxiNumber; j++) {
                textGridPane = ((GridPane) textsGridPane.get(j));
                int colorIndex = (x[j] + y[i]) / indexLen;
                String colorString = colors[colorIndex];
                BackgroundFill myBF = new BackgroundFill(Paint.valueOf(colorString), null, null);
                textGridPane.setBackground(new Background(myBF));
                ((Text) textGridPane.getChildren().get(0)).setText(String.format("(%d, %d)", x[j], y[i]));
            }
        }
    }

    private void setAccuracyPointView() {
        List<Point> points = systemServer.getCurrentAccuracyPointList();
        Point point;
        logger.debug(points);
        if (points.size() == 1) {
            point = points.get(0);
            accuracyPointCircle1.setRadius(10);
            accuracyPointCircle2.setRadius(0);
            setCirclePosition(accuracyPointCircle1, point);
        } else if (points.size() == 2) {
            point = points.get(0);
            accuracyPointCircle1.setRadius(10);
            setCirclePosition(accuracyPointCircle1, point);

            point = points.get(1);
            accuracyPointCircle2.setRadius(10);
            setCirclePosition(accuracyPointCircle2, point);
        }else{
            accuracyPointCircle1.setRadius(0);
            accuracyPointCircle2.setRadius(0);
        }

    }

    private Layer generateRandomTest(int xAxiNumber, int yAxiNumber, int noiseDelta, String type){
        switch (type){
            case "产生单击测试数据":
                return oneClick(xAxiNumber, yAxiNumber,noiseDelta);
            case "产生双击测试数据":
                return doubleClick(xAxiNumber, yAxiNumber,noiseDelta);
            case "产生长按测试数据":
                return longClick(xAxiNumber, yAxiNumber, noiseDelta);
            case "产生zoom测试数据":
                return zoomTest(xAxiNumber,yAxiNumber,noiseDelta);
            case "产生随机测试数据":
                return randomTest(xAxiNumber,yAxiNumber,noiseDelta);
            default:
                return GenerateTestData.generateOnePoint(xAxiNumber, yAxiNumber);
        }
    }

    private Layer randomTest(int xAxiNumber, int yAxiNumber, int noiseDelta){
        int kind = RandomUtils.nextInt(0,3);
        switch (kind){
            case 0:
                return GenerateTestData.generateBlankPoints(xAxiNumber, yAxiNumber, noiseDelta);
            case 2:
                return GenerateTestData.generateTowPoint(xAxiNumber,yAxiNumber);
            default:
                return GenerateTestData.generateOnePoint(xAxiNumber, yAxiNumber);
        }

    }

    private Layer oneClick(int xAxiNumber, int yAxiNumber, int noiseDelta){
        Layer layer;
        if(count % 2 == 0){
            layer = GenerateTestData.generateOnePoint(xAxiNumber,yAxiNumber);
        }else {
            layer = GenerateTestData.generateBlankPoints(xAxiNumber,yAxiNumber,noiseDelta);
        }
        if(RandomUtils.nextInt(0, 10) < 4){
           layer = randomTest(xAxiNumber,yAxiNumber,noiseDelta);
        }
        count ++;
        return layer;
    }

    private Layer doubleClick(int xAxiNumber, int yAxiNumber, int noiseDelta){
        Layer layer;
        if(count % 3 == 0){
            layer = GenerateTestData.generateBlankPoints(xAxiNumber,yAxiNumber,noiseDelta);
        }else{
            layer = lastLayer;
        }
        if(RandomUtils.nextInt(0, 10) < 3){
            layer = randomTest(xAxiNumber,yAxiNumber,noiseDelta);
            lastLayer = GenerateTestData.generateOnePoint(xAxiNumber,yAxiNumber);
        }
        count ++;
        return layer;
    }

    private Layer longClick(int xAxiNumber, int yAxiNumber, int noiseDelta){
        Layer layer;
        if(count % 6 == 0){
            layer = GenerateTestData.generateBlankPoints(xAxiNumber,yAxiNumber,noiseDelta);
        }else{
            layer = lastLayer;
        }
        if(RandomUtils.nextInt(0, 10) < 3){
            layer = randomTest(xAxiNumber,yAxiNumber,noiseDelta);
            lastLayer = GenerateTestData.generateOnePoint(xAxiNumber,yAxiNumber);
        }
        count ++;
        return layer;
    }

    private Layer zoomTest(int xAxiNumber, int yAxiNumber, int noiseDelta){
        Layer layer;
        if(count % 3 == 0){
            layer = GenerateTestData.generateOnePoint(xAxiNumber,yAxiNumber);
        }else {
            layer = GenerateTestData.generateTowPoint(xAxiNumber,yAxiNumber);
        }
        if(RandomUtils.nextInt(0, 10) < 4){
            layer = randomTest(xAxiNumber,yAxiNumber,noiseDelta);
        }
        count ++;
        return layer;
    }
    private Layer boundaryTest(int xAxiNumber, int yAxiNumber){
        int [] x = new int[xAxiNumber];
        int [] y = new int[yAxiNumber];
        y[yAxiNumber - 1] = 180;
        if(boundaryi < xAxiNumber){

            x[boundaryi] = 180;
            boundaryi++;
        }
        List<Integer> xl = new ArrayList<>();
        List<Integer> yl = new ArrayList<>();
        for(int i = 0; i < xAxiNumber;i++){
            xl.add(x[i]);
            yl.add(y[i]);
        }
        return new Layer(xl,yl);
    }
}
