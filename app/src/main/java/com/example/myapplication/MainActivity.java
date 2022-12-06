package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Button search_button;
    private Button locate_button;
    private Button reset_button;
    private EditText inputText;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private String text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = (EditText) findViewById(R.id.input);

        search_button = findViewById(R.id.Search_Button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                saveData();
                startActivity(intent);
                finish();
            }
        });

        reset_button = findViewById(R.id.Reset_Button);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText.setText("");
            }
        });




        locate_button = findViewById(R.id.Locate_Button);

        locate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                finish();

            }
        });

        loadData();
        updateViews();
        
    }


    //Sync Func

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, inputText.getText().toString());

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences =getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
    }

    public void updateViews(){
        inputText.setText(text);
    }


}
