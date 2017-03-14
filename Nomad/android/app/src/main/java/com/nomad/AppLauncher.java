package com.nomad;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


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
        mReactRootView = new ReactRootView(this);

        /*JSBundleLoader jsBundleLoader = new JSBundleLoader() {
            @Override
            public void loadScript(ReactBridge reactBridge) {
                reactBridge.loadScriptFromFile("/sdcard/Download/index.android.bundle", "/sdcard/Download/index.android.bundle");
            }
        };*/
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
        //R.layout.activity_app_launcher
        setContentView(mReactRootView);
    }
}
