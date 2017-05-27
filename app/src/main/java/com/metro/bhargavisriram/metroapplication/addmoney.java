package com.metro.bhargavisriram.metroapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Bhargavi on 31-03-2017.
 */

public class addmoney extends Activity {
    String amount;
    EditText amt;
    Button add;
    String uid;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_activity);
        add=(Button)findViewById(R.id.button3);
        add.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        amt= (EditText) findViewById(R.id.editText);
        amount=amt.getText().toString();
    SharedPreferences sharedPreferences;
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    uid = sharedPreferences.getString("uid", "0");
    new moneyActivity(getApplicationContext()).execute(uid,amount);

}
        });

        }
        }
