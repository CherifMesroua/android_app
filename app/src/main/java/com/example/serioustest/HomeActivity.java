package com.example.serioustest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.text);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", "");
        String email = preferences.getString("email", "");
        String age = preferences.getString("age", "");
        String address = preferences.getString("address", "");
        text.setText("username: "+username+"\nemail: "+""+email+"\nadress: "+address+"\nage: "+age);
    }
}