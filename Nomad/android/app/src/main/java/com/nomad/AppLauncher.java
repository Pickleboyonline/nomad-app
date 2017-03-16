package com.nomad;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;


public class AppLauncher extends AppCompatActivity {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mReactRootView = new ReactRootView(this);
        createReactNativeView(this);
        //R.layout.activity_app_launcher
        Button button = (Button) findViewById(R.id.button);

        setContentView(mReactRootView);
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
