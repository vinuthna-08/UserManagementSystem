package com.usermanagement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserManagementGUI extends Application {

    UserManager manager = new UserManager();
    TextArea outputArea = new TextArea();

    @Override
    public void start(Stage stage) {

        manager.getUsers().addAll(FileHandler.loadFromFile());

        // Input Fields
        TextField idField = new TextField();
        idField.setPromptText("User ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        // Buttons
        Button addBtn = new Button("Add User");
        Button viewBtn = new Button("View All");
        Button searchBtn = new Button("Search by Name");
        Button sortNameBtn = new Button("Sort by Name");
        Button sortIdBtn = new Button("Sort by ID");
        Button deleteBtn = new Button("Delete User");
        Button updateBtn = new Button("Update User");
        Button saveBtn = new Button("Save");
        Button exitBtn = new Button("Exit");

        outputArea.setEditable(false);
        outputArea.setPrefHeight(280);
        outputArea.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");

        // ADD USER
        addBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();

                boolean added = manager.addUser(new RegularUser(id, name, email, phone));
                if (added) {
                    showAlert("Success", "User added successfully!");
                    clearFields(idField, nameField, emailField, phoneField);
                } else {
                    showAlert("Failed", "Could not add user. Check ID or inputs.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid ID.");
            }
        });

        // VIEW ALL
        viewBtn.setOnAction(e -> {
            outputArea.clear();
            if (manager.getUsers().isEmpty()) {
                outputArea.setText("No users found.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (User u : manager.getUsers()) {
                sb.append("ID: ").append(u.getId())
                  .append(" | Name: ").append(u.getName())
                  .append(" | Email: ").append(u.getEmail())
                  .append(" | Phone: ").append(u.getPhone())
                  .append("\n");
            }
            outputArea.setText(sb.toString());
        });

        // SEARCH
        searchBtn.setOnAction(e -> {
            outputArea.clear();
            String keyword = nameField.getText().trim();
            if (keyword.isEmpty()) {
                showAlert("Error", "Enter a name to search.");
                return;
            }
            ArrayList<User> results = manager.searchUserByName(keyword);
            if (results.isEmpty()) {
                outputArea.setText("No matching users found.");
            } else {
                StringBuilder sb = new StringBuilder("Search Results:\n");
                for (User u : results) {
                    sb.append("ID: ").append(u.getId())
                      .append(" | Name: ").append(u.getName())
                      .append(" | Email: ").append(u.getEmail())
                      .append(" | Phone: ").append(u.getPhone())
                      .append("\n");
                }
                outputArea.setText(sb.toString());
            }
        });

        // SORT BY NAME
        sortNameBtn.setOnAction(e -> {
            manager.sortUsersByName();
            viewBtn.fire();
            showAlert("Sorted", "Users sorted by name (A-Z).");
        });

        // SORT BY ID
        sortIdBtn.setOnAction(e -> {
            manager.sortUsersById();
            viewBtn.fire();
            showAlert("Sorted", "Users sorted by ID.");
        });

        // DELETE
        deleteBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                boolean deleted = manager.deleteUser(id);
                if (deleted) {
                    showAlert("Deleted", "User ID " + id + " removed.");
                    clearFields(idField, nameField, emailField, phoneField);
                } else {
                    showAlert("Not Found", "No user with that ID.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Enter a valid User ID.");
            }
        });

        // UPDATE
        updateBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();

                boolean updated = manager.updateUser(id, name, email, phone);
                if (updated) {
                    showAlert("Updated", "User record updated successfully.");
                    clearFields(idField, nameField, emailField, phoneField);
                } else {
                    showAlert("Not Found", "No user with that ID.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid ID.");
            }
        });

        // SAVE
        saveBtn.setOnAction(e -> {
            FileHandler.saveToFile(manager.getUsers());
            showAlert("Saved", "User data saved to users.txt");
        });

        // EXIT
        exitBtn.setOnAction(e -> {
            FileHandler.saveToFile(manager.getUsers());
            stage.close();
        });

        // Layout
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(8);
        inputGrid.add(new Label("User ID:"), 0, 0);
        inputGrid.add(idField, 1, 0);
        inputGrid.add(new Label("Name:"), 0, 1);
        inputGrid.add(nameField, 1, 1);
        inputGrid.add(new Label("Email:"), 0, 2);
        inputGrid.add(emailField, 1, 2);
        inputGrid.add(new Label("Phone:"), 0, 3);
        inputGrid.add(phoneField, 1, 3);

        HBox row1 = new HBox(8, addBtn, updateBtn, deleteBtn);
        HBox row2 = new HBox(8, viewBtn, searchBtn);
        HBox row3 = new HBox(8, sortNameBtn, sortIdBtn);
        HBox row4 = new HBox(8, saveBtn, exitBtn);

        VBox root = new VBox(12,
                new Label("=== User Management System ==="),
                inputGrid,
                row1, row2, row3, row4,
                new Label("Output:"),
                outputArea
        );

        root.setPadding(new Insets(20));
        root.setStyle("-fx-font-size: 13px;");

        Scene scene = new Scene(root, 580, 680);
        stage.setScene(scene);
        stage.setTitle("User Management System");
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    public static void main(String[] args) {
        launch();
    }
}