package com.omniawe.nomad;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.react.ReactActivity;



public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "Nomad";
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = new Intent(this, AppLauncher.class);
        //startActivity(myIntent);
        // Build GoogleApiClient with AppInvite API for receiving deep links
    }





}
