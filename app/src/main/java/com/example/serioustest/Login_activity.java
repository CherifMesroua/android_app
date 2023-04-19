package com.example.serioustest;

import static android.os.SystemClock.sleep;

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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        PAGE="login.php";
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

    @Override
    protected void responseRecieved(String response, Map<String, String> params) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if(status.equals("success")){
                JSONObject userinfo = jsonObject.getJSONObject("user_info");
                String username = userinfo.getString("username");
                String age = userinfo.getString("age");
                String address = userinfo.getString("address");
                String email = userinfo.getString("email");
                //String info="username: "+username+"\nemail: "+""+email+"\nadress: "+address;

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", username);
                editor.putString("email", email);
                editor.putString("age", age);
                editor.putString("address", address);
                editor.apply();
                sleep(5);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }else{
                String error = jsonObject.getString("message");
                Toast.makeText(getApplicationContext(), "1-Error in the json is : " + error, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("key", "1-Error in the json: " + error);
                startActivity(intent);
                finish();
            }
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "2-Error in the json is : " + e.toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("key", "2-Error in the json is : " + e.toString());
            startActivity(intent);
            finish();
        }

    }

}
