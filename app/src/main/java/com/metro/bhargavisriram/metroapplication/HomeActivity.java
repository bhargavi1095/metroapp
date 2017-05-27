package com.metro.bhargavisriram.metroapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Bhargavi on 31-03-2017.
 */

public class HomeActivity extends Activity {
    Button addmoney,ticket,logout,report;
    TextView balinfo,welcome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        balinfo=(TextView)findViewById(R.id.textView4);
        welcome=(TextView) findViewById(R.id.textView6);
        welcome.setText("Welcome "+sharedPreferences.getString("name", ""));
        balinfo.setText("Your balance is: "+sharedPreferences.getString("balance", "-100"));
        addmoney=(Button)findViewById(R.id.button);
        ticket=(Button)findViewById(R.id.button2);
        logout=(Button)findViewById(R.id.button4);
        report=(Button)findViewById(R.id.button5);
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), addmoney.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp;
                sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sp.edit().remove("uid").commit();
                sp.edit().remove("name").commit();
                sp.edit().remove("phone").commit();
                sp.edit().remove("balance").commit();

                Intent i = new Intent(getApplicationContext(),loginPage.class);
                startActivity(i);
            }
        });
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generate qr code
                Intent i = new Intent(getApplicationContext(),showTicket.class);
                startActivity(i);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                SharedPreferences sharedPreferences;
                                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                               String phone= sharedPreferences.getString("phone", "-100");
                                new reportActivity(getApplicationContext()).execute(phone);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Do you want to report any suspicious activity?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                    }
        });
    }
}
