package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Book The Hotel Room");
        primaryStage.setScene(new Scene(root, 700, 490));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void stop() throws Exception {
        try {
            LoadData.getInstance().storeFiles();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void init() throws Exception {
        try {
            LoadData.getInstance().loadFiles();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

