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
import java.util.Objects;

import com.unity3d.player.UnityPlayer;


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

    public static boolean execMethod(String method, List<Object> params) {
        return  fuelignite.instance().execMethod(method, params);
    }


    public static void sendProgress(Map<String, Object> progress, List<Object> tags) {
        fuelignite.instance().sendProgress(progress, tags);
    }

    public static boolean getEvents(List<Object> eventTags) {
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

    public static boolean setUserConditions(Map<String, Object> userConditions) {
        return fueldynamics.instance().setUserConditions(userConditions);
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

}
