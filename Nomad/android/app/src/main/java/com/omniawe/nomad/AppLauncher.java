package com.omniawe.nomad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


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
import com.google.common.primitives.Booleans;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;



public class AppLauncher extends AppCompatActivity {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    public String mjsondata = "";

    private String mBundleFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mReactRootView = new ReactRootView(this);

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        //R.layout.activity_app_launcher
        //Button button = (Button) findViewById(R.id.button);
        Intent intent = getIntent();
        String url = intent.getDataString();
        final Map<String, String> data = parser(url);
        //get the id of the app
        Log.i("DATA", data.get("key"));
        String appID = data.get("id");


        new DownloadDataTask().execute(appID);

        //Server stuff **PUT IN ASYNC TASK**
        //set up request to server

        setContentView(R.layout.activity_app_launcher);
        ImageView myImage = (ImageView) findViewById(R.id.imageView);
        myImage.getLayoutParams().height = dpToPx(190);
        myImage.getLayoutParams().width = dpToPx(190);

        RequestQueue queue = Volley.newRequestQueue(AppLauncher.this);
        String urlRequest ="http://192.168.1.70:4567/data?id=" + appID;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("hello", "hello");
                        new DownloadAssets().execute(response);
                        new DownloadDataTask().execute(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("REQUEST", error.toString());
            }
        });
        queue.add(stringRequest);



    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public Map<String, String> parser(String url) {
        String query = url.split("\\?")[1];
        Map<String, String> map = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(query);
        return map;
    }

    private  void startReactNative(String s) {
        mReactRootView = new ReactRootView(AppLauncher.this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setJSBundleFile(s)
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(false)//BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "Nomad", null);
        setContentView(mReactRootView);
    }




    private class DownloadDataTask extends AsyncTask<String, Integer, String> {
        private String str = "sadac";
        private Boolean bool = false;

        @Override
        protected String doInBackground(String... params) {


                        // Display the first 500 characters of the response string.
                        //Log.i("REQUEST", response);
                        //setJsonData(response);
                        //DownloadAssets asset = new DownloadAssets();
                        //asset.execute(mjsondata);
                        File file = new File(AppLauncher.this.getCacheDir(), "index.android.bundle");
                        try{

                            JSONObject jObj = new JSONObject(params[0]);
                            String bundleUrl = jObj.getString("bundleUrl");
                            URL url = new URL("http://192.168.1.70:4567/" + bundleUrl + "index.android.bundle");

                            URLConnection uconn = url.openConnection();
                            uconn.setReadTimeout(30000);
                            uconn.setConnectTimeout(30000);
                            InputStream is = uconn.getInputStream();
                            BufferedInputStream bufferinstream = new BufferedInputStream(is);
                            FileOutputStream fos = new FileOutputStream(file);

                            int current = 0;
                            while ((current = bufferinstream.read()) != -1) {
                                fos.write(current);
                            }
                            bufferinstream.close();
                            Log.i("FINISHED", "FINISHED");
                            str = file.getAbsolutePath();
                            bool = true;

                        }
                        catch (JSONException | IOException e ) {
                            //Log.i("JSON Data", e.toString());
                        }
                        //setFileDir(file);
                        //createReactNativeView(AppLauncher.this);

            return file.getAbsolutePath();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("MAIN thread", s);

            if (bool) {
                startReactNative(str);
            }





        }
    }

    private class DownloadAssets extends AsyncTask<String, Integer, String> {
        private Bitmap bitmap;
        @Override
        protected String doInBackground(String... params)  {
            File file = new File(AppLauncher.this.getCacheDir(), "icon.png");
            try {

                JSONObject jObj = new JSONObject(params[0]);
                String iconUrl = jObj.getString("iconUrl");
                //String appName = jObj.getString("name");
                URL url = new URL("http://192.168.1.70:4567/" + iconUrl);

                URLConnection uconn = url.openConnection();
                uconn.setReadTimeout(30000);
                uconn.setConnectTimeout(30000);
                InputStream is = uconn.getInputStream();
                BufferedInputStream bufferinstream = new BufferedInputStream(is);
                FileOutputStream fos = new FileOutputStream(file);

                int current = 0;
                while ((current = bufferinstream.read()) != -1) {
                    fos.write(current);
                }
                bufferinstream.close();
                Log.i("FINISHED", "FINISHED");

            }
            catch (JSONException | IOException e){
                //do something
            }
             bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return file.getPath();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("MAIN thread", "hello");
            ImageView myImage = (ImageView) findViewById(R.id.imageView);
            myImage.setImageBitmap(bitmap);
        }
    }
}
