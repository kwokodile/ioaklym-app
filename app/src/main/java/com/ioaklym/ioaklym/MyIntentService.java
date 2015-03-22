package com.ioaklym.ioaklym;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;


public class MyIntentService extends IntentService {

    private static final String TAG = "com.ioaklym.ioaklym";

    @Override
    protected void onHandleIntent(Intent intent){
        Log.i(TAG, "Intent Service started");
        String bluetoothData = intent.getStringExtra("bluetoothData");
        postData(bluetoothData);

    }

    private void postData(String bluetoothData){
        // Create a new HttpClient and Post Header
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("https://ioaklym.herokuapp.com/steps");
        JSONObject json = new JSONObject();

            try {
                // JSON data:
                Data jsonData = new Data();
                jsonData.receiveData(bluetoothData);
                Log.i(TAG, "Data sent for parsing and processing");
                json.put("user",jsonData.getUser());
                json.put("angle",jsonData.getAngle());
                json.put("timestamp",jsonData.getTimestamp());

                // Post the data
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setEntity(new StringEntity(json.toString()));

                // Execute HTTP Post Request
                Log.i("Executing HTTP Post Request :", json.toString());
                HttpResponse response = httpClient.execute(httpPost);

                Log.i("HttpResponse Status Code", String.valueOf(response.getStatusLine().getStatusCode()));

                if (response.getStatusLine().getStatusCode() == 200){
                    String resp_body = EntityUtils.toString(response.getEntity());
                    Log.v("resp_body", resp_body);
                }
            } catch(JSONException e){
                e.printStackTrace();
            } catch (ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
    }

    public MyIntentService(){
        super("MyIntentService");
    }
}
