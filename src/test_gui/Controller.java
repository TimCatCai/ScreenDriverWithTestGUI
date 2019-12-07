package test_gui;

import function.system.ISystemServer;
import function.system.Message;
import function.system.SystemServer;
import interpolation.Point;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import pre.Layer;
import test.GenerateTestData;

import java.util.Arrays;
import java.util.List;


public class Controller {
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

    ISystemServer systemServer = SystemServer.newInstance();

    int count = 0;

    @FXML
    protected void click() throws Exception {
        ObservableList<Node> rootNodes = root.getChildren();

        int xAxiNumber = 10;
        int yAxiNumber = 10;
        int noiseDelta = systemServer.getNoiseDelta();
        Layer layer =  oneClick();
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

    private Layer generateRandomTest(int xAxiNumber, int yAxiNumber, int noiseDelta){
        int kind = RandomUtils.nextInt(0,6);
        switch (kind){
            case 0:
                return GenerateTestData.generateBlankPoints(xAxiNumber, yAxiNumber, noiseDelta);
            case 2:
                return GenerateTestData.generateTowPoint(xAxiNumber, yAxiNumber);
            default:
                return GenerateTestData.generateOnePoint(xAxiNumber, yAxiNumber);
        }
    }

    private Layer oneClick(){
        Layer layer;
        if(count % 3 == 0){
            layer = GenerateTestData.generateOnePoint(10,10);
        }else {
            layer = GenerateTestData.generateBlankPoints(10,10,25);
        }
        count ++;
        return layer;
    }
    private Layer doubleClick(){
        Integer [] x = {1,14,19,4,1,16,8,5,175,133};
        Integer [] y = {16,6,19,98,164,170,105,87,28,10};
        Integer [] xb = {23,18,6,8,16,24,8,11,13,15};
        Integer [] yb = {5,16,2,21,18,9,6,8,23,1};
        Layer layer;
        if(count % 3 == 0){
            layer = new Layer(Arrays.asList(xb), Arrays.asList(yb));
        }else{
            layer = new Layer(Arrays.asList(x), Arrays.asList(y));
        }
        if(RandomUtils.nextInt(5,50) < 10){
            layer = GenerateTestData.generateOnePoint(10,10);
        }
        count ++;
        return layer;
    }

    private Layer longClick(){
        Integer [] x = {1,14,19,4,1,16,8,5,175,133};
        Integer [] y = {16,6,19,98,164,170,105,87,28,10};
        Integer [] xb = {23,18,6,8,16,24,8,11,13,15};
        Integer [] yb = {5,16,2,21,18,9,6,8,23,1};
        Layer layer;
        if(count % 6 == 0){
            layer = new Layer(Arrays.asList(xb), Arrays.asList(yb));
        }else{
            layer = new Layer(Arrays.asList(x), Arrays.asList(y));
        }
        if(RandomUtils.nextInt(5,50) < 10){
            layer = GenerateTestData.generateOnePoint(10,10);
        }
        count ++;
        return layer;
    }
}
