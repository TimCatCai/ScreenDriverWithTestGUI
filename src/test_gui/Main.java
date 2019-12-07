package test_gui;

import gui.OneProgram;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Screen Driver");
        primaryStage.setScene(new Scene(root, 900, 990));
        primaryStage.show();

    }


    public static void main(String[] args) {
       // 这里模拟一个系统程序的建立
        OneProgram program = new OneProgram();
        program.button();
        launch(args);
    }
}
