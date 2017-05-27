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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bhargavi on 01-04-2017.
 */

public class LoginActivity extends AsyncTask<String,Void,String> {

    private Context context;
    String uid,name,balance,phone,rawkey;


    public LoginActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... arg0) {
        String phone = (String) arg0[0];
        String password = (String) arg0[1];
        try {
            JSONObject details= new JSONObject();
            details.put("phone",phone);
            details.put("password",password);
            HttpPost httpPost = new HttpPost("http://metroindia.000webhostapp.com/login.php");
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
            String temp=words[2];
            JSONObject jObject = null;
            JSONArray jArray =null;
            try {
                JSONObject jsonObject = new JSONObject(temp);
                JSONObject newJSON = jsonObject.getJSONObject("results");
                System.out.println(newJSON.toString());
                jsonObject = new JSONObject(newJSON.toString());

                    uid=jsonObject.getString("uid");
                    name=jsonObject.getString("name");
                    phone=jsonObject.getString("phone");
                    balance=jsonObject.getString("balance");
                    rawkey=jsonObject.getString("rawkey");

            } catch (JSONException e) {

                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            SharedPreferences sharedPreferences;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("phone", phone);
            editor.putString("balance", balance);
            editor.putString("uid", uid);
            editor.putString("rawkey", rawkey);
            editor.commit();
            Intent i = new Intent(context, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        else
        {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        }
    }
}
