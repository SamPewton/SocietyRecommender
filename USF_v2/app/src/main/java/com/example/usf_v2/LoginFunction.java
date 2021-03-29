package com.example.usf_v2;

import java.sql.*;
import java.util.Scanner;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;










public class LoginFunction {

    private Connection c = null;

    String userName = "";
    String password = "";

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;



    private static final String DATABASE_NAME = "UniSocietyFinderDatabase.db";
    private static final String DATABASE_PATH = "/data/data/com.example.usf_v2/databases/";
    SQLiteDatabase db;




    public LoginFunction(String username, String password, Context context) {

        //code taken from http://blog.harrix.org/article/6784
        mDBHelper = new DatabaseHelper(context);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (android.database.SQLException mSQLException) {
            throw mSQLException;
        }



        //deprecated code
         /*
        try {
            c = DriverManager.getConnection("jdbc:sqlite:/assets/UniSocietyFinderDatabase.db");
            this.userName = username;
            this.password = password;
        }

        catch (SQLException se) {
            //se.printStackTrace();
            System.out.println("db connection failed");
        }

        //askLogin(userName, password);

        System.out.println(checkDbExist());
        */
    }


    public boolean checkLogin(String[] userNameWildcard, String password) {
        try {

            String[] resultColumns = {"username, password"};
            Cursor result = mDb.query("User", resultColumns, "username = ?", userNameWildcard, null, null, null, null);
            result.moveToFirst();
            System.out.println(result.getCount() + " rows, " + result.getString(result.getColumnIndex("password")));
            String userPass = result.getString(result.getColumnIndex("password"));


            if (result.getCount() == 1 && userPass.equals(password)) {
                return true;
            } else if (result.getCount() == 0) {
                //create login?
                return false;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            //System.out.println("statement failed");
            //se.printStackTrace();
            return false;
        }

        //deprecated code
            /*
            //Statement s = c.createStatement();
            //ResultSet rs = s.executeQuery("SELECT * FROM 'User';");
            //boolean loggedIn = false;
            while (rs.next()) {
                if(rs.getString("username").compareTo(userName) == 0 && rs.getString("password").compareTo(password) == 0) {
                    //System.out.println("Welcome");
                    //loggedIn = true;
                    return true;
                    //break;
                }
            }


             */
            //if(!loggedIn) {
            //    System.out.println("Login Failed");
               // return false;
                //askLogin(userName, password);
                //checkLogin();
            //}
    }


//deprecated method - overriden by the apps login page
    /*
    public void askLogin(String userName, String password) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username");
        this.userName = scanner.nextLine();  // Read user input

        System.out.println("Enter password");
        this.password = scanner.nextLine();  // Read user input
    }
     */
}
