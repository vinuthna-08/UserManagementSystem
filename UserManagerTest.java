package com.usermanagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserManagerTest {

    @Test
    void testAddUser() {
        UserManager manager = new UserManager();
        boolean result = manager.addUser(
            new RegularUser(1, "Smith", "smith@gmail.com", "0412345678")
        );
        assertTrue(result);
    }

    @Test
    void testDeleteUser() {
        UserManager manager = new UserManager();
        manager.addUser(
            new RegularUser(2, "Alice", "alice@gmail.com", "0413256723")
        );
        boolean deleted = manager.deleteUser(2);
        assertTrue(deleted);
    }

    @Test
    void testSearchUser() {
        UserManager manager = new UserManager();
        manager.addUser(
            new RegularUser(3, "Charlie", "charlie@gmail.com", "0425467329")
        );
        int size = manager.searchUserByName("Charlie").size();
        assertEquals(1, size);
    }

    @Test
    void testSearchUser2() {
        UserManager manager = new UserManager();
        manager.addUser(
            new RegularUser(4, "Brevis", "brevis@gmail.com", "0433243921")
        );
        int size = manager.searchUserByName("Brevis").size();
        assertEquals(1, size);
    }
}