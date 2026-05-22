package com.usermanagement;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UserManager manager = new UserManager();
        manager.getUsers().addAll(FileHandler.loadFromFile());

        try (Scanner sc = new Scanner(System.in)) {

            while (true) {
                try {
                    System.out.println("\n===== User Management System =====");
                    System.out.println("1. Add User");
                    System.out.println("2. View Users");
                    System.out.println("3. Update User");
                    System.out.println("4. Delete User");
                    System.out.println("5. Search User by Name");
                    System.out.println("6. Sort by Name");
                    System.out.println("7. Sort by ID");
                    System.out.println("8. Save & Exit");
                    System.out.print("Enter choice: ");

                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {

                        case 1:
                            System.out.print("ID: ");
                            int id = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Name: ");
                            String name = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            System.out.print("Phone: ");
                            String phone = sc.nextLine();
                            manager.addUser(new RegularUser(id, name, email, phone));
                            break;

                        case 2:
                            manager.viewUsers();
                            break;

                        case 3:
                            System.out.print("Enter ID to update: ");
                            int uid = sc.nextInt();
                            sc.nextLine();
                            System.out.print("New Name: ");
                            String newName = sc.nextLine();
                            System.out.print("New Email: ");
                            String newEmail = sc.nextLine();
                            System.out.print("New Phone: ");
                            String newPhone = sc.nextLine();
                            manager.updateUser(uid, newName, newEmail, newPhone);
                            break;

                        case 4:
                            System.out.print("Enter ID to delete: ");
                            int deleteId = sc.nextInt();
                            manager.deleteUser(deleteId);
                            break;

                        case 5:
                            System.out.print("Enter name keyword: ");
                            String keyword = sc.nextLine();
                            ArrayList<User> results = manager.searchUserByName(keyword);
                            if (results.isEmpty()) {
                                System.out.println("No matching users found.");
                            } else {
                                System.out.println("Search Results:");
                                for (User u : results) {
                                    u.displayInfo();
                                }
                            }
                            break;

                        case 6:
                            manager.sortUsersByName();
                            manager.viewUsers();
                            break;

                        case 7:
                            manager.sortUsersById();
                            manager.viewUsers();
                            break;

                        case 8:
                            FileHandler.saveToFile(manager.getUsers());
                            System.out.println("Exiting... Goodbye!");
                            return;

                        default:
                            System.out.println("Invalid choice. Please enter 1 to 8.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter numbers where required.");
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("Unexpected error: " + e.getMessage());
                }
            }
        }
    }
}