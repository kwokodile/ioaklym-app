package com.ioaklym.ioaklym;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.ArrayList;

public class MyIntentService extends IntentService {

    private static final String TAG = "com.ioaklym.ioaklym";

    @Override
    protected void onHandleIntent(Intent intent){
        Log.i(TAG, "Intent Service started");

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            Log.i(TAG, "Ioaklym is connected to the Wi-Fi");
        } else {
            Log.i(TAG, "NO INTERNET CONNECTION NYOHH");
        }

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://posttestserver.com/post.php?dump&html&dir=henry&status_code=202&sleep=2");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
            nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));

            // Execute Post
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.i(TAG, "I executed a post");

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            Log.i("HttpResponse Status Code", String.valueOf(response.getStatusLine().getStatusCode()));
            if (response.getStatusLine().getStatusCode() == 200){
                String resp_body = EntityUtils.toString(response.getEntity());
                Log.v("resp_body", resp_body);
            }

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
