package com.great.Sachin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class Controller {

    private List<Contact>items;

    @FXML
    private TableView tableView;
    @FXML
    private VBox VBoxPane;

    public void initialize(){


        tableView.setItems((ContactData.getInstance().getContacts()));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void showNewItemDialogue(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(VBoxPane.getScene().getWindow());
        dialog.setTitle("Enter Your Details");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());

        }catch (IOException e){
            System.out.println("Couldn't find");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent()&& result.get()==ButtonType.OK){

            DialogueController controller = fxmlLoader.getController();
            controller.processResults();
        }
    }





}
