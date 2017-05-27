package com.metro.bhargavisriram.metroapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Bhargavi on 01-04-2017.
 */

public class loginPage extends Activity {
String uid, uphone,upass;
    EditText ph,ps;
    Button login,register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        uid=sharedPreferences.getString("uid", "-100");
        if(Integer.parseInt(uid)>=0){
            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
        }
        else{
        setContentView(R.layout.activity_login);
           ph=(EditText)findViewById(R.id.editText2);
            ps=(EditText)findViewById(R.id.editText22);
        uphone=ph.getText().toString();
        upass=ps.getText().toString();
            login=(Button)findViewById(R.id.button5);
            register=(Button)findViewById(R.id.button6);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(in);
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     new LoginActivity(getApplicationContext()).execute(ph.getText().toString(),ps.getText().toString());


                }
            });
       }//ELSE BRACKET
    }
}
