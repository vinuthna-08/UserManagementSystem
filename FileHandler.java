package com.usermanagement;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    public static void saveToFile(ArrayList<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User u : users) {
                writer.write(u.getId() + "," + u.getName() + "," + 
                             u.getEmail() + "," + u.getPhone());
                writer.newLine();
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static ArrayList<User> loadFromFile() {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    users.add(new RegularUser(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            data[3]
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("No saved file found. Starting fresh.");
        }
        return users;
    }
}