package com.great.Sachin;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogueController {
    @FXML
    public TextField firstNamePane;
    @FXML
    private TextArea lastNamePane;
    @FXML
    private TextField PhoneNumberPane;
    @FXML
    private TextField NotePane;

    public void processResults(){
        String shortDescription= firstNamePane.getText().trim();
        String details = lastNamePane.getText().trim();
        Long DeadLine = Long.parseLong(PhoneNumberPane.getText().trim());
        String Note = NotePane.getText().trim();

        ContactData.getInstance().addToDoItems(new Contact(shortDescription,details,DeadLine,Note));
    }
}
