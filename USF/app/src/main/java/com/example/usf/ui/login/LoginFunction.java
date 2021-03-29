package com.example.usf.ui.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LoginFunction {

    private Connection c = null;

    String userName = "";
    String password = "";

    public LoginFunction(String userName, String password) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:./data/UniSocietyFinderDatabase.db");

        }

        catch (SQLException se) {
            se.printStackTrace();
        }

        this.userName = userName;
        this.password = password;

        /*askLogin(userName, password);*/
    }


    public boolean checkLogin() {
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM 'User';");
            boolean loggedIn = false;

            while (rs.next()) {
                if(rs.getString("username").compareTo(userName) == 0 && rs.getString("password").compareTo(password) == 0) {
                    System.out.println("Welcome");
                    loggedIn = true;
                    return true;
                }
            }
            return false;
          /*  if(!loggedIn) {
                System.out.println("Login Failed");
                askLogin(userName, password);
                checkLogin();

            }*/
        }

        catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }



    public void askLogin(String userName, String password) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username");
        this.userName = scanner.nextLine();  // Read user input

        System.out.println("Enter password");
        this.password = scanner.nextLine();  // Read user input
    }
}