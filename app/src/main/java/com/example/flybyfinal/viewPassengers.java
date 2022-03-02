package com.example.flybyfinal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class viewPassengers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpasengers);

        toFlights2();


        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configSearchLv();
            }
        });

    }

    private void toFlights2(){
        Button btnToFlights = findViewById(R.id.btnToFlights);
        btnToFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewPassengers.this, flightActivity.class));
            }
        });
    }


    private void configSearchLv() {

        EditText edtSearch = findViewById(R.id.edtSearch);
        String searchFID = edtSearch.getText().toString();

        //creating variable for listview.
        ListView lvSearch = (ListView) findViewById(R.id.lvReceipts);

        //creating arraylist for entering database data into listview.
        ArrayList<String> arraylist = new ArrayList<String>();

        //create new DBH to access database methods.
        DBH db = new DBH(viewPassengers.this);


        //database method to get all of the flights out of flights table.
        Cursor cursor = db.getUsrFlights(searchFID);
        cursor.moveToFirst();


            if (cursor.moveToFirst()) {

                // do while loop to loop through array list items.
                do {

                    //add item from array list into listview.
                    arraylist.add("flight ID: " + cursor.getInt(0) + "   Username: " + cursor.getString(1));

                    //end of do while loop.
                } while (cursor.moveToNext());

                //array adapter that adapts arraylist.
                ArrayAdapter arrayAdapter = new ArrayAdapter(viewPassengers.this, android.R.layout.simple_list_item_1, arraylist);
                lvSearch.setAdapter(arrayAdapter);

                //allocating variables to listview.
                ListView sFlight = findViewById(R.id.lvReceipts);

        }
    }
}
