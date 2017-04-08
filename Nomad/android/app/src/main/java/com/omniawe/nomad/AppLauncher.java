package com.omniawe.nomad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.google.common.base.Splitter;
import com.omniawe.nomad.generated.ExponentBuildConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import de.greenrobot.event.EventBus;
import host.exp.exponent.Constants;
import host.exp.exponent.ExponentManifest;
import host.exp.exponent.RNObject;
import host.exp.exponent.di.NativeModuleDepsProvider;
import host.exp.exponent.kernel.KernelProvider;
import host.exp.expoview.ExpoViewBuildConfig;
import host.exp.expoview.Exponent;
import host.exp.expoview.ExponentActivity;



public class AppLauncher extends ExponentActivity {
  private Map<String, String> metadata;
  public String getUri() {
    return null;
  }

  @Override
  public String publishedUrl() {
    Intent intent = getIntent();
    String mdata = intent.getStringExtra("data");
    String url = intent.getStringExtra("url");
    final Map<String, String> data = parser(mdata);
    metadata = data;
    return url;
  }



  public Map<String, String> parser(String url) {
    String query = url.split("\\?")[1];
    Map<String, String> map = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(query);
    return map;
  }

  public Map<String, String> getData() {
    return metadata;
  }

  @Override
  public String developmentUrl() {
    //return ExponentBuildConstants.DEVELOPMENT_URL;
    return publishedUrl();
  }

  @Override
  public List<String> sdkVersions() {
    return new ArrayList<>(Arrays.asList("15.0.0"));
  }

  @Override
  public List<ReactPackage> reactPackages() {
    return ((MainApplication) getApplication()).getPackages();
  }

  @Override
  public boolean isDebug() {
    return BuildConfig.DEBUG;
  }
}









