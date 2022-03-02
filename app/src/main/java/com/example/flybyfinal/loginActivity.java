package com.example.flybyfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;


public class loginActivity extends AppCompatActivity {
    public static String accountName;

    //creating hashmap and other variables.
    HashMap<String, String> loginDetails = new HashMap<String, String>();
    EditText username, password;
    Button login;
    TextView errorText, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //allocating variables to widgets.
        login = findViewById(R.id.btnLogin);

        //calling method for switching to register page
        configureToRegisterButton();

        //listener for login button.
        login.setOnClickListener(new View.OnClickListener() {

            //onn click method for loging in.
            @Override
            public void onClick(View view) {
                //variables for widgets.
                EditText username, password;
                username = findViewById(R.id.txtUsernameL);
                password = findViewById(R.id.txtPasswordL);

                //extracting data from TextViews into Strings.
                String sUsername =username.getText().toString();
                String sPassword =password.getText().toString();

                //Creating new DBH class.
                DBH dbh = new DBH(loginActivity.this);

                //checking if the password and username fields are open and error catching.
                if (sUsername.equals("") || sPassword.equals("")) {

                    //toast to notify user of open fields.
                    Toast.makeText(loginActivity.this, "fields are empty, please enter a valid username and password", Toast.LENGTH_SHORT).show();

                } else {

                    //calls method for checking data against the data of the database.
                    boolean checkUserPass = dbh.checkUsernamePassword(sUsername, sPassword);

                    //if statement for checking if the password and username where correct.
                    if (checkUserPass == true) {

                        //toast to notify user he has successfully logged.
                        Toast.makeText(loginActivity.this, "successfully logged in", Toast.LENGTH_SHORT).show();

                        //moving to account activity if password and username are correct.
                        startActivity(new Intent(loginActivity.this, accountActivity.class));

                        //saving username into global variable.
                        accountName = sUsername;

                    } else {
                        //toast message if invalid credentials have been entered.
                        Toast.makeText(loginActivity.this, "enter valid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //method for button that takes you to the register screen
    private void configureToRegisterButton () {
        Button toRegisterButton = findViewById(R.id.btnToRegister);
        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //moving to register activity on button cllick.
                startActivity(new Intent(loginActivity.this, registerActivity.class));
            }
        });
    }

}