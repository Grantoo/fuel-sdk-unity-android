package com.fuelpowered.lib.fuelsdk.unity;

/**
 * Created by alexisbarra on 8/30/15.
 */

import android.content.Intent;
import android.os.Bundle;

import muneris.android.MunerisUnityActivity;

public class FuelSDKUnityActivity extends MunerisUnityActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FuelSDKUnitySharedActivity.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FuelSDKUnitySharedActivity.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FuelSDKUnitySharedActivity.onResume();
    }

    @Override
    protected void onPause() {
        FuelSDKUnitySharedActivity.onPause();
        super.onPause();
    }
}
