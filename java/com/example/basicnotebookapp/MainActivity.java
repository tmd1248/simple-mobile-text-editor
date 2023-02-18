package com.example.basicnotebookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



public class MainActivity extends AppCompatActivity {
    EditText userinput;
    String filename = "inputStorage";
       /* This is intended to run at launch, and will load from memory the last known text from the file.
    * It throws errors if the file does not exist or is otherwise unreachable.
    * */
    public void loadText(Context context) {
        String savedText;
        try {
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                savedText = stringBuilder.toString();
                userinput.setText(savedText);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userinput = findViewById(R.id.user_input);
        Context context = getApplicationContext();
        loadText(context);


    }


    public void delete(View view) {
 /* Attempts to overwrite the file with empty text, used to effectively delete it.
 * useful for starting over.
 * */
        String fileContents = "";
        try {
            FileOutputStream file = openFileOutput(filename, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);
            outputStreamWriter.write(fileContents);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            Toast.makeText(MainActivity.this, "Successfully deleted", Toast.LENGTH_LONG)
                    .show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }
    /*Saves the input from the user into a persistent file for use later.
    * */
        public void save(View view){
            String userText = userinput.getText().toString();
            try {
                FileOutputStream file = openFileOutput(filename, MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);
                outputStreamWriter.write(userText);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                Toast.makeText(MainActivity.this, "Successfully saved", Toast.LENGTH_LONG)
                        .show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }

        }
    }
