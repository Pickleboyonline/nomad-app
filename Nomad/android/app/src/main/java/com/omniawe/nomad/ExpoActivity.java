package com.omniawe.nomad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.react.ReactPackage;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by pickl on 4/7/2017.
 */
public class ExpoActivity extends Activity{
     private Intent intent ;
    private String url ;
    private Map<String, String> data ;
    private String urlRequest;
    private RequestQueue queue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        intent = getIntent();
        url = intent.getDataString();
        data = parser(url);
        urlRequest ="http://192.168.1.70:4567/data?id=" + data.get("id");
        queue = Volley.newRequestQueue(ExpoActivity.this);
        stringRequest = new StringRequest(Request.Method.GET, urlRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("hello", "hello");
                        startAppLauncher(url,response );


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("REQUEST", error.toString());
                setContentView(R.layout.failed_screen);
            }
        });


        queue.add(stringRequest);

    }

    public void retry(View v) {
        Toast.makeText(this, "Retrying...", Toast.LENGTH_LONG).show();
        setContentView(R.layout.loading_screen);
        queue.add(stringRequest);

    }
    public void startAppLauncher(String url, String expoUrl) {
        Intent intent = new Intent(this, AppLauncher.class);
        intent.putExtra("data", url);
        intent.putExtra("url", expoUrl);
        startActivity(intent);
    }

    public Map<String, String> parser(String url) {
        String query = url.split("\\?")[1];
        Map<String, String> map = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(query);
        return map;
    }
}
