package com.usermanagement;

public class RegularUser extends User {

    public RegularUser(int id, String name, String email, String phone) {
        super(id, name, email, phone);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Regular User");
    }
}