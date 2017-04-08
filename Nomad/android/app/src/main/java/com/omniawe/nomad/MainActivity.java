package com.omniawe.nomad;

import com.facebook.react.ReactPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.omniawe.nomad.generated.ExponentBuildConstants;
import host.exp.expoview.ExponentActivity;

public class MainActivity extends ExponentActivity {

  @Override
  public String publishedUrl() {
    return "exp://exp.host/@pickleboyonline/nomad";
  }

  @Override
  public String developmentUrl() {
    //return ExponentBuildConstants.DEVELOPMENT_URL;
    return "expc59b6aa35ad44c5ba5b3306fabf7ea3a://79-myn.pickleboyonline.nomad.exp.direct:80";
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
