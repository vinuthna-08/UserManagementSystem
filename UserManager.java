package com.usermanagement;

import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> users = new ArrayList<>();

    public boolean addUser(User user) {
        if (user == null) {
            System.out.println("Invalid user.");
            return false;
        }

        if (findUserById(user.getId()) != null) {
            System.out.println("User ID already exists.");
            return false;
        }

        if (user.getName().trim().isEmpty() || 
            user.getEmail().trim().isEmpty() ||
            user.getPhone().trim().isEmpty()) {
            System.out.println("Fields cannot be empty.");
            return false;
        }

        users.add(user);
        System.out.println("User added successfully.");
        return true;
    }

    public void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("No users available.");
            return;
        }
        for (User u : users) {
            u.displayInfo();
            u.displayRole();
            System.out.println("-------------------");
        }
    }

    public User findUserById(int id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> searchUserByName(String keyword) {
        ArrayList<User> result = new ArrayList<>();
        for (User u : users) {
            if (u.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(u);
            }
        }
        return result;
    }

    public void sortUsersByName() {
        int n = users.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (users.get(j).getName()
                        .compareToIgnoreCase(users.get(j + 1).getName()) > 0) {
                    User temp = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, temp);
                }
            }
        }
        System.out.println("Users sorted by name.");
    }

    public void sortUsersById() {
        int n = users.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (users.get(j).getId() > users.get(j + 1).getId()) {
                    User temp = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, temp);
                }
            }
        }
        System.out.println("Users sorted by ID.");
    }

    public boolean deleteUser(int id) {
        User u = findUserById(id);
        if (u != null) {
            users.remove(u);
            System.out.println("User deleted.");
            return true;
        }
        System.out.println("User not found.");
        return false;
    }

    public boolean updateUser(int id, String name, String email, String phone) {
        User u = findUserById(id);
        if (u != null) {
            if (name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
                System.out.println("Fields cannot be empty.");
                return false;
            }
            u.setName(name);
            u.setEmail(email);
            u.setPhone(phone);
            System.out.println("User updated.");
            return true;
        }
        System.out.println("User not found.");
        return false;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}