package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class Controller {
    @FXML
    private TextField textField;
    @FXML
    private Button HelloButton;
    @FXML
    private Button ByeButton;

    @FXML
    public void initialize(){
        HelloButton.setDisable(true);
        ByeButton.setDisable(true);
    }
    @FXML
    public void onButtonClicked(ActionEvent e){
        if(e.getSource().equals(HelloButton)){
            System.out.println("Hello "+ textField.getText());
        }else if(e.getSource().equals(ByeButton)){
            System.out.println("Bye "+textField.getText());
        }
    }

    @FXML
    public void handleKeyRelesed(){
        String text = textField.getText();
        boolean disableButtons = text.isEmpty()|| text.trim().isEmpty();
        HelloButton.setDisable(disableButtons);
        ByeButton.setDisable(disableButtons);
    }
}
