package com.example.usf_v2;

import java.sql.*;
import java.util.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import java.io.*;

/**
 * Login Function class used to log in, create an account and gather all of the user data.
 */

public class LoginFunction {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    /**
     * Constructor, attempts to open and update the database ready for any further queries.
     *
     * @param username ?
     * @param password ?
     * @param context  the window which is calling the function
     */
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

    /**
     * Gets the data from the User_Societies table that is associated with that specific user.
     *
     * @param userList     list of societies to populate
     * @param wildcardArgs String array of wildcards to fill the query
     * @return the list of societies
     */
    public List<String> getUserData(List<String> userList, String[] wildcardArgs) {
        try {
            Cursor result = mDb.rawQuery(
                    "SELECT s.society_name FROM 'User' u INNER JOIN 'User_Societies' us ON us.user_id = u.user_id " +
                            "INNER JOIN 'Societies' s ON s.society_id = us.society_id WHERE us.username = ?", wildcardArgs);
            int rowCount = result.getCount();
            result.moveToFirst();

            for (int i = 0; i < rowCount; i++) {
                userList.add(result.getString(result.getColumnIndex("society_name")));
                //System.out.println(result.getString(result.getColumnIndex("society_name")));
                result.moveToNext();
            }

        } catch (Exception e) {
            System.out.println("SQL Failed!");
            return userList;
        }
        return userList;
    }

    /**
     * method to create an account
     *
     * @param userNameWildcard - inputted username
     * @param password         - inputted password
     */


    public void createAccount(String[] userNameWildcard, String password) {
        String[] args = {userNameWildcard[0], password};
        System.out.println("123345");
        mDb.execSQL("insert into User(username, password) values(?, ?)", args);
    }


    /**
     * Checks the user inputted username and password against what is stored in the database.
     *
     * @param userNameWildcard used to fill the SQL query with the inputted username
     * @param password         to check the inputted password
     * @return true if present
     */
    public boolean checkLogin(String[] userNameWildcard, String password) {
        try {

            boolean loginState = false;
            String[] resultColumns = {"username, password"};
            Cursor result = mDb.query("User", resultColumns, "username = ?", userNameWildcard, null, null, null, null);
            System.out.println(result.getCount());
            //checks if there's one or more result in the DB
            if (result.getCount() > 0) {
                result.moveToFirst();

                System.out.println(result.getCount());
                System.out.println(result.getCount() + " rows, " + result.getString(result.getColumnIndex("password")));
                String userPass = result.getString(result.getColumnIndex("password"));

                //checks the username and password is correct
                if (result.getCount() == 1 && userPass.equals(password)) {
                    loginState = true;
                }
            }

            //otherwise create an account
            if (result.getCount() == 0) {
                createAccount(userNameWildcard, password);
                loginState = true;
            }
            return loginState;

        } catch (Exception e) {
            return false;
        }
    }


    public boolean passwordAllowed(String password) {
        boolean isAllowed = false;
        if (password.length() > 5) {
            isAllowed = true;
        }

        return isAllowed;
    }

    public boolean userNameAllowed(String username) {
        boolean isAllowed = false;
        if (username.contains(" ") || username.length() < 1) {

        } else {
            isAllowed = true;
        }
        return isAllowed;
    }


}

