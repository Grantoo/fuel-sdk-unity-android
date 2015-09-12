package com.fuelpowered.lib.fuelsdk.unity;

import android.content.Intent;
import android.os.Bundle;

import com.unity3d.player.UnityPlayerActivity;

public class FuelSDKUnityActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
