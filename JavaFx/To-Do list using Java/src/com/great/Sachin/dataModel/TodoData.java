package com.great.Sachin.dataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class TodoData {
    private static TodoData instance = new TodoData();
    private static String filename = "TodoListItems.txt";

    private ObservableList<ToDoItems> toDoItems;
    private DateTimeFormatter formatter;

    public static TodoData getInstance() {
        return instance;
    }

    public TodoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<ToDoItems> getToDoItems() {
        return toDoItems;
    }

    public void addToDoItems(ToDoItems items) {
        toDoItems.add(items);
    }

    public void loadTodoItems() throws IOException {

        toDoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                ToDoItems todoItem = new ToDoItems(shortDescription, details, date);
                toDoItems.add(todoItem);
            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storetoDoItems() throws IOException {

        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<ToDoItems> iter = toDoItems.iterator();
            while (iter.hasNext()) {
                ToDoItems item = iter.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDescription(),
                        item.getDetails(),
                        item.getDeadLine().format(formatter)));
                bw.newLine();
            }

        } finally {
            if (bw != null) {
                bw.close();
            }
        }

    }
}







