package com.example.serioustest;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Signup_Activity extends SendReceivePhp {
    TextInputEditText edit_email,edit_password,edit_username,edit_address,edit_age;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edit_username=findViewById(R.id.signup_username);
        edit_address=findViewById(R.id.signup_address);
        edit_email=findViewById(R.id.signup_email);
        edit_password=findViewById(R.id.signup_password);
        edit_age=findViewById(R.id.signup_age);
        btn=findViewById(R.id.signup_btn);

        PAGE="signup.php";
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=String.valueOf(edit_email.getText());
                String password=String.valueOf(edit_password.getText());
                String username=String.valueOf(edit_username.getText());
                String age=String.valueOf(edit_age.getText());
                String address=String.valueOf(edit_address.getText());
                if(TextUtils.isEmpty(email) || !email.contains("@")){
                    Toast.makeText(Signup_Activity.this,email+" invalid email!!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password) || (password.length() <8)){
                    Toast.makeText(Signup_Activity.this,password+" invalid password!!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(username) || (username.length() <8)){
                    Toast.makeText(Signup_Activity.this,username+" invalid username!!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(age) || (age.length() >2)){
                    Toast.makeText(Signup_Activity.this,age+" invalid age!!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(address)){
                    Toast.makeText(Signup_Activity.this,address+" invalid address!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Signup_Activity.this,"done!!",Toast.LENGTH_SHORT).show();
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("email", email);
                    hashMap.put("password", password);
                    hashMap.put("username", username);
                    hashMap.put("age", age);
                    hashMap.put("address", address);
                    send(hashMap);
                }
            }
        });

    }
    protected void responseRecieved(String response, Map<String, String> params) {
        if(response.trim().equals("success")){
            String email=String.valueOf(edit_email.getText());
            String username=String.valueOf(edit_username.getText());
            String age=String.valueOf(edit_age.getText());
            String address=String.valueOf(edit_address.getText());
            Toast.makeText(Signup_Activity.this, "Sign Up Success", Toast.
                    LENGTH_SHORT).show();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Signup_Activity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", username);
            editor.putString("email", email);
            editor.putString("age", age);
            editor.putString("address", address);
            editor.apply();

            sleep(5);
            Intent intent = new Intent(Signup_Activity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(Signup_Activity.this, "Sign Up Failed", Toast.
                    LENGTH_SHORT).show();
        }
    }

}

