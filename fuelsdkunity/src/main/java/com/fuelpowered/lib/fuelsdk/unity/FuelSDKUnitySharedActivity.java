package com.fuelpowered.lib.fuelsdk.unity;

/**
 * Created by alexisbarra on 8/30/15.
 */

import java.util.Map;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.fuelpowered.lib.fuelsdk.fuel;
import com.fuelpowered.lib.fuelsdk.fuelbroadcastreceiver;
import com.fuelpowered.lib.fuelsdk.fuelbroadcasttype;
import com.fuelpowered.lib.fuelsdk.fuelcompeteui;
import com.unity3d.player.UnityPlayer;
import org.json.JSONObject;
import com.fuelpowered.lib.propeller.JSONHelper;


public class FuelSDKUnitySharedActivity {

	private static final String LOG_TAG = "FuelSDKUnitySharedActiv";
	private static Activity sActivity;

	private static IntentFilter mIntentFilter;

	public static void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "onCreate()");

		sActivity = UnityPlayer.currentActivity;

		Log.d(LOG_TAG, sActivity.getLocalClassName());

        fuel.onCreate(sActivity);
        fuelcompeteui.onCreate(sActivity);


		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_VG_LIST.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_VG_ROLLBACK.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_NOTIFICATION_ENABLED.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_NOTIFICATION_DISABLED.toString());

		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_LOGIN_REQUEST.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_INVITE_REQUEST.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_SHARE_REQUEST.toString());

		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_IMPLICIT_LAUNCH_REQUEST.toString());

		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_USER_VALUES.toString());

		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_CHALLENGE_COUNT.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_TOURNAMENT_INFO.toString());

		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_EXIT.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_MATCH.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_FAIL.toString());

		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_EVENTS.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_LEADERBOARD.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_MISSION.toString());
		mIntentFilter.addAction(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_QUEST.toString());
	}

	public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "onActivityResult()");

		fuel.onActivityResult(sActivity, requestCode, resultCode, data);
        fuelcompeteui.onActivityResult(sActivity, requestCode, resultCode, data);
	}

	public static void onResume() {
        Log.d(LOG_TAG, "onResume()");

		LocalBroadcastManager.getInstance(sActivity).registerReceiver(
                sBroadcastReceiver,
                mIntentFilter);

		fuel.onResume(sActivity);
        fuelcompeteui.onResume(sActivity);
	}

	public static void onPause() {
        Log.d(LOG_TAG, "onPause()");

		LocalBroadcastManager.getInstance(sActivity).unregisterReceiver(sBroadcastReceiver);

		fuel.onPause(sActivity);
        fuelcompeteui.onPause(sActivity);
	}


	private static fuelbroadcastreceiver sBroadcastReceiver = new fuelbroadcastreceiver() {

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, String action, Map<String, Object> data) {

            String message = null;
            JSONObject jsonObject = new JSONObject();

            if (data == null) {
                message = "{\"action\": \""+action+"\" , \"data\" : \"{}\"}";
            } else {
                try {
                    jsonObject.put( "action" , action );
                    jsonObject.put( "data" , JSONHelper.toJSONObject(data) );
                    message = jsonObject.toString();
                } catch (Exception exception) {
                    message = "{\"action\": \"DATA_EXCEPTION\" , \"data\" : \""+exception.getMessage()+"\"}";
                }
            }

            Log.d(LOG_TAG, "Data Receiver message " + message);
            UnityPlayer.UnitySendMessage("FuelSDK", "DataReceiver", message);

		}
	};

}
