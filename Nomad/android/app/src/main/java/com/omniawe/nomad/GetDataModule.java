package com.omniawe.nomad;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;

public class GetDataModule extends ReactContextBaseJavaModule  {

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    public GetDataModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "GetDataAndroid";
    }


    @ReactMethod
    public void getData(Callback successCallback) {
        AppLauncher demo = new AppLauncher();
       successCallback.invoke(demo.getData());
    }
}