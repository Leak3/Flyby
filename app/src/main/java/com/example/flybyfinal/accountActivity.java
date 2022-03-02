package com.example.flybyfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class accountActivity extends AppCompatActivity {

    //global variables
    public static String aF, receiptID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //calling method for moving to login page
        configureLogout();
        configureFlights();
        displayAccount();
        addFunds();
        configMyFlight();
        cancelBooking();

    }



    //method for diplaying account information.
    private void displayAccount(){

        //allocating widgets to variables.
        TextView account = findViewById(R.id.txtUsernameAcc);
        TextView fullname = findViewById(R.id.txtFullnameAcc);
        TextView email = findViewById(R.id.txtEmailAcc);
        TextView balance = findViewById(R.id.txtBalanceAcc);

        //creating new DBH to access methods for the database.
        DBH db = new DBH(accountActivity.this);

        //method to get user information.
        Cursor cursor = db.displayUserInfo(loginActivity.accountName);
        cursor.moveToFirst();

        //entering user info gathered form database into textviews.
        account.setText(cursor.getString(0));
        fullname.setText(cursor.getString(2));
        email.setText(cursor.getString(3));
        balance.setText("$"+ cursor.getString(4));

        //storing user balance into a global variable.
        aF = cursor.getString(4);
    }

    //button for going to the add funds popup menu.
    private void addFunds(){
        Button addFunds = findViewById(R.id.btnEdit);
        addFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //moving from account activity to popwin popup activity.
                startActivity(new Intent (accountActivity.this, popWin.class));
            }
        });

    }

    //button for logout.
    private void configureLogout(){

        //allocating variable for button widget.
        Button toLoginButton = findViewById(R.id.btnLogout);

        //listener for toLogin button.
        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //moving from current activity back to login activity.
               startActivity(new Intent(accountActivity.this, loginActivity.class));
            }
        });

    }

    //button for going to flights screen.
    private void configureFlights(){

        //allocating variable to button.
        Button toLoginButton = findViewById(R.id.btnFlights);
        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //moving from current activity to fllightactivity.
                startActivity(new Intent(accountActivity.this, flightActivity.class));
            }
        });

    }

    //button for displaying all the users flights.
    private void configMyFlight() {

        //creating variable for listview.
        ListView myFlights = (ListView) findViewById(R.id.myFlights);

        //creating arraylist for entering database data into listview.
        ArrayList<String> arraylist = new ArrayList<String>();

        //creating new DBH to access database methods.
        DBH db = new DBH(accountActivity.this);

        //method for getting users booked flights.
        Cursor result = db.getMyFlights(loginActivity.accountName);


        if (result.moveToFirst()) {

            //do while loop for entering data into listview.
            do {

                //adding data from arraylist into listview.
                arraylist.add(result.getInt(0) + "#   username: " + result.getString(1) + "   flightID: " + result.getString(2) + "   Price: $" + result.getString(3));

                //end of while loop.
            } while (result.moveToNext());
        }

        //arrayadapter for addapting the arraylist.
        ArrayAdapter arrayAdapter = new ArrayAdapter(accountActivity.this, android.R.layout.simple_list_item_1, arraylist);
        myFlights.setAdapter(arrayAdapter);

        //allocating variables for listview.
        ListView aFlight = findViewById(R.id.myFlights);
        aFlight.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //getting data out of arraylist as a string.
                String lItem = arraylist.get(i);

                //spllitting the string to get the receipt ID.
                String[] splitArray = lItem.split("#");

                //making receiptID the first half of the split String.
                receiptID = splitArray[0];

                //making toast to show the receipt id that has been selected.
                Toast.makeText(accountActivity.this, receiptID, Toast.LENGTH_SHORT).show();

            }
        });
    }

    //button for cancelling booking.
    private void cancelBooking(){
        Button cancelBooking = findViewById(R.id.btnCancel);
        cancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    //creating new DBH to access database methods.
                    DBH db = new DBH(accountActivity.this);

                    //calling method for cancelling booking.
                    db.cancelBooking(receiptID);

                    //updating listview on click.
                    configMyFlight();

                }catch (Exception e){

                    //error catching if you dont select a record.
                    Toast.makeText(accountActivity.this, "please select a flight you would like to cancel.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}