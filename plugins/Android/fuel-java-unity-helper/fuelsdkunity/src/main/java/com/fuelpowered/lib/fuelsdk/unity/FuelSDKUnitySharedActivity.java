package com.fuelpowered.lib.fuelsdk.unity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.fuelpowered.lib.fuelsdk.fuel;
import com.fuelpowered.lib.fuelsdk.fuelbroadcastreceiver;
import com.fuelpowered.lib.fuelsdk.fuelbroadcasttype;
import com.fuelpowered.lib.fuelsdk.fuelcompete;
import com.fuelpowered.lib.fuelsdk.fuelcompeteui;
import com.fuelpowered.lib.fuelsdk.fuelignite;
import com.unity3d.player.UnityPlayer;

public class FuelSDKUnitySharedActivity {

	private static final String LOG_TAG = "FuelSDKUnitySharedActiv";
	static final String SENDER_ID = "870194926634";
	private static Activity sActivity;


	private static IntentFilter mIntentFilter;

	public static void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "onCreate()");

		sActivity = UnityPlayer.currentActivity;

		Log.d(LOG_TAG, sActivity.getLocalClassName());

		fuel.onCreate(sActivity, SENDER_ID);
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

		final Handler handler = new Handler();

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, String action, Map<String, Object> data) {
			// gonna get something
			if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_VG_LIST.toString())) {
				//Toast.makeText(getApplicationContext(), "Virtual Goods List", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_VG_ROLLBACK.toString())) {
				//Toast.makeText(getApplicationContext(), "Challenge Counts", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_NOTIFICATION_ENABLED.toString())) {
				//Toast.makeText(getApplicationContext(), "Notification Enabled", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_NOTIFICATION_DISABLED.toString())) {
				//Toast.makeText(getApplicationContext(), "Notification Disabled", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_LOGIN_REQUEST.toString())) {
				// call back to say we have done it
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(getApplicationContext(), "Login Request", Toast.LENGTH_SHORT).show();
						fuel.instance().sdkSocialLoginCompleted(null);
					}
				}, 100);
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_INVITE_REQUEST.toString())) {
				// call back to say we have done it
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(getApplicationContext(), "Invite Request", Toast.LENGTH_SHORT).show();
						fuel.instance().sdkSocialInviteCompleted();
					}
				}, 100);
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_SHARE_REQUEST.toString())) {
				// call back to say we have done it
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(getApplicationContext(), "Share Request", Toast.LENGTH_SHORT).show();
						fuel.instance().sdkSocialShareCompleted();
					}
				}, 100);
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_EXIT.toString())) {
				//Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_FAIL.toString())) {
				//Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_MATCH.toString())) {
				if (data != null) {
					// extract the info
					final String tournamentId = (String) data.get("tournamentID");
					final String matchId = (String) data.get("matchID");
					final long score = 43;
					// we should play a game here then call the result
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							//Toast.makeText(getApplicationContext(), "Match Exit", Toast.LENGTH_SHORT).show();
							Map<String, Object> matchResult = new HashMap<String, Object>();
							matchResult.put("tournamentID", tournamentId);
							matchResult.put("matchID", matchId);
							matchResult.put("score", score);
							matchResult.put("visualScore", Long.toString(score));

							fuelcompete.instance().submitMatchResult(matchResult);
							fuelcompeteui.instance().launch();
						}
					}, 100);
				}
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_CHALLENGE_COUNT.toString())) {
				//Toast.makeText(getApplicationContext(), "Challenge Counts", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_TOURNAMENT_INFO.toString())) {
				//Toast.makeText(getApplicationContext(), "Tournament Info", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_EVENTS.toString())) {
				////Toast.makeText(getApplicationContext(), "Events", Toast.LENGTH_SHORT).show();
				// iterate the list and gather the events.
				List<Map<String, Object>> events = (List<Map<String, Object>>) data.get("events");
				for (Map<String, Object> event : events) {
					String activityID = (String) event.get("id");
					switch ((int) event.get("type")) {
						case 0:
							fuelignite.instance().getLeaderBoard(activityID);
							break;
						case 1:
							fuelignite.instance().getMission(activityID);
							break;
						case 2:
							fuelignite.instance().getQuest(activityID);
							break;
					}
				}
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_LEADERBOARD.toString())) {
				//////Toast.makeText(getApplicationContext(), "LeaderBoard", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_MISSION.toString())) {
				//////Toast.makeText(getApplicationContext(), "Mission", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_QUEST.toString())) {
				//////Toast.makeText(getApplicationContext(), "Quest", Toast.LENGTH_SHORT).show();
			}

		}
	};

}
