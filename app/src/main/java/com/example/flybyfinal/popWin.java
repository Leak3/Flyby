package com.example.flybyfinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class popWin extends accountActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwin);

        //creating display metrics to resize popup screen.
        DisplayMetrics dispMetr = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dispMetr);

        //allocating variables to height and width of popup window.
        int w = dispMetr.widthPixels;
        int h = dispMetr.heightPixels;

        //setting height and width of popup window.
        getWindow().setLayout((int)(w*.8),(int)(h*.4));

        //allocating variable for button and edittext.
        Button add = findViewById(R.id.btnAdd);
        EditText userInp = findViewById(R.id.addFundsTxt);

        //onclick listener for button which will add designated amount of money to user balance.
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //extracting useriinput from textedit into a string.
                String addedFunds = userInp.getText().toString();

                double dub;
                double dubOg;
                //error catching if textedit is empty
                try {

                    //parsing string into double for user input aswell as user balance.
                    dub = Double.parseDouble(addedFunds);
                    dubOg = Double.parseDouble(accountActivity.aF);

                    //creating new DBH to access database methods.
                    DBH db = new DBH(popWin.this);

                    //calling method for adding funds.
                    db.addFunds(dubOg, dub, loginActivity.accountName);
                    Toast.makeText(popWin.this, "funds have been added.", Toast.LENGTH_SHORT).show();

                    //moving back to account activity.
                    startActivity(new Intent(popWin.this, accountActivity.class));

                }catch (Exception  e) {

                    //toast to notify user that imput is invalid.
                    Toast.makeText(popWin.this, "please enter valid number, and if you want to cancel enter 0.", Toast.LENGTH_SHORT).show();

                }








                }
            });
        };
    }

