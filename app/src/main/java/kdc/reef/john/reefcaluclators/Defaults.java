package kdc.reef.john.reefcaluclators;

import android.app.Application;

import java.io.Serializable;

/**
 * Created by John on 5/31/2017.
 */

public class Defaults implements Serializable{

    private static String currency = "$";
    private static int curPos =0;
    private static String measurement = "\"";
    private static int measPos =0;
    private static boolean purchasedUpgrade = false;

    public static String getCurrency() {
        return currency;
    }

    public static void setCurrency(String currency) {
        Defaults.currency = currency;
    }

    public static int getCurPos() {
        return curPos;
    }

    public static void setCurPos(int curPos) {
        Defaults.curPos = curPos;
    }

    public static String getMeasurement() {
        return measurement;
    }

    public static void setMeasurement(String measurement) {
        Defaults.measurement = measurement;
    }

    public static int getMeasPos() {
        return measPos;
    }

    public static void setMeasPos(int measPos) {
        Defaults.measPos = measPos;
    }

    public static boolean isPurchasedUpgrade() {
        return purchasedUpgrade;
    }

    public static void setPurchasedUpgrade(boolean purchasedUpgrade) {
        Defaults.purchasedUpgrade = purchasedUpgrade;
    }

}
