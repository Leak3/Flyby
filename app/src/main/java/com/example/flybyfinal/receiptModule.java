package com.example.flybyfinal;


import android.database.Cursor;
import android.os.Bundle;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import android.widget.Toast;

public class receiptModule extends flightActivity {

    static final int READ_BLOCK_SIZE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //method for writing to textfile.
    public void writeTxtFile(Cursor cursor) {

        try {
            //creating/locating file receipts.
            FileOutputStream fileout=openFileOutput("receipts.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

            //format for printing receipt to file
            outputWriter.write("Receipt number      : "+cursor.getString(0));
            outputWriter.write("Order placed by User: "+cursor.getString(1));
            outputWriter.write("For flight number   : "+cursor.getString(2));
            outputWriter.write("Billed for          : $"+cursor.getString(3));

            outputWriter.close();

            //displaying message if the file has been successfully saved.
            Toast.makeText(getBaseContext(), "File saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}