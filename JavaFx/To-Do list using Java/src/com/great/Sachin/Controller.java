package com.great.Sachin;

import com.great.Sachin.dataModel.ToDoItems;
import com.great.Sachin.dataModel.TodoData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    private List<ToDoItems> itemsTodo;
    @FXML
    private ListView<ToDoItems> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadLineId;
    @FXML
    private BorderPane mainBorderPane;


    public void initialize() {


        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItems>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItems> observable, ToDoItems oldValue, ToDoItems newValue) {
                if (newValue != null) {
                    ToDoItems item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d,YYYY");
                    deadLineId.setText(df.format(item.getDeadLine()));
                }
            }
        });

        todoListView.setItems((TodoData.getInstance().getToDoItems()));
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        todoListView.setCellFactory(new Callback<ListView<ToDoItems>, ListCell<ToDoItems>>() {
            @Override
            public ListCell<ToDoItems> call(ListView<ToDoItems> param) {
                ListCell<ToDoItems> cell = new ListCell<ToDoItems>() {

                    @Override
                    protected void updateItem(ToDoItems items, boolean empty) {
                        super.updateItem(items, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(items.getShortDescription());
                            if(items.getDeadLine().isBefore(LocalDate.now())){
                                setTextFill(Color.RED);
                            }
                            else if((items.getDeadLine().equals(LocalDate.now().plusDays(1)))){
                                setTextFill(Color.BLUE);
                            }
                        }

                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void showNewItemDialogue(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Enter Your Details");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialogue.fxml"));

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



    @FXML
    public void handleClickListView() {
        ToDoItems items = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(items.getDetails());
        deadLineId.setText(items.getDeadLine().toString());

    }

}
