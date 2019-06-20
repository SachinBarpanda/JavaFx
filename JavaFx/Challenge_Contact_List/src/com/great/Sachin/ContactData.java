package com.great.Sachin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class ContactData {
    private static ContactData instance = new ContactData();
    private static String filename = "Contact_List.txt";

    private ObservableList<Contact> contacts;
    private DateTimeFormatter formatter;

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public void addToDoItems(Contact items) {
        contacts.add(items);
    }

    public static ContactData getInstance() {
        return instance;
    }

    public void loadTodoItems() throws IOException {

        contacts = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];
                long date = Long.parseLong(dateString);
                String note = itemPieces[3];
                Contact todoItem = new Contact(shortDescription,details,date,note);
                contacts.add(todoItem);
            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeToDoItems() throws IOException {

        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<Contact> iter = contacts.iterator();
            while (iter.hasNext()) {
                Contact item = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s",
                        item.getFirstName(),
                        item.getLastName(),
                        item.getPhoneNumber(),
                        item.getNotes()));
                bw.newLine();
            }

        } finally {
            if (bw != null) {
                bw.close();
            }
        }

    }
}
