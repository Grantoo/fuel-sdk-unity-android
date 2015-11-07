package com.fuelpowered.lib.fuelsdk.unity;

/**
 * Created by alexisbarra on 8/30/15.
 */

import android.util.Log;

import com.fuelpowered.lib.fuelsdk.fuel;
import com.fuelpowered.lib.fuelsdk.fuelcompete;
import com.fuelpowered.lib.fuelsdk.fuelcompeteui;
import com.fuelpowered.lib.fuelsdk.fueldynamics;
import com.fuelpowered.lib.fuelsdk.fuelignite;
import com.fuelpowered.lib.fuelsdk.fueligniteui;
import com.fuelpowered.lib.fuelsdk.fuelimpl.fueljsonhelper;
import com.fuelpowered.lib.fuelsdk.fuelnotificationtype;
import com.fuelpowered.lib.fuelsdk.fuelorientationtype;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class FuelSDKUnitySingleton {

    private static final String kLogTag = "FuelSDKUnitySingleton";

    //--Fuel methods
    public static void initialize(String gameId, String gameSecret, boolean gameHasLogin,
                                  boolean gameHasInvite, boolean gameHasShare) {

        Log.i(kLogTag, "Initialize FuelSdkUnity");

        fuel.setup(gameId, gameSecret, gameHasLogin, gameHasInvite, gameHasShare);
        fuel.instance().setLanguageCode("EN");
        fueldynamics.setup();

        Log.i(kLogTag, "Finished Initialize");
    }

    public static void useSandbox() {
        fuel.useSandbox();
    }

    public static void setNotificationToken(String notificationToken) {
        fuel.instance().setNotificationToken(notificationToken);
    }

    public static boolean enableNotification(fuelnotificationtype notificationType) {
        return fuel.instance().enableNotification(notificationType);
    }

    public static boolean disableNotification(fuelnotificationtype notificationType) {
        return fuel.instance().disableNotification(notificationType);
    }

    public static boolean isNotificationEnabled(fuelnotificationtype notificationType) {
        return fuel.instance().isNotificationEnabled(notificationType);
    }

    public static void setLanguageCode(String langCode) {
        fuel.instance().setLanguageCode(langCode);
    }

    public static boolean setNotificationIcon(int iconResId) {
        return fuel.instance().setNotificationIcon(iconResId);
    }

    public static boolean setNotificationIcon(String iconName) {
        return fuel.instance().setNotificationIcon(iconName);
    }

    public static boolean syncVirtualGoods() {

        return fuel.instance().syncVirtualGoods();
    }

    public static boolean acknowledgeVirtualGoods(final String transactionId, final boolean consumed) {
        return fuel.instance().acknowledgeVirtualGoods(transactionId, consumed);
    }

    public static boolean sdkSocialLoginCompleted(Map<String, Object> loginData) {
        return fuel.instance().sdkSocialLoginCompleted(loginData);
    }

    public static boolean sdkSocialInviteCompleted() {
        return fuel.instance().sdkSocialInviteCompleted();
    }

    public static boolean sdkSocialShareCompleted() {
        return fuel.instance().sdkSocialShareCompleted();
    }


    //--Compete methods

    public static void initializeCompete() {

        Log.i(kLogTag, "Initialize Compete");
        fuelcompete.setup();
    }

    public static boolean submitMatchResult(String matchResultJSONString) {
        JSONObject matchResultJSON = null;
        
        try {
            matchResultJSON = new JSONObject(matchResultJSONString);
        } catch (JSONException jsonException) {
            return false;
        }
        
        Object matchResultObject = normalizeJSONObject(matchResultJSON);
        
        if (matchResultObject == null) {
            return false;
        }
        
        if (!(matchResultObject instanceof Map)) {
            return false;
        }
        
        @SuppressWarnings("unchecked")
        Map<String, Object> matchResult = (Map<String, Object>) matchResultObject;
        
        return fuelcompete.instance().submitMatchResult(matchResult);
    }

    public static void syncChallengeCounts() {

        fuelcompete.instance().syncChallengeCounts();
    }

    public static void syncTournamentInfo() {

        fuelcompete.instance().syncTournamentInfo();
    }

    //--Compete UI methods

    public static void initializeCompeteUI() {

        fuelcompeteui.setup();

    }

    public static void setOrientationuiCompete(fuelorientationtype orientation) {
        fuelcompeteui.instance().setOrientation(orientation);
    }

    public static boolean Launch() {
        Log.i(kLogTag, "Launch");
        return fuelcompeteui.instance().launch();
    }


    //--Ignite methods

    public static void initializeIgnite() {

        Log.i(kLogTag, "Initialize fuelignite");
        fuelignite.setup();
        fueligniteui.setup();
        fueligniteui.instance().setOrientation(fuelorientationtype.portrait);

    }

    public static boolean execMethod(String method, String params) {
        try {
            JSONArray jsonParams = new JSONArray(params);
            List<Object> paramsList = fueljsonhelper.sharedInstance().toList(jsonParams, false);
            return  fuelignite.instance().execMethod(method, paramsList);
        }catch (JSONException e) {
            Log.e(kLogTag, "ExecMethod error method : "+ method + "; params:" + params + "; Exception" + e.toString());
            return false;
        }
    }


    public static void sendProgress(String progress, String tags) {
        try {
            JSONObject jsonProgress = new JSONObject(progress);
            HashMap<String, Object> progressMap = (HashMap<String, Object>) fueljsonhelper.sharedInstance().toMap(jsonProgress);

            List<Object> tagsList = null;
            if( tags != null ) {
                JSONArray jsonTags = new JSONArray(tags);
                if (jsonTags != null) {
                    tagsList = fueljsonhelper.sharedInstance().toList(jsonTags, false);
                }
            }
            fuelignite.instance().sendProgress(progressMap, tagsList);
        }catch (JSONException e) {
            Log.e(kLogTag, "sendProgress error: "+e.getMessage());
        }
    }

    public static boolean getEvents(String eventTags) {
        try {
            List<Object> eventTagsList = null;
            if( eventTags != null ) {
                JSONArray jsonEventTags = new JSONArray(eventTags);
                eventTagsList = fueljsonhelper.sharedInstance().toList(jsonEventTags, false);
            }
            return  fuelignite.instance().getEvents(eventTagsList);
        }catch (JSONException e) {
            return false;
        }
    }

    public static boolean getLeaderBoard(String boardID) {
        return  fuelignite.instance().getLeaderBoard(boardID);
    }

    public static boolean getMission(String missionID) {
        return fuelignite.instance().getMission(missionID);
    }

    public static boolean getQuest(String questID) {
        return  fuelignite.instance().getQuest(questID);
    }


    //--Ignite UI methods

    public static void initializeIgniteUI() {

        fueligniteui.setup();

    }

    public static  void setOrientationuiIgnite(fuelorientationtype orientation) {
        fueligniteui.instance().setOrientation(orientation);
    }

    //--Dynamics methods

    public static void initializeDynamics() {

        fueldynamics.setup();
    }

    public static boolean setUserConditions(String userConditions) {
        try{
            JSONObject jsonProgress = new JSONObject(userConditions);
            HashMap<String, Object> userConditionsMap = (HashMap<String, Object>) fueljsonhelper.sharedInstance().toMap(jsonProgress);

            return fueldynamics.instance().setUserConditions(userConditionsMap);

        }catch (JSONException e) {
            Log.e(kLogTag, "setUserConditions error: "+e.getMessage());
            return false;

        }
    }

    public static boolean syncUserValues() {

        return fueldynamics.instance().syncUserValues();
    }

    //--GCM
    public static void initializeGCM(String googleProjectID) {
        fuel.instance().initializeGCM(UnityPlayer.currentActivity, googleProjectID);
    }

    public static void onPause() {
        Log.i(kLogTag, "onPause");
    }

    public static void onResume() {
        Log.i(kLogTag, "onResume");
    }

    public static void onQuit() {
        Log.i(kLogTag, "onQuit");
    }
    
    //--Utility methods
    /***************************************************************************
     * Normalizes a JSON object into its deserialized form. Used to deserialize
     * JSON which includes value meta-data in order to preserve it's type. Does
     * not suffer the type conversion issues between ints versus longs, and
     * floats versus doubles since the internal representation of primitives are
     * strings.
     *
     * @param json JSON object to normalize.
     * @return The normalized JSON object, null otherwise.
     */
    private static Object normalizeJSONObject(JSONObject json) {
        if (json == null) {
            return null;
        }
        
        if (isNormalizedJSONValue(json)) {
            return normalizeJSONValue(json);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        Iterator<?> iterator = json.keys();
        
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            
            if (key == null) {
                continue;
            }
            
            Object value = json.opt(key);
            
            if (value == null) {
                continue;
            }
            
            Object normalizedValue = null;
            
            if (value instanceof JSONArray) {
                normalizedValue = normalizeJSONArray((JSONArray) value);
            } else if (value instanceof JSONObject) {
                normalizedValue = normalizeJSONObject((JSONObject) value);
            } else {
                continue;
            }
            
            if (normalizedValue == null) {
                continue;
            }
            
            resultMap.put(key, normalizedValue);
        }
        
        return resultMap;
    }
    
    /***************************************************************************
     * Normalizes a JSON array into its deserialized form. Used to deserialize
     * JSON which includes value meta-data in order to preserve it's type. Does
     * not suffer the type conversion issues between ints versus longs, and
     * floats versus doubles since the internal representation of primitives are
     * strings.
     *
     * @param json JSON array to normalize.
     * @return The normalized JSON array, null otherwise.
     */
    private static Object normalizeJSONArray(JSONArray json) {
        if (json == null) {
            return null;
        }
        
        List<Object> resultList = new ArrayList<Object>();
        
        int count = json.length();
        
        for (int index = 0; index < count; index++) {
            Object value = json.opt(index);
            
            if (value == null) {
                continue;
            }
            
            Object normalizedValue = null;
            
            if (value instanceof JSONArray) {
                normalizedValue = normalizeJSONArray((JSONArray) value);
            } else if (value instanceof JSONObject) {
                normalizedValue = normalizeJSONObject((JSONObject) value);
            } else {
                continue;
            }
            
            if (normalizedValue == null) {
                continue;
            }
            
            resultList.add(normalizedValue);
        }
        
        return resultList;
    }
    
    /***************************************************************************
     * Normalizes a JSON value into its deserialized form. Used to deserialize
     * JSON which includes value meta-data in order to preserve it's type. Does
     * not suffer the type conversion issues between ints versus longs, and
     * floats versus doubles since the internal representation of primitives are
     * strings.
     *
     * @param json JSON value to normalize.
     * @return The normalized JSON value, null otherwise.
     */
    private static Object normalizeJSONValue(JSONObject json) {
        if (json == null) {
            return null;
        }
        
        String type = (String) json.opt("type");
        String value = (String) json.opt("value");
        
        if ((type == null) || (value == null)) {
            return null;
        }
        
        DataType dataType = DataType.findByValue(type);
        
        if (dataType == null) {
            return null;
        }
        
        switch (dataType) {
            case INTEGER:
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException numberFormatException) {
                    return null;
                }
            case LONG:
                try {
                    return Long.parseLong(value);
                } catch (NumberFormatException numberFormatException) {
                    return null;
                }
            case FLOAT:
                try {
                    return Float.parseFloat(value);
                } catch (NumberFormatException numberFormatException) {
                    return null;
                }
            case DOUBLE:
                try {
                    return Double.parseDouble(value);
                } catch (NumberFormatException numberFormatException) {
                    return null;
                }
            case BOOLEAN:
                return Boolean.parseBoolean(value);
            case STRING:
                return value;
            default:
                return null;
        }
    }
    
    /***************************************************************************
     * Validates whether or not the given JSON object is a serialized JSON
     * value.
     * 
     * @param json JSON object to validate.
     * @return True if the given JSON object is a serialized JSON value, false
     *         otherwise.
     */
    private static boolean isNormalizedJSONValue(JSONObject json) {
        if (json == null) {
            return false;
        }
        
        Object checksumObject = json.opt("checksum");
        
        if (!(checksumObject instanceof String)) {
            return false;
        }
        
        String checksum = (String) checksumObject;
        
        if (!checksum.equals("faddface")) {
            return false;
        }
        
        Object typeObject = json.opt("type");
        
        if (!(typeObject instanceof String)) {
            return false;
        }
        
        String type = (String) json.opt("type");
        
        if (DataType.findByValue(type) == null) {
            return false;
        }
        
        Object valueObject = json.opt("value");
        
        if (!(valueObject instanceof String)) {
            return false;
        }
        
        return true;
    }
    
}
