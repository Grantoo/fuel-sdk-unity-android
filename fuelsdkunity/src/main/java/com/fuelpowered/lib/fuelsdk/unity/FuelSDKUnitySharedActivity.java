package com.fuelpowered.lib.fuelsdk.unity;

/**
 * Created by alexisbarra on 8/30/15.
 */

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
import android.os.Debug;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.fuelpowered.lib.fuelsdk.fuel;
import com.fuelpowered.lib.fuelsdk.fuelbroadcastreceiver;
import com.fuelpowered.lib.fuelsdk.fuelbroadcasttype;
import com.fuelpowered.lib.fuelsdk.fuelcompete;
import com.fuelpowered.lib.fuelsdk.fuelcompeteui;
import com.fuelpowered.lib.fuelsdk.fuelignite;
import com.fuelpowered.lib.fuelsdk.fuelnotificationtype;
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

		final Handler handler = new Handler();

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, String action, Map<String, Object> data) {

            if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_VG_LIST.toString())) {

                String message = null;

                if (data == null) {
                    message = "";
                } else {
                    List<String> paramList = new ArrayList<String>();

                    String transactionId = "";
                    Object transactionIdObject = data.get("transactionID");

                    if ((transactionIdObject != null) &&
                            (transactionIdObject instanceof String)) {
                        transactionId = (String) transactionIdObject;
                    }

                    paramList.add(transactionId);

                    Object virtualGoodsObject = data.get("virtualGoods");

                    if ((virtualGoodsObject != null) &&
                            (virtualGoodsObject instanceof List)) {
                        List<Object> virtualGoods = (List<Object>) virtualGoodsObject;

                        for (Object virtualGoodObject : virtualGoods) {
                            if ((virtualGoodObject == null) ||
                                    !(virtualGoodObject instanceof Map)) {
                                continue;
                            }

                            Map<String, Object> virtualGood = (Map<String, Object>) virtualGoodObject;
                            Object virtualGoodIdObject = virtualGood.get("goodId");

                            if ((virtualGoodIdObject == null) ||
                                    !(virtualGoodIdObject instanceof String)) {
                                continue;
                            }

                            paramList.add((String) virtualGoodIdObject);
                        }
                    }

                    message = TextUtils.join("&", paramList);
                }

                Log.d(LOG_TAG, " Virtual goods message: " + message);
                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnVirtualGoodList", message);

			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_VG_ROLLBACK.toString())) {

                String message = null;

                if (data == null) {
                    message = "";
                } else {
                    String transactionId = "";
                    Object transactionIdObject = data.get("transactionID");

                    if ((transactionIdObject != null) &&
                            (transactionIdObject instanceof String)) {
                        transactionId = (String) transactionIdObject;
                    }

                    message = transactionId;
                }
                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnVirtualGoodRollback", message);
                Log.d(LOG_TAG,  "Virtual Goods Roll back message: " + message);

			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_NOTIFICATION_ENABLED.toString())) {

                String message = null;
                fuelnotificationtype notificationType = null;
                if(data != null) {

                    notificationType = fuelnotificationtype.findByValue(Integer.parseInt(data.get("notificationType").toString()));
                    message = Integer.toString(notificationType.value());
                }

                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnSdkOnNotificationEnabled", Integer.toString(notificationType.value()));
                Log.d(LOG_TAG,  "Notification Enabled message : " + message);

			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_NOTIFICATION_DISABLED.toString())) {

                String message = null;
                fuelnotificationtype notificationType = null;
                if(data != null) {
                    notificationType =  fuelnotificationtype.findByValue(Integer.parseInt(data.get("notificationType").toString()));
                    message = Integer.toString(notificationType.value());
                }

                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnSdkOnNotificationDisabled", Integer.toString(notificationType.value()));
                Log.d(LOG_TAG,  "Notification Disabled message : " + message);

            } else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_LOGIN_REQUEST.toString())) {

                String message = null;
                if(data != null) {
                    message = ((boolean)data.get("allowCache")) ? "true" : "false";
                }

                Log.d(LOG_TAG, "Social login request message : " + message);

                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnSdkSocialLogin", message);

			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_INVITE_REQUEST.toString())) {

                try
                {
                    Map<String, Object> inviteDetail = data;

                    StringBuilder stringBuilder = new StringBuilder();

                    boolean first = true;

                    for (String key : inviteDetail.keySet()) {
                        if (key == null) {
                            continue;
                        }

                        if (first) {
                            first = false;
                        } else {
                            stringBuilder.append('&');
                        }

                        String value = (String) inviteDetail.get(key);

                        if (value == null) {
                            continue;
                        }

                        stringBuilder.append(URLEncoder.encode(key, "UTF-8"));
                        stringBuilder.append('=');
                        stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
                    }


                    UnityPlayer.UnitySendMessage(
                            "FuelSDK",
                            "PropellerOnSdkSocialInvite",
                            stringBuilder.toString());

                    Log.d(LOG_TAG, "Social invite  message : " + stringBuilder.toString());


                } catch (Exception exception) {
                    Log.w(LOG_TAG, exception);

                }

			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_SOCIAL_SHARE_REQUEST.toString())) {

                try
                {
                    Map<String, Object> shareDetail = data;

                    StringBuilder stringBuilder = new StringBuilder();

                    boolean first = true;

                    for (String key : shareDetail.keySet()) {
                        if (key == null) {
                            continue;
                        }

                        if (first) {
                            first = false;
                        } else {
                            stringBuilder.append('&');
                        }

                        String value = (String) shareDetail.get(key);

                        if (value == null) {
                            continue;
                        }

                        stringBuilder.append(URLEncoder.encode(key, "UTF-8"));
                        stringBuilder.append('=');
                        stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
                    }

                    UnityPlayer.UnitySendMessage(
                            "FuelSDK",
                            "PropellerOnSdkSocialShare",
                            stringBuilder.toString());

                    Log.d(LOG_TAG, "Social share  message : " + stringBuilder.toString());


                } catch (Exception exception) {
                    Log.w(LOG_TAG, exception);

                }

			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_EXIT.toString())) {

                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnSdkCompletedWithExit", "");
                Log.d(LOG_TAG,  "Compete exit");

            } else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_FAIL.toString())) {

                String failReason = "";

                for (Map.Entry<String, Object> entry : data.entrySet())
                {
                    Log.d(LOG_TAG, entry.getKey() + "/" + entry.getValue());
                    failReason += entry.getValue().toString();
                }

                Log.d(LOG_TAG,  "Compete fail message : " + failReason);
                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnSdkFailed", failReason);

            } else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_MATCH.toString())) {

                List<String> paramList = new ArrayList<String>();

                String tournamentID = (String) data.get("tournamentID");

                if (!TextUtils.isEmpty(tournamentID)) {
                    paramList.add(tournamentID);
                }

                String matchID = (String) data.get("matchID");

                if (!TextUtils.isEmpty(matchID)) {
                    paramList.add(matchID);
                }

                String paramsJSON = null;
                String paramsJSONUrlEncoded = null;

                try {
                    Map<String, Object> params = (Map<String, Object>) data.get("params");
                    JSONObject jsonObject = JSONHelper.toJSONObject(params);

                    if (jsonObject != null) {
                        paramsJSON = jsonObject.toString();
                        paramsJSONUrlEncoded = URLEncoder.encode(paramsJSON, "UTF-8");

                        if (!TextUtils.isEmpty(paramsJSONUrlEncoded)) {
                            paramList.add(paramsJSONUrlEncoded);
                        }
                    }
                } catch (Exception exception) {
                    Log.w(LOG_TAG, exception);
                }

                Log.d(LOG_TAG, "Compete Match message :  " + (tournamentID == null ? "" : tournamentID) + " - " + (matchID == null ? "" : matchID) + " - " + (paramsJSON == null ? "" : paramsJSON));

                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnSdkCompletedWithMatch", TextUtils.join("&", paramList));

			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_CHALLENGE_COUNT.toString())) {

                String message = null;

                if (data == null) {
                    message = "";
                } else {
                    String count = "";
                    Object countObject = data.get("count");

                    if ((countObject != null) &&
                            (countObject instanceof Integer)) {
                        count = ((Integer) countObject).toString();
                    }

                    message = count;
                }

                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnChallengeCountChanged", message);

                Log.d(LOG_TAG,  "Compete challenge count message: " + message);


            } else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_COMPETE_TOURNAMENT_INFO.toString())) {

                String message = null;

                if (data == null) {
                    message = "";
                } else {
                    List<String> paramList = new ArrayList<String>();

                    String name = "";

                    try {
                        Object nameObject = data.get("name");

                        if ((nameObject != null) &&
                                (nameObject instanceof String)) {
                            name = URLEncoder.encode(
                                    (String) nameObject,
                                    "UTF-8");
                        }
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        Log.w(LOG_TAG, unsupportedEncodingException);
                    }

                    paramList.add(name);

                    String campaignName = "";

                    try {
                        Object campaignNameObject = data.get("campaignName");

                        if ((campaignNameObject != null) &&
                                (campaignNameObject instanceof String)) {
                            campaignName = URLEncoder.encode(
                                    (String) campaignNameObject,
                                    "UTF-8");
                        }
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        Log.w(LOG_TAG, unsupportedEncodingException);
                    }

                    paramList.add(campaignName);

                    String sponsorName = "";

                    try {
                        Object sponsorNameObject = data.get("sponsorName");

                        if ((sponsorNameObject != null) &&
                                (sponsorNameObject instanceof String)) {
                            sponsorName = URLEncoder.encode(
                                    (String) sponsorNameObject,
                                    "UTF-8");
                        }
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        Log.w(LOG_TAG, unsupportedEncodingException);
                    }

                    paramList.add(sponsorName);

                    String startDate = "";

                    try {
                        Object startDateObject = data.get("startDate");

                        if ((startDateObject != null) &&
                                (startDateObject instanceof Long)) {
                            startDate = URLEncoder.encode(
                                    ((Long) startDateObject).toString(),
                                    "UTF-8");
                        }
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        Log.w(LOG_TAG, unsupportedEncodingException);
                    }

                    paramList.add(startDate);

                    String endDate = "";

                    try {
                        Object endDateObject = data.get("endDate");

                        if ((endDateObject != null) &&
                                (endDateObject instanceof Long)) {
                            endDate = URLEncoder.encode(
                                    ((Long) endDateObject).toString(),
                                    "UTF-8");
                        }
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        Log.w(LOG_TAG, unsupportedEncodingException);
                    }

                    paramList.add(endDate);

                    String logo = "";

                    try {
                        Object logoObject = data.get("logo");

                        if ((logoObject != null) &&
                                (logoObject instanceof String)) {
                            logo = URLEncoder.encode(
                                    (String) logoObject,
                                    "UTF-8");
                        }
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        Log.w(LOG_TAG, unsupportedEncodingException);
                    }

                    paramList.add(logo);

                    message = TextUtils.join("&", paramList);
                }

                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnTournamentInfo", message);

                Log.d(LOG_TAG,  "Compete tournament info message " + message);

                //Toast.makeText(getApplicationContext(), "Tournament Info", Toast.LENGTH_SHORT).show();
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_EVENTS.toString())) {
                Log.d(LOG_TAG,  "Ignite events");
                //TODO implement
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
                //TODO implement
                Log.d(LOG_TAG,  "Ignite leaderboard");
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_MISSION.toString())) {
                //TODO implement
                Log.d(LOG_TAG,  "Ignite mission");
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_IGNITE_QUEST.toString())) {
                //TODO implement
                Log.d(LOG_TAG,  "Ignite quest");
			} else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_USER_VALUES.toString())) {
                String message = null;

                if (data == null) {
                    message = "";
                } else {
                    List<String> paramList = new ArrayList<String>();

                    Map<String, Object> variablesMap =
                            (Map<String, Object>) data.get("variables");

                    if (variablesMap != null) {
                        for (String key : variablesMap.keySet()) {
                            if (key == null) {
                                continue;
                            }

                            Object valueObject = variablesMap.get(key);

                            if ((valueObject == null) ||
                                    !(valueObject instanceof String)) {
                                continue;
                            }

                            String value = (String) valueObject;

                            paramList.add(key);
                            paramList.add(value);
                        }
                    }

                    Map<String, Object> dynamicConditionsMap =
                            (Map<String, Object>) data.get("dynamicConditions");

                    if (dynamicConditionsMap != null) {
                        for (String key : dynamicConditionsMap.keySet()) {
                            if (key == null) {
                                continue;
                            }

                            Object valueObject = dynamicConditionsMap.get(key);

                            if ((valueObject == null) ||
                                    !(valueObject instanceof String)) {
                                continue;
                            }

                            String value = (String) valueObject;

                            paramList.add(key);
                            paramList.add(value);
                        }
                    }

                    message = TextUtils.join("&", paramList);
                }

                UnityPlayer.UnitySendMessage("PropellerCommon", "PropellerOnUserValues", message);
                Log.d(LOG_TAG,  "User values message : " + message);
            }else if (action.equals(fuelbroadcasttype.FSDK_BROADCAST_IMPLICIT_LAUNCH_REQUEST.toString())) {

                String message = null;

                if (data != null) {
                    message = (String) data.get("applicationState");
                }

                if (message == null) {
                    message = "";
                }

                Log.d(LOG_TAG,  "Implicit launch request message : " + message);
                UnityPlayer.UnitySendMessage("FuelSDK", "PropellerOnImplicitLaunch", message);
            }

		}
	};

}
