package com.example.myapplication;


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    private String TAG = "List";
    private ListView listView;
    private SimpleAdapter adapter;
    private Button return_button;
    private EditText outputText;
    private String text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.list_view);
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread();
        jsonHandlerThread.start();

        try {
            jsonHandlerThread.join();

            // set up adapter
            adapter = new SimpleAdapter(
                    this,
                    ContactInfo.contactList,
                    R.layout.list_view_layout,
                    new String[] { ContactInfo.NAME, ContactInfo.ID, ContactInfo.TELEPHONE, ContactInfo.ADDRESS, ContactInfo.WEBSITE },
                    new int[] { R.id.name,R.id.id, R.id.telephone, R.id.address, R.id.website }
            );


            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    HashMap<String, String> contact = new HashMap<String, String>();
                    contact = (HashMap<String, String>)listView.getItemAtPosition(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                    builder.setTitle(contact.get(ContactInfo.NAME));
                    builder.setMessage("Address: " + contact.get(ContactInfo.ADDRESS));
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                    HashMap<String, String> contact = (HashMap<String, String>)listView.getItemAtPosition(position);
                    outputText.setText("");
                    outputText.setText(contact.get(ContactInfo.ADDRESS));
                    Toast.makeText(ListActivity.this, "Copied.", Toast.LENGTH_LONG).show();
                    return true;
                }
            });


        } catch (InterruptedException e) {
            Log.e(TAG, "InterrputedExcetion: " + e.getMessage());
        }


        outputText = (EditText) findViewById(R.id.output);

        outputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (ListActivity.this).adapter.getFilter().filter(charSequence.toString().replace(" ", ""));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return_button = findViewById(R.id.Return_button);
        // Return to MainActivity
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                saveData();
                finish();
            }
        });

        loadData();
        updateViews();
    }





    //Sync Function
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MainActivity.TEXT, outputText.getText().toString());

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences =getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(MainActivity.TEXT, "");
    }

    public void updateViews() {
        outputText.setText(text);
    }

}



