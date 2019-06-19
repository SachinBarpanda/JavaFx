package com.great.Sachin;

import com.great.Sachin.dataModel.ToDoItems;
import com.great.Sachin.dataModel.TodoData;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogueController {
    @FXML
    private TextField ShortDescription;
    @FXML
    private TextArea DetailsPane;
    @FXML
    private DatePicker DeadLinePicker;

    public void processResults(){
        String shortDescription= ShortDescription.getText().trim();
        String details = DetailsPane.getText().trim();
        LocalDate DeadLine = DeadLinePicker.getValue();

        TodoData.getInstance().addToDoItems(new ToDoItems(shortDescription,details,DeadLine));

    }
}
