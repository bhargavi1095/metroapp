package com.metro.bhargavisriram.metroapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainActivity extends Activity {
    String name,phone,password;
    EditText user_name,user_ph,user_pass;
    Button reg;
    byte[] skey = new byte[1000];
    String skeyString;
    static byte[] raw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg=(Button)findViewById(R.id.buttonreg);
        reg.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       user_name = (EditText) findViewById(R.id.editText4);
                                       user_ph=(EditText)findViewById(R.id.editText5);
                                       user_pass=(EditText)findViewById(R.id.editText6);
                                       name=user_name.getText().toString();
                                       phone=user_ph.getText().toString();
                                       password=user_pass.getText().toString();
                                       generateSymmetricKey();
                                       String temp=raw.toString();
                                       new RegisterActivity(getApplicationContext()).execute(name,phone,password,temp);

                                   }
                               });

    }
    public void generateSymmetricKey() {
        try {
            Random r = new Random();
           // int num = r.nextInt(10000);
            int num = 234567;
            String knum = String.valueOf(num);
            byte[] knumb = knum.getBytes();
            skey=getRawKey(knumb);
            skeyString = new String(skey);
            System.out.println("Blowfish Symmetric key = "+skeyString);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 128, 256 and 448 bits may not be available
        SecretKey skey = kgen.generateKey();
        raw = skey.getEncoded();
        return raw;
    }


}
