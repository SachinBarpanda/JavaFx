package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    public Label label;

    @FXML
    public void handleAction(){
         label.setText("This is me ,just do it!");
    }
}
