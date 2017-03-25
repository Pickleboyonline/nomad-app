package com.omniawe.nomad;

import android.content.Intent;

import com.facebook.react.ReactPackage;
import com.omniawe.nomad.generated.ExponentBuildConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import host.exp.expoview.ExponentActivity;

public class AppLauncher extends ExponentActivity {

  public String getUri() {
    return null;
  }

  @Override
  public String publishedUrl() {
    Intent intent = getIntent();
    String url = intent.getDataString();
    final Map<String, String> data = parser(url);
    return "exp://exp.host/@pickleboyonline/nomad";
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
