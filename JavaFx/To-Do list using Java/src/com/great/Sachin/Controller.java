package com.great.Sachin;

import com.great.Sachin.dataModel.ToDoItems;
import com.great.Sachin.dataModel.TodoData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    @FXML
    private List<ToDoItems>items;
    @FXML
    private ListView<ToDoItems> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadLineId;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;

    private FilteredList<ToDoItems> filteredList;


    public void initialize() {
        listContextMenu=new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        MenuItem editMenuItem = new MenuItem("Edit");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToDoItems items  = todoListView.getSelectionModel().getSelectedItem();
                deleteItem(items);
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);
        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ToDoItems items = todoListView.getSelectionModel().getSelectedItem();
                editItem(items);
            }
        });
        listContextMenu.getItems().addAll(editMenuItem);
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

        filteredList =new FilteredList<ToDoItems>(TodoData.getInstance().getToDoItems(),
                new Predicate<ToDoItems>(){
            @Override
            public boolean test(ToDoItems items) {
                return true;
            }
        });

        SortedList<ToDoItems> sort = new SortedList<ToDoItems>(filteredList,
                new Comparator<ToDoItems>() {
            @Override
            public int compare(ToDoItems o1, ToDoItems o2) {
                return o1.getDeadLine().compareTo(o2.getDeadLine());
            }
        });

       //todoListView.setItems((TodoData.getInstance().getToDoItems()));
        todoListView.setItems(sort);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        //Adding color property
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

                cell.emptyProperty().addListener(
                        (obs,wasEmpty,isNowEmty) -> {
                            if (isNowEmty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });
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
    public void editItem(ToDoItems item){//this is to edit
        ToDoItems items = todoListView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog1 = new Dialog<>();
        dialog1.initOwner(mainBorderPane.getScene().getWindow());
        dialog1.setTitle("Edit "+item.getShortDescription());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialogue.fxml"));

        try{
            dialog1.getDialogPane().setContent(fxmlLoader.load());

        }catch (IOException e){
            System.out.println("Not present");
            e.printStackTrace();
            return;
        }

        dialog1.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog1.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DialogueController contactController = fxmlLoader.getController();
        contactController.editContact(items);

        Optional<ButtonType> result = dialog1.showAndWait();
        if(result.isPresent()&& result.get()==ButtonType.OK){
            DialogueController controller = fxmlLoader.getController();
            controller.processResults();
        }

        if(result.isPresent() && result.get()==ButtonType.OK){
            TodoData.getInstance().deleteMenuItem(item);
        }

        //this is to update in main text location
    }

    @FXML
    public void handleClickListView() {
        ToDoItems items = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(items.getDetails());
        deadLineId.setText(items.getDeadLine().toString());

    }

    public void deleteItem(ToDoItems items){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete To-Do Item");
        alert.setHeaderText("Delete Item "+items.getShortDescription());
        alert.setContentText("Are you sure want to delete :\n" +
                "Press OK to confirm");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get()==ButtonType.OK){
            TodoData.getInstance().deleteMenuItem(items);
        }
    }
    public void handleFilterButton(){
        if(filterToggleButton.isSelected()){
            filteredList.setPredicate(new Predicate<ToDoItems>() {
                @Override
                public boolean test(ToDoItems items) {
                    return (items.getDeadLine().equals(LocalDate.now()));
                }
            });

        }else{
            filteredList.setPredicate(new Predicate<ToDoItems>() {
                @Override
                public boolean test(ToDoItems items) {
                    return true;
                }
            });
       }
    }
    public void ExitButton(){
        Platform.exit();
    }
}
