package com.example.serioustest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendReceivePhp extends AppCompatActivity {
    protected void send(HashMap<String,String> hashMap){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.36/seriousTest/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if(status.equals("success")){
                                JSONObject userinfo = jsonObject.getJSONObject("user_info");
                                String username = userinfo.getString("username");
                                String age = userinfo.getString("age");
                                String address = userinfo.getString("address");
                                String email = userinfo.getString("email");
                                String info="username: "+username+"\nemail: "+""+email+"\nadress: "+address;

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("username", username);
                                editor.putString("email", email);
                                editor.putString("age", age);
                                editor.putString("address", address);
                                editor.apply();

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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "3-volley Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("key", "3-volley Error: " + error.toString());
                        startActivity(intent);
                        finish();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
