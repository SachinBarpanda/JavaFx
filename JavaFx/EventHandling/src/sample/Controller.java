package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.lang.management.PlatformLoggingMXBean;

public class Controller {
    @FXML
    private TextField textField;
    @FXML
    private Button HelloButton;
    @FXML
    private Button ByeButton;
    @FXML
    private CheckBox myCheckBox;
    @FXML
    private Label ourLabel;

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

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(10000);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("BackGround task complete!");
                        }
                    });
                     }catch(InterruptedException event){
                }
            }
        };
        new Thread(task).start();

        if(myCheckBox.isSelected()){
            textField.clear();
            HelloButton.setDisable(true);
            ByeButton.setDisable(true);
        }
    }

    @FXML
    public void handleKeyRelesed(){
        String text = textField.getText();
        boolean disableButtons = text.isEmpty()|| text.trim().isEmpty();
        HelloButton.setDisable(disableButtons);
        ByeButton.setDisable(disableButtons);
    }

    @FXML
    public void handleChange(){
        System.out.println("The checkBox is "+(myCheckBox.isSelected()?"checked":"unchecked"));
    }
}
