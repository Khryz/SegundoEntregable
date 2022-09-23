package com.wizeline.DAO;

public interface UserDAO {
    String createUser(String user, String pass);
    String login(String user, String pass);
}
