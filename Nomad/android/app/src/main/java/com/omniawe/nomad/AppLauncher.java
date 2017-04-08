package com.omniawe.nomad;

import android.content.Intent;

import com.facebook.react.ReactPackage;
import com.google.common.base.Splitter;
import com.omniawe.nomad.generated.ExponentBuildConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import host.exp.expoview.ExponentActivity;

public class AppLauncher extends ExponentActivity {
  private Map<String, String> metadata;
  public String getUri() {
    return null;
  }

  @Override
  public String publishedUrl() {
    Intent intent = getIntent();
    String url = intent.getDataString();
    final Map<String, String> data = parser(url);
    metadata = data;
    
    return "exp://exp.host/@pickleboyonline/nomad";
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
    return ExponentBuildConstants.DEVELOPMENT_URL;
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
