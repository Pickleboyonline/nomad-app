package com.omniawe.nomad;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;


import java.util.Map;



public class AppLauncher extends AppCompatActivity {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    public String mjsondata = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mReactRootView = new ReactRootView(this);

        //R.layout.activity_app_launcher
        //Button button = (Button) findViewById(R.id.button);
        Intent intent = getIntent();
        String url = intent.getDataString();
        final Map<String, String> data = parser(url);
        //get the id of the app
        Log.i("DATA", data.get("key"));
        String appID = data.get("id");

        //set up request to server
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlRequest ="http://192.168.1.70:4567/data?id=" + appID;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("REQUEST", response);
                        setJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("REQUEST", error.toString());
            }
        });
        queue.add(stringRequest);

        createReactNativeView(this);
        setContentView(mReactRootView);
    }
    public void setJsonData(String str) {
        mjsondata = str;
    }

    public Map<String, String> parser(String url) {
        String query = url.split("\\?")[1];
        Map<String, String> map = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(query);
        return map;
    }




    protected void createReactNativeView(Context context) {
        mReactRootView = new ReactRootView(context);
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String bundleFile = sdDir + "/Download/index.android.bundle";
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setJSBundleFile(bundleFile)
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "Nomad", null);
    }


}
