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
    }


    /**
     * method to create an account
     *
     * @param userNameWildcard - inputted username
     * @param password         - inputted password
     */
    public void createAccount(String[] userNameWildcard, String password) {
        String[] args = {userNameWildcard[0], password};
        ContentValues insert = new ContentValues();
        insert.put("username", userNameWildcard[0]);
        insert.put("password", password);
        long id = mDb.insert("User", null, insert);
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
            //checks if there's one or more result in the DB
            if (result.getCount() > 0) {
                result.moveToFirst();
                String userPass = result.getString(result.getColumnIndex("password"));

                //checks the username and password is correct
                if (result.getCount() == 1 && userPass.equals(password)) {
                    loginState = true;
                }
            }

            //otherwise create an account
            if (result.getCount() == 0) {
                if(passwordAllowed(password) && userNameAllowed(userNameWildcard[0]))
                {
                    createAccount(userNameWildcard, password);
                    loginState = true;
                }
            }
            result.close();
            return loginState;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks the user inputted password to see if it is at least of length 5.
     * @param password to check
     * @return true if passed validation, otherwise false
     */
    public boolean passwordAllowed(String password) {
        boolean isAllowed = false;
        if (password.length() > 5) {
            isAllowed = true;
        }
        return isAllowed;
    }

    /**
     * Checks the user inputted username to see if it contains spaces or is blank
     * @param username to check
     * @return true if username passes validation, false otherwise
     */
    public boolean userNameAllowed(String username) {
        boolean isAllowed = false;
        if (username.contains(" ") || username.length() < 1) {

        } else {
            isAllowed = true;
        }
        return isAllowed;
    }

    /**
     * Checks the quiz answers and prepares an array to pass to the query
     * @return String array containing the categories that have been answered 'Yes'
     */
    public String[] categorySelector(String[] answers) {
        //String[] results = {"N","N","N","Y","N"};
        //String[] categoryQuestion = {"Y", "N", "Y", "Y", "Y"};
        String[] categories = {"Culture & Religion", "Sport", "Art", "Music", "Technology"};
        int counter = 0;

        for(int i = 0; i < answers.length; i++) {
            if(answers[i].equals("Y")) {
                counter++;
            }
        }

        //arraylist to add categories to
        List<String> returnArray = new ArrayList<>();

        //this is the misc category that will always show for everyone
        returnArray.add("Misc");

        //add each of the categories that have been replied to with 'yes'
        for(int i = 0; i < answers.length; i++) {
            if(answers[i].equals("Y")) {
                returnArray.add(categories[i]);
            }
        }

        //turn the arraylist into a String array ready to return
        String[] stringArray = Arrays.copyOf(returnArray.toArray(), returnArray.toArray().length, String[].class);
        return stringArray;
    }

    /**
     * run the algorithm to match societies based on quiz questions, updates the database as required
     * @param username to input values against
     */
    public void runAlgorithm(String[] username, String[] answers) {
        try {
            String[] categories = categorySelector(answers);
            //loop through all of the values in the wildcard array. these are generated from the string builder
            //remove all of the societies previously stored against this account, they retook the quiz
            mDb.delete("User_Societies", "username = ?", username);
            for(int k = 0; k < categories.length; k++) {
                //temp wildcard for querying each time
                String[] tempWC = {categories[k]};
                //columns to search the db for
                String[] resultColumnsQR = {"society_id, society_name"};

                //query to return all of the societies in a specific category
                Cursor result = mDb.query("Societies", resultColumnsQR, "category = ?", tempWC, null, null, null, null);
                System.out.println(result.getCount());
                //query to get the user id for inserting into the table
                String[] resultColumns = {"user_id, username"};
                Cursor usersID = mDb.query("User", resultColumns, "username = ?", username, null, null, null, null);

                //if there is a result (there will be), then move to insert
                if (result.getCount() > 0 && usersID.getCount() > 0) {
                    result.moveToFirst();
                    usersID.moveToFirst();

                    int userIDInt = usersID.getInt(usersID.getColumnIndex("user_id"));

                    //insert each of the results in the database
                    for (int i = 0; i < result.getCount(); i++) {
                        int societyID = result.getInt(result.getColumnIndex("society_id"));
                        String societyName = result.getString(result.getColumnIndex("society_name"));

                        Object[] insertWildcards = {societyID, userIDInt, societyName, username[0]};

                        //query to insert into the user societies table
                        mDb.execSQL("insert into User_Societies(society_id, user_id, society_name, username) values(?, ?, ?, ?);", insertWildcards);
                        result.moveToNext();
                    }

                }
                //close both result connections
                result.close();
                usersID.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the data from the User_Societies table that is associated with that specific user.
     *
     * @param userList     list of societies to populate
     * @param wildcardArgs String array of wildcards to fill the query
     * @return the list of societies
     */
    public HashMap<String, String> getUserData(HashMap<String, String> userList, String[] wildcardArgs) {
        try {
            Cursor result = mDb.rawQuery(
                    "SELECT s.society_name, s.webpage_link FROM 'User' u INNER JOIN 'User_Societies' us ON us.user_id = u.user_id " +
                            "INNER JOIN 'Societies' s ON s.society_id = us.society_id WHERE us.username = ?", wildcardArgs);
            int rowCount = result.getCount();
            result.moveToFirst();

            for (int i = 0; i < rowCount; i++) {
                userList.put(result.getString(result.getColumnIndex("society_name")), result.getString(result.getColumnIndex("webpage_link")));
                result.moveToNext();
            }

            result.close();

        } catch (Exception e) {
            System.out.println("SQL Failed!");
            return userList;
        }
        return userList;
    }

    public int getUserSocietyCount(String[] wildcardArgs) {
        try {
            Cursor result = mDb.rawQuery(
                    "SELECT s.society_name, s.webpage_link FROM 'User' u INNER JOIN 'User_Societies' us ON us.user_id = u.user_id " +
                            "INNER JOIN 'Societies' s ON s.society_id = us.society_id WHERE us.username = ?", wildcardArgs);
            int total = result.getCount();


            result.close();
            return total;
        } catch (Exception e) {
            System.out.println("SQL Failed!");
            return 0;
        }
    }

    /**
     * returns a hashmap of all societies in the database
     * @param userList hashmap to populate
     * @return the updated hashmap
     */
    public HashMap<String, String> getAllSocieties(HashMap<String, String> userList) {
        try {
            Cursor result = mDb.rawQuery(
                    "SELECT society_name, webpage_link FROM 'Societies';", null);
            int rowCount = result.getCount();
            result.moveToFirst();

            for (int i = 0; i < rowCount; i++) {
                userList.put(result.getString(result.getColumnIndex("society_name")), result.getString(result.getColumnIndex("webpage_link")));
                result.moveToNext();
            }

            result.close();
        } catch (Exception e) {
            System.out.println("SQL Failed!");
            return userList;
        }
        return userList;
    }

}

