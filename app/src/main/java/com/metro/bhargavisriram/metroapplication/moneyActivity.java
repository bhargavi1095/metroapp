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

/**
 * Created by Bhargavi on 31-03-2017.
 */

public class moneyActivity   extends AsyncTask<String,Void,String> {

    private Context context;
    String balance,tuid,tamount;


    public moneyActivity (Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... arg0) {
        String uid = (String) arg0[0];
        String amount = (String) arg0[1];
        tuid=uid;
        tamount=amount;
        try {
            JSONObject details= new JSONObject();
            details.put("uid",uid);
            details.put("amount",amount);
            HttpPost httpPost = new HttpPost("http://metroindia.000webhostapp.com/addmoney.php");
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
            balance=words[2];

            //  Toast.makeText(context, "You have been registered", Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("balance", balance);
            editor.commit();
            Intent i = new Intent(context, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        else
        {
            Toast.makeText(context, result+":::ERROR:::", Toast.LENGTH_LONG).show();

        }
    }
}
