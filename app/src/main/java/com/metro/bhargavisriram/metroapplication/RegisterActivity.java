package com.metro.bhargavisriram.metroapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Bhargavi on 31-03-2017.
 */

public class RegisterActivity  extends AsyncTask<String,Void,String> {

    private Context context;
    String sname,sphone,uid;
    String rawkey,inputMessage,encryptedData;
    static byte[] raw;


    public RegisterActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... arg0) {
        String name = (String) arg0[0];
        String phone = (String) arg0[1];
        String password = (String) arg0[2];
         rawkey = (String) arg0[3];
        sname=name;
        sphone=phone;
        try {
            JSONObject details= new JSONObject();
            details.put("name",name);
            details.put("phone",phone);
            details.put("password",password);
            details.put("rawkey",rawkey);

            HttpPost httpPost = new HttpPost("http://metroindia.000webhostapp.com/registeruser.php");
            httpPost.setEntity(new StringEntity(details.toString()));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse response = new DefaultHttpClient().execute(httpPost);
            String responseString="";
            responseString= EntityUtils.toString(response.getEntity());

            return responseString;

        }

        catch (Exception e){
            return new String("Excption :" + e.getMessage());
        }


    }

    @Override
    protected void onPostExecute(String result){
        if(result.contains("ok")) {
            String[] words=result.split("\\s");
                 uid=words[2];
            try {
                SharedPreferences sharedPreferences;
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                inputMessage=sphone;
                byte[] ibyte = inputMessage.getBytes();
                raw = rawkey.getBytes();
                byte[] ebyte=encrypt(raw, ibyte);
                encryptedData = new String(ebyte);

            }
            catch(Exception e) {
                System.out.println(e);
            }

          //  Toast.makeText(context, "You have been registered", Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", sname);
            editor.putString("phone", sphone);
            editor.putString("balance", "0");
            editor.putString("uid", uid);
            editor.putString("rawkey", rawkey);
            editor.commit();

            Intent i = new Intent(context, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        else
        {
            Toast.makeText(context,"Phone number is already registered", Toast.LENGTH_LONG).show();

        }
    }
      private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }
}

