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

    private static final String TAG = "MyIntentService";

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
                if (Double.parseDouble(jsonData.getStrideTime())>0.15){
                    // Ignore stride_time == 0.1s
                    json.put("user",jsonData.getUser());
                    Log.d(TAG, "Angle: "+ jsonData.getAngle());
                    json.put("angle",jsonData.getAngle());
                    json.put("stride",jsonData.getStrideTime());
                    json.put("swing",jsonData.getSwingTime());
                    json.put("stance",jsonData.getStanceTime());
                    json.put("timestamp",jsonData.getTimestamp());
                } else {
                    throw new IllegalArgumentException("stride time must be longer than 0.15s");
                }

                // Post the data
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setEntity(new StringEntity(json.toString()));

                // Execute HTTP Post Request
                Log.i("Executing POST :", json.toString());
                HttpResponse response = httpClient.execute(httpPost);

                Log.i("HttpResponse Status", String.valueOf(response.getStatusLine().getStatusCode()));

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
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }
    }

    public MyIntentService(){
        super("MyIntentService");
    }
}
