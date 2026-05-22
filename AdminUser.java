package com.usermanagement;

public class AdminUser extends User {

    public AdminUser(int id, String name, String email, String phone) {
        super(id, name, email, phone);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Admin");
    }
}