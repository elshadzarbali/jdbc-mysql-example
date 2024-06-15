package org.example;

import org.example.dao.UserDAO;
import org.example.entity.User;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        // adding 5 users to database
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("password" + i);
            user.setEmail("email" + i);
            user.setJoinDate(new Date());
            System.out.println(userDAO.addUser(user).getId());
        }

        // adding another 5 users to database
        for (int i = 6; i <= 10; i++) {
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("password" + i);
            user.setEmail("email" + i);
            user.setJoinDate(new Date());
            System.out.println(userDAO.addUser2(user));
        }

        // Get all users
        List<User> users = userDAO.getAllUsers();
        users.forEach(System.out::println);
    }
}