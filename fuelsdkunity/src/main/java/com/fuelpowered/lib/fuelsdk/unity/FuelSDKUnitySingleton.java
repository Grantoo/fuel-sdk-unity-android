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
import com.fuelpowered.lib.fuelsdk.fuellocalnotificationtype;
import com.fuelpowered.lib.fuelsdk.fuelloginstate;
import com.fuelpowered.lib.fuelsdk.fuelnotificationtype;
import com.fuelpowered.lib.fuelsdk.fuelorientationtype;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public final class FuelSDKUnitySingleton {

    /**
     * Data type enumeration.
     */
    private static enum DataType {
        
        INTEGER("0"),
        LONG("1"),
        FLOAT("2"),
        DOUBLE("3"),
        BOOLEAN("4"),
        STRING("5");
        
        /**
         * Mapping of values to their enumeration.
         */
        private static Map<String, DataType> mValueEnumMap;
        
        /**
         * Static initializer.
         */
        static {
            mValueEnumMap = new HashMap<String, DataType>();
            
            for (DataType dataType : DataType.values()) {
                mValueEnumMap.put(dataType.mValue, dataType);
            }
        }
        
        /**
         * Enumeration value.
         */
        private String mValue;
        
        /***********************************************************************
         * Constructor.
         *
         * @param value Value to bind to the enumeration.
         */
        private DataType(String value) {
            mValue = value;
        }
        
        /***********************************************************************
         * Retrieves the enumeration that matches the given value.
         * 
         * @param value Value to retrieve the matching enumeration for.
         * @return The matching enumeration to the given value, null if there is
         *         no match.
         */
        public static DataType findByValue(String value) {
            return mValueEnumMap.get(value);
        }
        
    }

    private static final String kLogTag = "FuelSDKUnitySingleton";

    //--Fuel methods
    public static void initialize(String gameId, String gameSecret, boolean gameHasLogin,
                                  boolean gameHasInvite, boolean gameHasShare) {

        Log.i(kLogTag, "Initialize FuelSdkUnity");

        fuel.setup(gameId, gameSecret, gameHasLogin, gameHasInvite, gameHasShare);

        Log.i(kLogTag, "Finished Initialize");
    }

    public static void setNotificationToken(String notificationToken) {
        fuel.setNotificationToken(notificationToken);
    }

    public static boolean enableNotification(fuelnotificationtype notificationType) {
        return fuel.enableNotification(notificationType);
    }

    public static boolean disableNotification(fuelnotificationtype notificationType) {
        return fuel.disableNotification(notificationType);
    }

    public static boolean isNotificationEnabled(fuelnotificationtype notificationType) {
        return fuel.isNotificationEnabled(notificationType);
    }

    public static boolean setLocalNotificationMessage(String localNotificationType, String message) {
        if (localNotificationType == null) {
            return false;
        }

        fuellocalnotificationtype type =
                fuellocalnotificationtype.findByAlias(localNotificationType.toLowerCase());

        if (type == null) {
            return false;
        }

        return fuel.setLocalNotificationMessage(type, message);
    }

    public static boolean setLocalNotificationActive(String localNotificationType, boolean active) {
        if (localNotificationType == null) {
            return false;
        }

        fuellocalnotificationtype type =
                fuellocalnotificationtype.findByAlias(localNotificationType.toLowerCase());

        if (type == null) {
            return false;
        }

        return fuel.setLocalNotificationActive(type, active);
    }

    public static boolean isLocalNotificationActive(String localNotificationType) {
        if (localNotificationType == null) {
            return false;
        }

        fuellocalnotificationtype type =
                fuellocalnotificationtype.findByAlias(localNotificationType.toLowerCase());

        if (type == null) {
            return false;
        }

        return fuel.isLocalNotificationActive(type);
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

    public static boolean openVirtualGoodsConnection() {
        return fuel.instance().openVirtualGoodsConnection();
    }

    public static boolean closeVirtualGoodsConnection() {
        return fuel.instance().closeVirtualGoodsConnection();
    }

    public static boolean syncVirtualGoods(String tag) {

        return fuel.instance().syncVirtualGoods(tag);
    }

    public static boolean acknowledgeVirtualGoods(String tag, String acknowledgementTokensJSONString) {
        List<String> acknowledgementTokens = null;

        if (acknowledgementTokensJSONString != null) {
            List<Object> acknowledgementTokensList = deserializeList(acknowledgementTokensJSONString);

            if (acknowledgementTokensList == null) {
                return false;
            }

            acknowledgementTokens = new ArrayList<String>();

            for (Object acknowledgementTokenObject : acknowledgementTokensList) {
                acknowledgementTokens.add(acknowledgementTokenObject.toString());
            }
        }


        return fuel.instance().acknowledgeVirtualGoods(tag, acknowledgementTokens);
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

    public static boolean requestUpdateUserInfo(String userDetailsJSONString) {
        Map<String, Object> userDetails = null;

        if (userDetailsJSONString != null) {
            userDetails = deserializeMap(userDetailsJSONString);

            if (userDetails == null) {
                return false;
            }
        }

        return fuel.instance().requestUpdateUserInfo(userDetails);
    }

    public static boolean requestUserAvatars(){
        return fuel.instance().requestUserAvatars();
    }

    public static boolean getUserData(String userID, String keysJSONString){
        List<Object> keys = null;

        if (keysJSONString != null) {
            keys = deserializeList(keysJSONString);

            if (keys == null) {
                return false;
            }
        }

        return fuelignite.instance().getUserData(userID, keys);
    }

    public static boolean submitUserData(String userDataJSONString){
        Map<String, Object> userData = null;

        if (userDataJSONString != null) {
            userData = deserializeMap(userDataJSONString);

            if (userData == null) {
                return false;
            }
        }

        return fuelignite.instance().submitUserData(userData);
    }

    public static boolean requestUserInfo(){
        return fuel.instance().requestUserInfo();
    }

    //--Compete methods

    public static void initializeCompete() {

        Log.i(kLogTag, "Initialize Compete");
        fuelcompete.setup();
    }

    public static boolean submitMatchResult(String matchResultJSONString) {
        Map<String, Object> matchResult = null;

        if (matchResultJSONString != null) {
            matchResult = deserializeMap(matchResultJSONString);

            if (matchResult == null) {
                return false;
            }
        }

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

    public static boolean launch() {
        Log.i(kLogTag, "Launch");
        return fuelcompeteui.instance().launch();
    }


    //--Ignite methods

    public static void initializeIgnite() {

        Log.i(kLogTag, "Initialize fuelignite");
        fuelignite.setup();
    }

    public static boolean execMethod(String method, String paramsJSONString) {
        List<Object> params = null;

        if (paramsJSONString != null) {
            params = deserializeList(paramsJSONString);

            if (params == null) {
                return false;
            }
        }

        return fuelignite.instance().execMethod(method, params);
    }


    public static void sendProgress(String progressJSONString, String tagsJSONString) {
        Map<String, Object> progress = null;

        if (progressJSONString != null) {
            progress = deserializeMap(progressJSONString);

            if (progress == null) {
                return;
            }
        }

        List<Object> tags = null;

        if (tagsJSONString != null) {
            tags = deserializeList(tagsJSONString);

            if (tags == null) {
                return;
            }
        }

        fuelignite.instance().sendProgress(progress, tags);
    }

    public static boolean getEvents(String tagsJSONString) {
        List<Object> tags = null;

        if (tagsJSONString != null) {
            tags = deserializeList(tagsJSONString);

            if (tags == null) {
                return false;
            }
        }

        return fuelignite.instance().getEvents(tags);
    }

    public static boolean getSampleEvents(String tagsJSONString) {
        List<Object> tags = null;

        if (tagsJSONString != null) {
            tags = deserializeList(tagsJSONString);

            if (tags == null) {
                return false;
            }
        }

        return fuelignite.instance().getSampleEvents(tags);
    }

    public static boolean getEvent(String activityID) {
        return  fuelignite.instance().getEvent(activityID);
    }

    public static boolean joinEvent(String eventID) {
        return  fuelignite.instance().joinEvent(eventID);
    }

    public static boolean getLocalizationFile() {
        return fuel.getLocalizationFile();
    }

    public static boolean getLeaderBoard(String boardID) {
        return  fuelignite.instance().getLeaderBoard(boardID);
    }

    public static boolean getMission(String missionID) {
        return fuelignite.instance().getMission(missionID);
    }

    public static boolean getOffer(String offerID) {
        return fuelignite.instance().getOffer(offerID);
    }

    public static boolean acceptOffer(String offerID) {
        return fuelignite.instance().acceptOffer(offerID);
    }

    public static boolean getVote(String voteID) {
        return fuelignite.instance().getVote(voteID);
    }

    public static boolean submitVoteCandidacy(String voteID, String candidacyMetadataJSONString, String candidateTagsJSONString) {
        Map<String, Object> candidacyMetadata = null;

        if (candidacyMetadataJSONString != null) {
            candidacyMetadata = deserializeMap(candidacyMetadataJSONString);

            if (candidacyMetadata == null) {
                return false;
            }
        }

        List<Object> candidateTags = null;

        if (candidateTagsJSONString != null) {
            candidateTags = deserializeList(candidateTagsJSONString);

            if (candidateTags == null) {
                return false;
            }
        }

        return fuelignite.instance().submitVoteCandidacy(voteID, candidacyMetadata, candidateTags);
    }

    public static boolean submitVote(String voteID, String votesJSONString, String voterTagsJSONString) {
        Map<String, Object> votes = null;

        if (votesJSONString != null) {
            votes = deserializeMap(votesJSONString);

            if (votes == null) {
                return false;
            }
        }

        List<Object> voterTags = null;

        if (voterTagsJSONString != null) {
            voterTags = deserializeList(voterTagsJSONString);

            if (voterTags == null) {
                return false;
            }
        }

        return fuelignite.instance().submitVote(voteID, votes, voterTags);
    }

    public static boolean getVoteCandidates(String voteID, int numCandidates) {
        return fuelignite.instance().getVoteCandidates(voteID, numCandidates);
    }

    public static boolean getCandidateLeaders(String voteID, int numCandidates) {
        return fuelignite.instance().getCandidateLeaders(voteID, numCandidates);
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

    public static boolean setUserConditions(String userConditionsJSONString) {
        Map<String, Object> userConditions = null;

        if (userConditionsJSONString != null) {
            userConditions = deserializeMap(userConditionsJSONString);

            if (userConditions == null) {
                return false;
            }
        }

        return fueldynamics.instance().setUserConditions(userConditions);
    }

    public static boolean syncUserValues() {

        return fueldynamics.instance().syncUserValues();
    }

    //--GCM
    public static void initializeGCM(String googleProjectID) {
        fuel.initializeGCM(UnityPlayer.currentActivity, googleProjectID);
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

    //--Login methods
    public static int getLoginState() {
        return fuel.instance().getLoginState().ordinal();
    }

    public static boolean login() {
        return fuel.instance().login();
    }
    
    //--Utility methods
    /***************************************************************************
     * Deserializes a Map<String, Object> from the given JSON string. The JSON
     * string must have the primative map values encoded with meta-data used to
     * preserve the primitive value type. This is to eliminate type conversion
     * issues between ints versus longs, and floats versus doubles since the
     * internal representation of primitives are strings.
     *
     * @param jsonString The JSON string encoded Map<String, Object> to
     *                   deserialize.
     * @return The deserialized Map<String, Object> on success, null otherwise.
     */
    private static Map<String, Object> deserializeMap(String jsonString) {
        if (jsonString == null) {
            return null;
        }

        JSONObject json = null;

        try {
            json = new JSONObject(jsonString);
        } catch (JSONException jsonException) {
            return null;
        }

        Object object = normalizeJSONObject(json);

        if (object == null) {
            return null;
        }

        if (!(object instanceof Map)) {
            return null;
        }

        return (Map<String, Object>)object;
    }

    /***************************************************************************
     * Deserializes a List<Object> from the given JSON string. The JSON string
     * must have the primative list values encoded with meta-data used to
     * preserve the primitive value type. This is to eliminate type conversion
     * issues between ints versus longs, and floats versus doubles since the
     * internal representation of primitives are strings.
     *
     * @param jsonString The JSON string encoded List<Object> to
     *                   deserialize.
     * @return The deserialized List<Object> on success, null otherwise.
     */
    private static List<Object> deserializeList(String jsonString) {
        if (jsonString == null) {
            return null;
        }

        JSONArray json = null;

        try {
            json = new JSONArray(jsonString);
        } catch (JSONException jsonException) {
            return null;
        }

        Object object = normalizeJSONArray(json);

        if (object == null) {
            return null;
        }

        if (!(object instanceof List)) {
            return null;
        }

        return (List<Object>)object;
    }

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
