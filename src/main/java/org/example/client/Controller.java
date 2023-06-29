package org.example.client;

public class Controller {

    public Controller() {

    }

    public int login(String username, char[] password) {
        if (username.equals("admin") && String.valueOf(password).equals("password"))
            return 0;
        return Errors.NO_SUCH_USER;
    }
}