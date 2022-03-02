package com.example.flybyfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class flightActivity extends AppCompatActivity {
    public static receiptModule c1;
    public static String flightID;


    boolean cantBook;
    boolean flightFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        congfigureAccount();
        configFlightLV();
        bookFlight();
        toSearch();

    }

    //button for moving to account screen
    private void congfigureAccount() {

        //allocating variable to button.
        Button toLoginButton = findViewById(R.id.btnBToAccount);
        toLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //moving from flight activity to account activity.
                startActivity(new Intent(flightActivity.this, accountActivity.class));
            }
        });

    }

    //method for booking flight.
    private void bookFlight() {

        //allocating variables to buttons
        Button btnBook = findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    //create new DBH to access database methods.
                    DBH db = new DBH(flightActivity.this);

                    //method to get user information.
                    Cursor cursor = db.displayUserInfo(loginActivity.accountName);
                    cursor.moveToFirst();

                    //method to get user information.
                    Cursor cursor1 = db.displayUserInfo(loginActivity.accountName);
                    cursor1.moveToFirst();

                    int af2 = cursor.getInt(4);


                        Toast.makeText(flightActivity.this, db.makeBooking(flightID, loginActivity.accountName, af2), Toast.LENGTH_SHORT).show();

                        //updates the listview with new flight capacities.
                        configFlightLV();

                        //calling method that creates receipts.
                        printReceipt();

                }catch (Exception e){

                    //error catching if you dont select a record.
                   Toast.makeText(flightActivity.this, "please select a flight you would like to book.", Toast.LENGTH_SHORT).show();
               }

            }
        });

    }

    //method that creates receipts.
    private void createReceipt() {

        //create new DBH to access database methods.
        DBH db = new DBH(flightActivity.this);

        //datavase method which gets receipt using global variable receiptID.
        db.getReceipts(accountActivity.receiptID);

        //creating instance of receipt module and calling writeTxtFile method to create text file containing receipts.
        c1.writeTxtFile(db.getReceipts(accountActivity.receiptID));

    }

    private void configFlightLV() {

        //creating variable for listview.
        ListView allFlights = (ListView) findViewById(R.id.allFlights);

        //creating arraylist for entering database data into listview.
        ArrayList<String> arraylist = new ArrayList<String>();

        //create new DBH to access database methods.
        DBH db = new DBH(flightActivity.this);


        //database method to get all of the flights out of flights table.
        Cursor cursor = db.getFlights();
        cursor.moveToFirst();


        if (cursor.moveToFirst()) {

            // do while loop to loop through array list items.
            do {

                    //add item from array list into listview.
                    arraylist.add(cursor.getInt(0) + "   Price: $" + cursor.getString(1) + "   Destination: " + cursor.getString(2) + "   Departure: " + cursor.getString(3) + "   Duration:" + cursor.getString(4) + "   Arrival: " + cursor.getString(5) + "   Available seats: " + cursor.getString(6));

                //end of do while loop.
            } while (cursor.moveToNext());

            //array adapter that adapts arraylist.
            ArrayAdapter arrayAdapter = new ArrayAdapter(flightActivity.this, android.R.layout.simple_list_item_1, arraylist);
            allFlights.setAdapter(arrayAdapter);

            //allocating variables to listview.
            ListView aFlight = findViewById(R.id.allFlights);
            aFlight.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //getting flight id by getting flight location on listview.
                    flightID = Integer.toString(i + 1);


                }
            });
        }
    }

    private void toSearch(){
        Button toSearch = findViewById(R.id.btnToSearch);
        toSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(flightActivity.this, viewPassengers.class));
            }
        });
    }

    private void printReceipt() {


                    DBH db = new DBH(flightActivity.this);
                    String name = "receipts.txt";
                    String fileText = db.getFileContents();

                    FileOutputStream fos = null;

                    try {
                        fos = openFileOutput(name, MODE_PRIVATE);
                        fos.write(fileText.getBytes());

                        Toast.makeText(flightActivity.this, "Saved to " + getFilesDir() + "/" + name,
                                Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
}
