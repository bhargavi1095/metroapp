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
 * Created by Bhargavi on 02-04-2017.
 */

public class reportActivity  extends AsyncTask<String,Void,String> {

    private Context context;
    String uid,name,balance,phone,rawkey;


    public reportActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... arg0) {
        String phone = (String) arg0[0];
        try {
            JSONObject details= new JSONObject();
            details.put("phone",phone);
            HttpPost httpPost = new HttpPost("http://metroindia.000webhostapp.com/report.php");
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
            try{
                SharedPreferences sp;
                sp = PreferenceManager.getDefaultSharedPreferences(context);
                sp.edit().remove("uid").commit();
                sp.edit().remove("name").commit();
                sp.edit().remove("phone").commit();
                sp.edit().remove("balance").commit();
                Toast.makeText(context,"Your account has been frozen,Contact Customer Care",Toast.LENGTH_LONG).show();
                Intent i = new Intent(context,loginPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            catch (Exception e){Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();}

        }
        else
        {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        }
    }
}
