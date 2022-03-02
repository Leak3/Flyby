package com.example.flybyfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;


public class registerActivity extends AppCompatActivity {

    //creating variables for widgets.
    EditText fullname, email, username, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //calling method for moving to login page and registering.
        configureToLogin();
        register();
    }

    //button for going to login screen
    private void configureToLogin() {

        //declaring button.
        Button toLoginButton = findViewById(R.id.btnToLogin);

        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //moving to loging activity.
                startActivity(new Intent(registerActivity.this, loginActivity.class));
            }
        });
    }

    //button for registering
    private void register() {

        //declaring variables for widgets.
        Button toLoginButton = findViewById(R.id.btnRegister);
        EditText username, password, fullname, email;
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        fullname = findViewById(R.id.txtName);
        email = findViewById(R.id.txtEmail);

        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating variables for and extracitng data form user input.
                String sUsername,sPassword, sFullname, sEmail;
                sUsername = username.getText().toString();
                sPassword = password.getText().toString();
                sFullname = fullname.getText().toString();
                sEmail = email.getText().toString();

                //creating default balance variable.
                double newBalance = 0;

                //creating new user object for storing user data.
                user user = null;

                //if statement to check for empty fields aswell as unavailable fields.
                if (sFullname == "" || sPassword == "" || sUsername == "" || sEmail == ""){

                    //toast notifying user of invalid register details.
                    Toast.makeText(registerActivity.this, "enter valid details", Toast.LENGTH_SHORT).show();
                }else{

                    //try catch for creating user object.
                    try {

                        //creating user object with user input.
                         user = new user(sUsername, sPassword, sEmail, sFullname, newBalance);

                        //creating new DBH for accessing database methods.
                        DBH dbh = new DBH(registerActivity.this);

                        //creating boolean for user object creating.
                        boolean succ = dbh.createUser(user);



                        if (succ) {

                            //toast for notifying that registration was a successs.
                            Toast.makeText(registerActivity.this, "user was created succsefully", Toast.LENGTH_SHORT).show();

                            //moving from register activity to login activity.
                            startActivity(new Intent(registerActivity.this, loginActivity.class));

                        }else if (!succ){

                            Toast.makeText(registerActivity.this, "user was not registered please try again, enter all fields, or enter a unique username", Toast.LENGTH_SHORT).show();

                        }

                         //error handling.
                    }catch (Exception e){

                        //creating toast if there is an error while creating user.
                        Toast.makeText(registerActivity.this, "error creating customer", Toast.LENGTH_SHORT).show();
                    }
                }
        };
    })
;}}

