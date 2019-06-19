package com.great.Sachin;

import com.great.Sachin.dataModel.TodoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("To-do List");
        primaryStage.setScene(new Scene(root, 900,500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop()throws IOException {
        try{
            TodoData.getInstance().storetoDoItems();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws IOException{
        try{
            TodoData.getInstance().loadTodoItems();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
