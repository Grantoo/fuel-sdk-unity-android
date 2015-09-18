package com.fuelpowered.lib.fuelsdk.unity;

/**
 * Created by alexisbarra on 8/30/15.
 */

import android.app.Activity;
import android.util.Log;


import com.fuelpowered.lib.fuelsdk.fuel;
import com.fuelpowered.lib.fuelsdk.fuelcompete;
import com.fuelpowered.lib.fuelsdk.fuelcompeteui;
import com.fuelpowered.lib.fuelsdk.fueldynamics;
import com.fuelpowered.lib.fuelsdk.fuelignite;
import com.fuelpowered.lib.fuelsdk.fueligniteui;
import com.fuelpowered.lib.fuelsdk.fuelnotificationtype;
import com.fuelpowered.lib.fuelsdk.fuelorientationtype;
import com.fuelpowered.lib.fuelsdk.fuelbroadcasttype;
import com.fuelpowered.lib.fuelsdk.fuelbroadcastreceiver;

import java.util.List;
import java.util.Map;
import com.unity3d.player.UnityPlayer;


public final class FuelSDKUnitySingleton {

    private static final String kLogTag = "FuelSDKUnitySingleton";


    //--Fuel methods
    public static void initialize(String gameId, String gameSecret, boolean gameHasLogin,
                                  boolean gameHasInvite, boolean gameHasShare) {

        Log.i(kLogTag, "Initialize FuelSdkUnity");

        fuel.init(gameId, gameSecret, gameHasLogin, gameHasInvite, gameHasShare);
        fuel.instance().setLanguageCode("EN");
        fueldynamics.init();

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
        fuelcompete.init();
        fuelcompeteui.init();
        fuelcompeteui.instance().setOrientation(fuelorientationtype.portrait);
    }

    public static boolean submitMatchResult(Map<String, Object> matchResult) {
        return fuelcompete.instance().submitMatchResult(matchResult);
    }

    public static void syncChallengeCounts() {

        fuelcompete.instance().syncChallengeCounts();
    }

    public static void syncTournamentInfo() {

        fuelcompete.instance().syncTournamentInfo();
    }

    //--Compete UI methods
    public static void setOrientationuiCompete(fuelorientationtype orientation) {
        fuelcompeteui.instance().setOrientation(orientation);
    }

    public static boolean Launch() {
        Log.i(kLogTag, "Launch");
        return fuelcompeteui.instance().launch();
    }


    //--Ignite methods

    public static void initializeIngite() {

        Log.i(kLogTag, "Initialize fuelignite");
        fuelignite.init();
        fueligniteui.init();
        fueligniteui.instance().setOrientation(fuelorientationtype.portrait);

    }

    public static boolean execMethod(String method, List<Object> params) {
        return  fuelignite.instance().execMethod(method, params);
    }


    public static void sendProgress(Map<String, Object> progress, List<String> tags) {
        fuelignite.instance().sendProgress(progress, tags);
    }

    public static boolean getEvents(List<String> eventTags) {
        return  fuelignite.instance().getEvents(eventTags);
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

    public static void initializeIngiteUI() {

        fueligniteui.init();

    }

    public static  void setOrientationuiIgnite(fuelorientationtype orientation) {
        fueligniteui.instance().setOrientation(orientation);
    }

    //--Dynamics methods

    public static void initializeDynamics() {

        fueldynamics.init();
    }

    public static boolean setUserConditions(Map<String, Object> userConditions) {
        return fueldynamics.instance().setUserConditions(userConditions);
    }

    public static boolean syncUserValues() {

        return fueldynamics.instance().syncUserValues();
    }


    public static void onPause() {
        Log.i(kLogTag, "OnPause");
        Activity sActivity = UnityPlayer.currentActivity;
        fuel.onPause(sActivity);
    }

    public static void onResume() {
        Log.i(kLogTag, "OnResume");
        Activity sActivity = UnityPlayer.currentActivity;
        fuel.onResume(sActivity);
    }

    public static void onQuit() {
        Log.i(kLogTag, "OnQuit");
        Activity sActivity = UnityPlayer.currentActivity;
    }

}
