package com.example.flybyfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBH extends SQLiteOpenHelper {

    //static variables for refrencing to database tables and collumns.
    public static final String users = "users";
    public static final String username = "username";
    public static final String password = "password";
    public static final String email = "email";
    public static final String fullname = "fullname";
    public static final String balance = "balance";
    public static final String flights = "flights";
    public static final String flightID = "flightID";
    public static final String priceR = "price";
    public static final String price = "price";
    public static final String destination = "destination";
    public static final String departureTime = "departureTime";
    public static final String flightTime = "flightTime";
    public static final String arivalTime = "arivalTime";
    public static final String capacity = "capacity";
    public static final String receipts = "receipts";
    public static final String receiptID = "receiptID";

    public DBH(@Nullable Context context) {
        super(context, "flyby.db", null, 1);
    }

    //creating database with sqlite aswell as inserting the 20 flights into the flights table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + users + " (" + username + " VARCHAR(45) PRIMARY KEY, " + password + " VARCHAR(45), " + fullname + " VARCHAR(45), " + email + " VARCHAR(45), " + balance + " DOUBLE)";
        db.execSQL(createUserTable);
        String createFlightTable = "CREATE TABLE " + flights + " (  " + flightID + " INTEGER PRIMARY KEY AUTOINCREMENT  UNIQUE, " + price + " DOUBLE, " + destination + "   VARCHAR, " + departureTime + " TEXT, " + flightTime + "    TEXT, " + arivalTime + "    TEXT, " + capacity + "      INT (40) )";
        db.execSQL(createFlightTable);
        String createReceiptTable = "CREATE TABLE " + receipts + " (" + receiptID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + username + "  VARCHAR (45) REFERENCES users (username), " + flightID + "  INT REFERENCES FLIGHTS (flightID), " + priceR + " DOUBLE REFERENCES FLIGHTS (price))";
        db.execSQL(createReceiptTable);

        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('100', 'London','07:00','10hrs','17:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('120', 'Tokyo','12:00','15hrs','03:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('160', 'Moscow','04:00','12hrs','16:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('100', 'Los Angeles','14:00','20hrs','16:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('210', 'Kyoto','12:00','15hrs','03:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('330', 'Bangladesh','13:00','7hrs','20:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('300', 'Paris','06:00','8hrs','14:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('100', 'Amsterdam','07:00','9hrs','16:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('50', 'Rome','08:00','10hrs','18:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('75', 'Barcelona','19:00','11hrs','06:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('180', 'Berlin','23:00','3hrs','02:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('250', 'Luxemburgh','03:00','12hrs','15:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('140', 'Montreal','09:00','4hrs','13:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('200', 'Rio','07:00','10hrs','17:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('190', 'Mexico City','05:00','10hrs','15:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('170', 'Melbourne','21:00','24hrs','21:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('200', 'Bejing','03:00','17hrs','20:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('220', 'Cape Town','20:00','16hrs','12:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('100', 'Mykanos','01:00','24hrs','01:00','40' )");
        db.execSQL("INSERT INTO flights( price, destination, departureTime, flightTime, arivalTime, capacity) VALUES('130', 'Istanbul','22:00','8hrs','06:00','40' )");

    }

    //called when version of database is upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //method for registering user into the database.
    public boolean createUser(user user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(username, user.getUsername());
        cValues.put(password, user.getPassword());
        cValues.put(fullname, user.getFullname());
        cValues.put(email, user.getEmail());
        cValues.put(balance, user.getBalance());

        long insert = db.insert(users, null, cValues);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //method for checking password and username for login.
    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + users + " WHERE username = ? AND  password  = ?", new String[]{username, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //method for getting flights out of database.
    public Cursor getFlights() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + flights, null);
        cursor.moveToFirst();
        return cursor;
    }

    //method for getting the users flights out of database.
    public Cursor getMyFlights(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + receipts+" WHERE username = ?", new String[]{username});
        cursor.moveToFirst();
        return cursor;
    }

    //method for getting user info out of database.
    public Cursor  displayUserInfo(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + users + " WHERE username = ?", new String[]{username});
        cursor.moveToFirst();
        
        return cursor;
    }

    //method for adding funds to user account.
    public void  addFunds(Double funds, Double addedFunds, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        funds = funds + addedFunds;

        cursor = db.rawQuery("UPDATE "+users+" SET "+balance+" = "+funds+" WHERE username = ?", new String[]{username});
        cursor.moveToFirst();
    }

    //method for making a booking (includes adding receipt to database, removing seat, removing funds from user).
    public String  makeBooking(String flightID, String username1, double funds) {

        String message = null;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + flights + " WHERE flightID = ?", new String[]{flightID});
        cursor.moveToFirst();

        String cap = cursor.getString(6);
        String price = cursor.getString(1);

        double s = Double.parseDouble(cap);
        double price1 = Double.parseDouble(price);


        if (funds >= price1 && s > 0) {

            s = s - 1;
            funds = funds - price1;

            Cursor cursor1 = db.rawQuery("UPDATE " + flights + " SET " + capacity + " = " + s + " WHERE flightID = ?", new String[]{flightID});
            cursor1.moveToFirst();

            Cursor cursor2 = db.rawQuery("UPDATE " + users + " SET " + balance + " = " + funds + " WHERE username = ?", new String[]{username1});
            cursor2.moveToFirst();

            db.execSQL("INSERT INTO receipts(username, flightID, price) VALUES('" + username1 + "', '" + flightID + "', '" + price1 + "')");


             message = "booking has been made.";

        } else if (s <= 0) {
             message = "flight capacity has been reached.";
        } else if (funds < price1) {
            message = "insufficient funds in balance.";
        }
        return message;
    }

    //method for cancelling booking (includes adding back seat and deleting receipt).
    public void  cancelBooking(String receiptID) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " +receipts+" WHERE receiptID = ?", new String[]{receiptID});
        cursor.moveToFirst();
        String flightID2 = cursor.getString(2);

        Cursor cursor3 = db.rawQuery("SELECT * FROM " +flights+ " WHERE flightID = ?", new String[]{flightID2});
        cursor3.moveToFirst();
        String cap = cursor3.getString(6);

        Cursor cursor1 = db.rawQuery("DELETE FROM " +receipts+ " WHERE receiptID = ?", new String[]{receiptID});
        cursor1.moveToFirst();


        double s = Double.parseDouble(cap);
        s = s+1;

        Cursor cursor2 = db.rawQuery("UPDATE " + flights + " SET " + capacity + " = " + s + " WHERE flightID = ?", new String[]{flightID2});
        cursor2.moveToFirst();

    }

    //method for getting receipts out of database.
    public Cursor getReceipts(String receiptID){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + receipts + " WHERE receiptID = ?", new String[]{receiptID});
        cursor.moveToFirst();

        return cursor;

    }

    public Cursor getUsrFlights(String searchFID) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT flightID,username FROM " + receipts + " WHERE flightID = ?", new String[]{searchFID});
        cursor.moveToFirst();

        return cursor;
    }

    public String getFileContents() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT *  FROM " + receipts + " WHERE receiptID =(SELECT MAX(receiptID) FROM " + receipts + ");", null);
        cursor.moveToFirst();

        int receiptID = cursor.getInt(0);
        String username = cursor.getString(1);
        int flightID = cursor.getInt(2);
        double price = cursor.getInt(3);

        String text = "Receipt number      : " + receiptID + "\n" + "Order placed by User: "+ username + "\n" + "For flight number   : " + flightID + "\n" + "Billed for          : $" + price;

        return text;

    }





}
