package com.example.serioustest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class Login_activity extends SendReceivePhp {
    Button login_btn;
    ProgressBar progressBar;
    TextView text;
    TextInputEditText edit_email,edit_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        progressBar=findViewById(R.id.progressbar1);
        text=findViewById(R.id.go_to_signup);
        login_btn=findViewById(R.id.login_btn);
        edit_email=findViewById(R.id.login_email);
        edit_password=findViewById(R.id.login_password);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_activity.this, Signup_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email=String.valueOf(edit_email.getText());
                String password=String.valueOf(edit_password.getText());
                if(TextUtils.isEmpty(email) || !email.contains("@")){
                    Toast.makeText(Login_activity.this,email+" invalid email!!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password) || (password.length() <8)){
                    Toast.makeText(Login_activity.this,password+" invalid password!!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Login_activity.this,"done!!",Toast.LENGTH_SHORT).show();
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("email", email);
                    hashMap.put("password", password);
                    send(hashMap);
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}